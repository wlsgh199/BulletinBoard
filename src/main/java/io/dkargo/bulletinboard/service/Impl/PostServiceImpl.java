package io.dkargo.bulletinboard.service.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.dkargo.bulletinboard.dto.request.ReqPostDTO;
import io.dkargo.bulletinboard.dto.response.ResPostDTO;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.entity.QPost;
import io.dkargo.bulletinboard.entity.QPostFile;
import io.dkargo.bulletinboard.repository.PostRepository;
import io.dkargo.bulletinboard.repository.support.PostRepositorySupport;
import io.dkargo.bulletinboard.service.FileService;
import io.dkargo.bulletinboard.service.MemberService;
import io.dkargo.bulletinboard.service.PostCategoryService;
import io.dkargo.bulletinboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostRepositorySupport postRepositorySupport;
    private final JPAQueryFactory queryFactory;

    private final PostCategoryService postCategoryService;
    private final MemberService memberService;
    private final FileService fileService;
    @Override
    public List<ResPostDTO> findAllPost(Pageable pageable) {
        return postRepositorySupport.findAllPost(pageable)
                .stream()
                .map(ResPostDTO::new)
                .collect(Collectors.toList());
    }

//    @Override
//    public ResPostDTO findPostById(Long id) {
//        return postRepositorySupport.findPostById(id)
//                .stream()
//                .map(ResPostDTO::new)
//                .collect(Collectors.toList());
//    }

    @Override
    public List<ResPostDTO> findPostByMemberId(Long memberId, Pageable pageable) {
        return postRepositorySupport.findPostByMemberId(memberId, pageable)
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
    public Post savePost(ReqPostDTO reqPostDTO, List<MultipartFile> fileList) throws IOException{

        //Member 조회
        Member member = memberService.findMemberById(reqPostDTO.getUserId());

        //게시글 저장
        Post post = new Post(member, reqPostDTO);
        postRepository.save(post);

        //게시글 * 카테고리 뎁스만큼 저장
        postCategoryService.saveAllPostCategory(post, reqPostDTO.getCategoryId());

        //파일 있을시 저장
        if (fileList != null && !fileList.isEmpty()) {
            //파일 개수 3개 이상 x
//            if (fileCount < 3) {
//                // error
//            }
            fileService.saveAllFile(post, fileList);
        }

        return post;
    }
}
