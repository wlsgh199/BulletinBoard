package io.dkargo.bulletinboard.service.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.dkargo.bulletinboard.dto.request.ReqPostDTO;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.repository.PostRepository;
import io.dkargo.bulletinboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public Post findPostById(Long id) {

//        Post post = postRepository.findById(id).orElseThrow(IllegalAccessError::new);
//        System.out.println("post.getPostCategoryList() = " + post.getPostCategoryList());
        return postRepository.findById(id).orElseThrow(IllegalAccessError::new);
    }


    @PersistenceContext
    EntityManager em;

    public List<Post> findPostByMemberId(Long id) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

//        jpaQueryFactory.selectFrom()
//        return postRepository.findPostByMemberId(id);
        return null;
    }

    @Override
    public Post savePost(Member member, ReqPostDTO reqPostDTO) {
        //게시글 저장
        Post post = new Post(member, reqPostDTO);
        postRepository.save(post);

        return post;
    }
}
