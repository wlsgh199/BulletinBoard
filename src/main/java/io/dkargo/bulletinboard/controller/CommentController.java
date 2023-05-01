//package io.dkargo.bulletinboard.controller;
//
//import io.dkargo.bulletinboard.dto.request.ReqCommentDTO;
//import io.dkargo.bulletinboard.dto.response.ResCommentReplyDTO;
//import io.dkargo.bulletinboard.dto.response.ResReplyDTO;
//import io.dkargo.bulletinboard.service.CommentService;
//import io.swagger.annotations.ApiOperation;
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.web.bind.annotation.*;
//
//
//import javax.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.List;
//
//
//@RestController
//@RequestMapping("/comments")
//@RequiredArgsConstructor
//@Transactional
//public class CommentController {
//    private final CommentService commentService;
//
//    @ApiOperation(value = "댓글 등록")
//    @PostMapping(value = "")
//    public void addComment(@RequestBody ReqCommentDTO reqCommentDTO) {
//        System.out.println("reqCommentDTO.getContent() = " + reqCommentDTO.getContent());
//        commentService.addComment(reqCommentDTO);
//    }
//
//    @ApiOperation(value = "댓글/답글 조회")
//    @GetMapping(value = "")
//    public List<ResCommentReplyDTO> findCommentReplyByPostId() {
//        List<ResCommentReplyDTO> resCommentReplyDTOList = new ArrayList<>();
//        ResCommentReplyDTO resCommentReplyDTO = new ResCommentReplyDTO();
//        resCommentReplyDTO.setId(1L);
//        resCommentReplyDTO.setContent("댓글 내용");
//        resCommentReplyDTO.setMemberId(1L);
//
//        List<ResReplyDTO> resReplyDTOList = new ArrayList<>();
//        ResReplyDTO resReplyDTO = new ResReplyDTO();
//        resReplyDTO.setId(1L);
//        resReplyDTO.setContent("답글 내용");
//        resReplyDTO.setUserId(1L);
//        resReplyDTOList.add(resReplyDTO);
//
//
//        ResReplyDTO resReplyDTO2 = new ResReplyDTO();
//        resReplyDTO2.setId(1L);
//        resReplyDTO2.setContent("답글22222 내용");
//        resReplyDTO2.setUserId(1L);
//        resReplyDTOList.add(resReplyDTO2);
//
//        resCommentReplyDTO.setResReplyDTOList(resReplyDTOList);
//        resCommentReplyDTOList.add(resCommentReplyDTO);
//        return resCommentReplyDTOList;
//    }
//}
