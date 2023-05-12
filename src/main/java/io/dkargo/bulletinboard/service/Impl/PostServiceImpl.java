package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.post.ReqCreatePostDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqFindOptionPostDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqUpdatePostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResFindDetailPostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResFindOptionPostDTO;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.entity.PostCategory;
import io.dkargo.bulletinboard.entity.User;
import io.dkargo.bulletinboard.exception.CustomException;
import io.dkargo.bulletinboard.exception.ErrorCode;
import io.dkargo.bulletinboard.repository.PostCategoryRepository;
import io.dkargo.bulletinboard.repository.PostFileRepository;
import io.dkargo.bulletinboard.repository.PostRepository;
import io.dkargo.bulletinboard.repository.UserRepository;
import io.dkargo.bulletinboard.repository.support.PostRepositorySupport;
import io.dkargo.bulletinboard.service.PostCategoryService;
import io.dkargo.bulletinboard.service.PostFileService;
import io.dkargo.bulletinboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostRepositorySupport postRepositorySupport;
    private final PostCategoryService postCategoryService;
    private final PostCategoryRepository postCategoryRepository;
    private final UserRepository userRepository;
    private final PostFileService postFileService;
    private final PostFileRepository postFileRepository;

    @Value("${file.maxCount}")
    private Integer maxFileCount;

    @Override
    public ResFindDetailPostDTO findDetailPostById(long id, long userId, String password) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        //비공개 게시물 체크
        if (post.getPostOpenUseFlag()) {
            //자신이 작성한 게시물인지 체크
            if (!post.getUser().userIdValidCheck(userId)) {
                //비밀번호 체크
                if (!post.passwordValidCheck(password)) {
                    throw new CustomException(ErrorCode.PASSWORD_NOT);
                }
            }
        }

        //조회수 증가
//        postRepositorySupport.incrementClickCount(id);
        post.incrementClickCount();

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
    public void createPost(ReqCreatePostDTO reqCreatePostDTO) throws IOException {

        //User 조회
        User user = userRepository.findById(reqCreatePostDTO.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        //게시글 저장
        Post post = Post.builder()
                .user(user)
                .title(reqCreatePostDTO.getTitle())
                .content(reqCreatePostDTO.getContent())
                .postPassword(reqCreatePostDTO.getPostPassword())
                .postOpenUseFlag(reqCreatePostDTO.getPostOpenUseFlag())
                .replyCommentUseFlag(reqCreatePostDTO.getReplyCommentUseFlag())
                .build();

        postRepository.save(post);

        //게시글 * 카테고리 뎁스만큼 저장
        postCategoryService.saveAllPostCategory(post, reqCreatePostDTO.getCategoryId());
        //파일리스트 저장
        if (!CollectionUtils.isEmpty(reqCreatePostDTO.getFiles())) {
            //파일리스트 개수제한 체크
            if (reqCreatePostDTO.getFiles().size() > maxFileCount) {
                new CustomException(ErrorCode.FILES_TOTO);
            }

            postFileService.createAllPostFile(post, reqCreatePostDTO.getFiles());
        }
    }

    @Override
    public void updatePost(long postId, ReqUpdatePostDTO reqUpdatePostDTO) throws IOException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

//        if (!post.getUser().userIdValidCheck(reqUpdatePostDTO.getUserId())) {
//            throw new CustomException(ErrorCode.UPDATE_ONLY_WRITER);
//        }

        post.update(reqUpdatePostDTO);

        PostCategory postCategory = postCategoryRepository.findTopByPostOrderByCategoryDesc(post);

        //카테고리가 다르면 업데이트
        if (!reqUpdatePostDTO.getCategoryId().equals(postCategory.getCategory().getId())) {
            //기존 postCategory 데이터 삭제
            postCategoryRepository.deleteAllInBatch(post.getPostCategoryList());
            //게시글 * 카테고리 뎁스만큼 생성
            postCategoryService.saveAllPostCategory(post, reqUpdatePostDTO.getCategoryId());
        }

        //파일이 있으면 수정 로직타고 없으면 기존 리스트 전부 삭제
        if (!CollectionUtils.isEmpty(reqUpdatePostDTO.getFiles())){
            postFileService.updateAllPostFile(post, reqUpdatePostDTO.getFiles());
        } else {
            postFileService.deleteAllPostFile(post.getPostFileList());
        }
    }

    @Override
    public void deletePost(long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        //TODO : 유저 확인 (시큐리티)
//        if (!post.getUser().userIdValidCheck(reqDeletePostDTO.getUserId())) {
//            throw new CustomException(ErrorCode.UPDATE_ONLY_WRITER);
//        }

        postRepository.delete(post);
    }
}
