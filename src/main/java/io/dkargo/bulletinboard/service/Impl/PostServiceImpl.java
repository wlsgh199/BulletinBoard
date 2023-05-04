package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.post.*;
import io.dkargo.bulletinboard.dto.response.post.ResFindOptionPostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResFindDetailPostDTO;
import io.dkargo.bulletinboard.entity.User;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.repository.PostFileRepository;
import io.dkargo.bulletinboard.repository.PostCategoryRepository;
import io.dkargo.bulletinboard.repository.PostRepository;
import io.dkargo.bulletinboard.repository.UserRepository;
import io.dkargo.bulletinboard.repository.support.PostRepositorySupport;
import io.dkargo.bulletinboard.service.PostFileService;
import io.dkargo.bulletinboard.service.PostCategoryService;
import io.dkargo.bulletinboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostRepositorySupport postRepositorySupport;
    private final PostCategoryService postCategoryService;
    private final PostCategoryRepository postCategoryRepository;
    private final UserRepository userRepository;
    private final PostFileService postFileService;
    private final PostFileRepository postFileRepository;


    @Override
    public ResFindDetailPostDTO findDetailPostById(Long id, Long userId, String password) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 게시물이 존재하지 않습니다."));

        //비공개 게시물 체크
        if (post.getPostOpenUseFlag().equals("Y")) {
            //자신이 작성한 게시물인지 체크
            if (!post.getUser().userIdValidCheck(userId)) {
                //비밀번호 체크
                if (!post.passwordValidCheck(password)) {
                    throw new RuntimeException("잘못된 비밀번호 입니다.");
                }
            }
        }

        //조회수 증가
        postRepositorySupport.incrementClickCount(id);

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
    public void addPost(ReqAddPostDTO reqAddPostDTO) throws IOException {

        //User 조회
        User user = userRepository.findById(reqAddPostDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않습니다."));

        //게시글 저장
        Post post = Post.builder()
                .user(user)
                .title(reqAddPostDTO.getTitle())
                .content(reqAddPostDTO.getContent())
                .postPassword(reqAddPostDTO.getPostPassword())
                .postOpenUseFlag(reqAddPostDTO.getPostOpenUseFlag())
                .replyCommentUseFlag(reqAddPostDTO.getReplyCommentUseFlag())
                .build();

        postRepository.save(post);

        //게시글 * 카테고리 뎁스만큼 저장
        postCategoryService.saveAllPostCategory(post, reqAddPostDTO.getCategoryId());
        //파일리스트 저장
        postFileService.saveAllPostFile(post, reqAddPostDTO.getFiles());
    }

    @Override
    public void patchPost(ReqPatchPostDTO reqPatchPostDTO) throws IOException {
        Post post = postRepository.findById(reqPatchPostDTO.getId())
                .orElseThrow(() -> new RuntimeException("해당 게시물이 존재하지 않습니다."));

        if (!post.getUser().userIdValidCheck(reqPatchPostDTO.getUserId())) {
            throw new RuntimeException("게시물 작성자만 수정할수 있습니다.");
        }

        //제목 수정
        if (reqPatchPostDTO.getTitle() == null) {
            reqPatchPostDTO.setTitle(post.getTitle());
        }

        //내용 수정
        if (reqPatchPostDTO.getContent() == null) {
            reqPatchPostDTO.setContent(post.getContent());
        }

        //게시물 비공개 여부 수정
        if (reqPatchPostDTO.getPostOpenUseFlag() == null) {
            reqPatchPostDTO.setPostOpenUseFlag(post.getPostOpenUseFlag());
        }

        //게시물 비밀번호 수정
        if (reqPatchPostDTO.getPostPassword() == null) {
            reqPatchPostDTO.setPostPassword(post.getPostPassword());
        }

        //댓글,답글 사용 여부
        if (reqPatchPostDTO.getReplyCommentUseFlag() == null) {
            reqPatchPostDTO.setReplyCommentUseFlag(post.getReplyCommentUseFlag());
        }

        post.patch(reqPatchPostDTO);
        postRepository.save(post);

        //카테고리 업데이트
        if (reqPatchPostDTO.getCategoryId() != null) {
            //기존 postCategory 데이터 삭제
            postCategoryRepository.deleteAllInBatch(post.getPostCategoryList());
            //게시글 * 카테고리 뎁스만큼 생성
            postCategoryService.saveAllPostCategory(post, reqPatchPostDTO.getCategoryId());
        }

        //기존 파일 삭제
        postFileRepository.deleteAllInBatch(post.getPostFileList());
        //파일 생성
        postFileService.saveAllPostFile(post, reqPatchPostDTO.getFiles());

    }

    @Override
    public void putPost(ReqPutPostDTO reqPutPostDTO) throws IOException {
        Post post = postRepository.findById(reqPutPostDTO.getId())
                .orElseThrow(() -> new RuntimeException("해당 게시물이 존재하지 않습니다."));

        if (!post.getUser().userIdValidCheck(reqPutPostDTO.getUserId())) {
            throw new RuntimeException("게시물 작성자만 수정할수 있습니다.");
        }

        post.put(reqPutPostDTO);
        postRepository.save(post);

        //기존 postCategory 데이터 삭제
        postCategoryRepository.deleteAllInBatch(post.getPostCategoryList());
        //게시글 * 카테고리 뎁스만큼 생성
        postCategoryService.saveAllPostCategory(post, reqPutPostDTO.getCategoryId());


        //기존 파일 삭제
        postFileService.deleteAllPostFileByPostId(post.getId());

        //파일리스트 저장
        postFileService.saveAllPostFile(post, reqPutPostDTO.getFiles());
    }

    @Override
    public void deletePost(ReqDeletePostDTO reqDeletePostDTO) {
        Post post = postRepository.findById(reqDeletePostDTO.getId())
                .orElseThrow(() -> new RuntimeException("해당 게시물이 존재하지 않습니다."));

        if (!post.getUser().userIdValidCheck(reqDeletePostDTO.getUserId())) {
            throw new RuntimeException("게시물 작성자만 삭제할수 있습니다.");
        }

        postRepository.delete(post);
    }
}
