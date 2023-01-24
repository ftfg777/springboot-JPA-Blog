package com.jcw.blog.controller;

import com.jcw.blog.auth.PrincipalDetail;
import com.jcw.blog.model.Board;
import com.jcw.blog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //@AuthenticationPrincipal PrincipalDetail principal
    @GetMapping({"", "/"})
    public String index(Model model,
                        @PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
        Page<Board> boards = boardService.글목록(pageable);
//        List<Board> boards = pagingBoard.getContent();

        model.addAttribute("boards", boards);
        return "index";
    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable Long id, Model model){
        Board board = boardService.글상세보기(id);
        model.addAttribute("board", board);

        return "board/detail";
    }

    @GetMapping("/board/saveForm")
    public String saveForm(){
        return "board/saveForm";
    }

}