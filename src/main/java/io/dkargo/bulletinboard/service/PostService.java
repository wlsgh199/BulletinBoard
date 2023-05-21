package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.post.ReqCreatePostDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqFindOptionPostDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqUpdatePostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResCreatePostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResFindDetailPostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResFindOptionPostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResUpdatePostDTO;
import io.dkargo.bulletinboard.entity.Member;

import java.io.IOException;
import java.util.List;

public interface PostService {
    //게시물 아이디로 게시물 조회
    ResFindDetailPostDTO findDetailPostById(long id, long memberId, String boardPassword);

    //게시물 검색 조건으로 게시물 조회 (아무옵션없을시 전체 조회)
    List<ResFindOptionPostDTO> findPostByFindOptionDTO(ReqFindOptionPostDTO reqFindOptionPostDTO);

    //게시물 추가
    ResCreatePostDTO createPost(ReqCreatePostDTO reqCreatePostDTO, long memberId) throws IOException;

    //게시물 수정
    ResUpdatePostDTO updatePost(long postId, ReqUpdatePostDTO reqUpdatePostDTO, long memberId) throws IOException;

    //게시물 삭제
    void deletePost(long postId, long memberId);

}
