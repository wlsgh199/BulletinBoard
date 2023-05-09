package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.post.*;
import io.dkargo.bulletinboard.dto.response.post.ResFindOptionPostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResFindDetailPostDTO;

import java.io.IOException;
import java.util.List;

public interface PostService {
    //게시물 아이디로 게시물 조회
    ResFindDetailPostDTO findDetailPostById(long id, long userId, String password);
    //게시물 검색 조건으로 게시물 조회 (아무옵션없을시 전체 조회)
    List<ResFindOptionPostDTO> findPostByFindOptionDTO(ReqFindOptionPostDTO reqFindOptionPostDTO);
    //게시물 추가
    void addPost(ReqAddPostDTO reqAddPostDTO) throws IOException;
    //게시물 수정
    void putPost(ReqPutPostDTO reqPutPostDTO) throws IOException;
    //게시물 부분 수정
    void patchPost(ReqPatchPostDTO reqPatchPostDTO) throws IOException;
    //게시물 삭제
    void deletePost(ReqDeletePostDTO reqDeletePostDTO);

}
