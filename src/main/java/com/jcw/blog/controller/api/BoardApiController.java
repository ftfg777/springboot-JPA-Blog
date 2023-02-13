package com.jcw.blog.controller.api;

import com.jcw.blog.auth.PrincipalDetail;
import com.jcw.blog.dto.ReplySaveRequestDto;
import com.jcw.blog.dto.ResponseDto;
import com.jcw.blog.model.Board;
import com.jcw.blog.model.Reply;
import com.jcw.blog.model.User;
import com.jcw.blog.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BoardApiController {
    private final BoardService boardService;

    @GetMapping("/api/board/{id}")
    public Board getOneId(@PathVariable Long id){
        return boardService.글상세보기(id);
    }

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal){
        System.out.println("BoardApiController save 호출");

        boardService.글작성(board, principal.getUser());

        System.out.println("BoardApiController save 끝");
        return new ResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK, 1);
    }


    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> delete(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetail principal){
        System.out.println("BoardApiController delete 호출");

        boardService.글삭제(id, principal.getUser());

        System.out.println("BoardApiController delete 끝");
        return new ResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK, 1);
    }

    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable Long id, @RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal){
        System.out.println("BoardApiController put 호출");

        boardService.글수정(id, board, principal.getUser());



        System.out.println("BoardApiController put 끝");
        return new ResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK, 1);
    }

    // 데이터를 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다.
    // 이 프로젝트에서 dto 사용하지 않은 이유는 규모가 작기 때문에
    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto){
        System.out.println("BoardApiController replySave 호출");

        boardService.댓글작성(replySaveRequestDto);

        System.out.println("BoardApiController replySave 끝");
        return new ResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK, 1);
    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> replyDelete(@PathVariable Long replyId, @AuthenticationPrincipal PrincipalDetail principal){
        System.out.println("BoardApiController replyDelete 호출");

        boardService.댓글삭제(replyId, principal.getUser());

        System.out.println("BoardApiController replyDelete 끝");
        return new ResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK, 1);
    }

}
