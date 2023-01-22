package com.jcw.blog.controller.api;

import com.jcw.blog.auth.PrincipalDetail;
import com.jcw.blog.dto.ResponseDto;
import com.jcw.blog.model.Board;
import com.jcw.blog.model.User;
import com.jcw.blog.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BoardApiController {
    private final BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal){
        System.out.println("BoardApiController save 호출");

        boardService.글작성(board, principal.getUser());

        System.out.println("BoardApiController save 끝");
        return new ResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK, 1);

    }
}
