package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.ReqPostDTO;

public interface PostService {
    void savePost(ReqPostDTO reqPostDTO);
}
