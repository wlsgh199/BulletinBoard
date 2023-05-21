package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.post.ReqCreatePostDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqFindOptionPostDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqUpdatePostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResCreatePostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResFindDetailPostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResFindOptionPostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResUpdatePostDTO;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.entity.PostCategory;
import io.dkargo.bulletinboard.exception.CustomException;
import io.dkargo.bulletinboard.exception.ErrorCodeEnum;
import io.dkargo.bulletinboard.repository.MemberRepository;
import io.dkargo.bulletinboard.repository.PostCategoryRepository;
import io.dkargo.bulletinboard.repository.PostRepository;
import io.dkargo.bulletinboard.repository.support.PostRepositorySupport;
import io.dkargo.bulletinboard.service.PostCategoryService;
import io.dkargo.bulletinboard.service.PostFileService;
import io.dkargo.bulletinboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostRepositorySupport postRepositorySupport;
    private final PostCategoryService postCategoryService;
    private final PostCategoryRepository postCategoryRepository;
    private final PostFileService postFileService;
    private final MemberRepository memberRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Value("${file.maxCount}")
    private int MAX_FILE_COUNT;

    @Override
    public ResFindDetailPostDTO findDetailPostById(long id, long memberId, String password) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.POST_NOT_FOUND));

        //비공개 게시물 체크
        if (post.getPostOpenUseFlag()) {
            //자신이 작성한 게시물인지 체크
            if (!post.getMember().userIdValidCheck(memberId)) {
                //게시판 비밀번호 체크
                if (!post.passwordCheck(password)) {
                    throw new CustomException(ErrorCodeEnum.PASSWORD_ERROR);
                }
            }
        }

        //조회수 증가
        postRepositorySupport.incrementClickCount(id);

        //1차 캐시에 기존에 조회한게 남아있어서.. 한번 초기화 후 조회
        entityManager.clear();

        //게시물 상세 조회
        return ResFindDetailPostDTO.builder()
                .post(postRepositorySupport.findDetailPostById(id)).build();
    }

    @Override
    public List<ResFindOptionPostDTO> findPostByFindOptionDTO(ReqFindOptionPostDTO reqFindOptionPostDTO) {
        return postRepositorySupport.findPostByReqGetDTO(reqFindOptionPostDTO)
                .stream()
                .map(ResFindOptionPostDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ResCreatePostDTO createPost(ReqCreatePostDTO reqCreatePostDTO, long memberId) throws IOException {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.MEMBER_NOT_FOUND));

        //게시글 저장
        Post post = Post.builder()
                .member(member)
                .title(reqCreatePostDTO.getTitle())
                .content(reqCreatePostDTO.getContent())
                .postPassword(reqCreatePostDTO.getPostPassword())
                .postOpenUseFlag(reqCreatePostDTO.getPostOpenUseFlag())
                .replyCommentUseFlag(reqCreatePostDTO.getReplyCommentUseFlag())
                .build();

        //비공개 게시물 일때
        if (!reqCreatePostDTO.getReplyCommentUseFlag()) {
            // 비밀번호 blank 체크
            if (!post.passwordValidCheck(reqCreatePostDTO.getPostPassword())) {
                throw new CustomException(ErrorCodeEnum.PASSWORD_ERROR);
            }
        }

        postRepository.save(post);

        //게시글 * 카테고리 뎁스만큼 저장
        postCategoryService.saveAllPostCategory(post, reqCreatePostDTO.getCategoryId());

        //파일리스트 저장
        if (!CollectionUtils.isEmpty(reqCreatePostDTO.getFiles())) {
            //파일리스트 개수제한 체크
            reqCreatePostDTO.fileSizeCheck(MAX_FILE_COUNT);
            //파일 저장
            postFileService.createAllPostFile(post, reqCreatePostDTO.getFiles());
        }

        return new ResCreatePostDTO(post);
    }

    @Override
    public ResUpdatePostDTO updatePost(long postId, ReqUpdatePostDTO reqUpdatePostDTO, long memberId) throws IOException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.POST_NOT_FOUND));

        //자신이 작성한게 아니면 에러 발생
        if (!post.getMember().userIdValidCheck(memberId)) {
            throw new CustomException(ErrorCodeEnum.UPDATE_ONLY_WRITER);
        }

        post.update(reqUpdatePostDTO);

        //비공개 게시물 일때
        if (!post.getReplyCommentUseFlag()) {
            // 비밀번호 blank 체크
            if (!post.passwordValidCheck(post.getPostPassword())) {
                throw new CustomException(ErrorCodeEnum.PASSWORD_ERROR);
            }

        }

        PostCategory postCategory = postCategoryRepository.findTopByPostOrderByCategoryDesc(post);

        //카테고리가 다르면 업데이트
        if (!reqUpdatePostDTO.getCategoryId().equals(postCategory.getCategory().getId())) {
            //기존 postCategory 데이터 삭제
            postCategoryRepository.deleteAllInBatch(post.getPostCategoryList());
            //게시글 * 카테고리 뎁스만큼 생성
            postCategoryService.saveAllPostCategory(post, reqUpdatePostDTO.getCategoryId());
        }

        //파일이 있으면 수정 로직타고 없으면 기존 리스트 전부 삭제
        if (!CollectionUtils.isEmpty(reqUpdatePostDTO.getFiles())) {
            postFileService.updateAllPostFile(post, reqUpdatePostDTO.getFiles());
        } else {
            postFileService.deleteAllPostFile(post.getPostFileList());
        }
        return new ResUpdatePostDTO(post);
    }

    //게시글 삭제
    @Override
    public void deletePost(long postId, long memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.POST_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.MEMBER_NOT_FOUND));

        //관리자는 일반유저 게시물 삭제 가능
        if (member.isUser()) {
            //자신이 작성한게 아니면 에러 발생
            if (!post.getMember().userIdValidCheck(member.getId())) {
                throw new CustomException(ErrorCodeEnum.UPDATE_ONLY_WRITER);
            }
        }
        //TODO : 실제 파일 삭제 고려
        postRepository.delete(post);
    }
}
