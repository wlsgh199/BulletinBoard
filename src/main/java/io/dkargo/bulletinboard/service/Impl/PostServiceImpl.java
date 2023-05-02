package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.post.ReqDeletePostDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqPatchPostDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqAddPostDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqPutPostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResPostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResPostDetailDTO;
import io.dkargo.bulletinboard.entity.User;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.repository.PostFileRepository;
import io.dkargo.bulletinboard.repository.PostCategoryRepository;
import io.dkargo.bulletinboard.repository.PostRepository;
import io.dkargo.bulletinboard.repository.support.PostRepositorySupport;
import io.dkargo.bulletinboard.service.PostFileService;
import io.dkargo.bulletinboard.service.UserService;
import io.dkargo.bulletinboard.service.PostCategoryService;
import io.dkargo.bulletinboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final UserService userService;
    private final PostFileService postFileService;
    private final PostFileRepository postFileRepository;

    @Override
    public List<ResPostDTO> findAllPost(Pageable pageable) {
        return postRepositorySupport.findAllPost(pageable)
                .stream()
                .map(ResPostDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ResPostDetailDTO findDetailPostById(Long id, Long userId, String password) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 게시물이 존재하지 않습니다."));

        //비공개 게시물 체크
        if(post.getPostOpenUseFlag().equals("Y")){
            //자신이 작성한 게시물인지 체크
            if(!post.getUser().userIdValidCheck(userId)) {
                //비밀번호 체크
                if (!post.getPostPassword().equals(password)) {
                    throw new RuntimeException("잘못된 비밀번호 입니다.");
                }
            }
        }

        //클릿 횟수 증가
        postRepositorySupport.incrementClickCount(id);

        //게시물 상세 조회
        return ResPostDetailDTO.builder()
                .post(postRepositorySupport.findDetailPostById(id)).build();
    }

    @Override
    public List<ResPostDTO> findPostByMemberId(Long userId, Pageable pageable) {
        return postRepositorySupport.findPostByMemberId(userId, pageable)
                .stream()
                .map(ResPostDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResPostDTO> findPostByTitle(String title, Pageable pageable) {
        return postRepositorySupport.findPostByTitle(title, pageable)
                .stream()
                .map(ResPostDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResPostDTO> findPostByContent(String title, Pageable pageable) {
        return postRepositorySupport.findPostByContent(title, pageable)
                .stream()
                .map(ResPostDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResPostDTO> findPostByCategory(Long categoryId, Pageable pageable) {
        return postRepositorySupport.findPostByCategory(categoryId, pageable)
                .stream()
                .map(ResPostDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void savePost(ReqAddPostDTO reqAddPostDTO, List<MultipartFile> fileList) throws IOException {

        //User 조회
        User user = userService.findMemberById(reqAddPostDTO.getUserId());

        //게시글 저장
        Post post = new Post(user, reqAddPostDTO);
        postRepository.save(post);

        //게시글 * 카테고리 뎁스만큼 저장
        postCategoryService.saveAllPostCategory(post, reqAddPostDTO.getCategoryId());
        //파일리스트 저장
        postFileService.saveAllPostFile(post, fileList);
    }

    @Override
    public void patchPost(ReqPatchPostDTO reqPatchPostDTO, List<MultipartFile> fileList) throws IOException {
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
        postFileService.saveAllPostFile(post, fileList);

    }

    @Override
    public void putPost(ReqPutPostDTO reqPutPostDTO, List<MultipartFile> fileList) throws IOException {
        Post post = postRepository.findById(reqPutPostDTO.getId())
                .orElseThrow(() -> new RuntimeException("해당 게시물이 존재하지 않습니다."));

        if (!post.getUser().userIdValidCheck(reqPutPostDTO.getUserId())) {
            throw new RuntimeException("게시물 작성자만 수정할수 있습니다.");
        }

        post.update(reqPutPostDTO);

        //게시글 * 카테고리 뎁스만큼 저장
        postCategoryService.saveAllPostCategory(post, reqPutPostDTO.getCategoryId());

        //기존 파일 삭제
        postFileService.deleteAllPostFileByPostId(post.getId());

        //파일리스트 저장
        postFileService.saveAllPostFile(post, fileList);
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
