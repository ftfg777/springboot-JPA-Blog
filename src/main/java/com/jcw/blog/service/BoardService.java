package com.jcw.blog.service;

import com.jcw.blog.model.Board;
import com.jcw.blog.model.User;
import com.jcw.blog.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true) //select 할 때 트랜잭션 시작, 서비스 종료시에 트랜잭셩 종료(정합성 유지)
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void 글작성(Board board, User user) {
        System.out.println("BoardService.글작성 호출");

        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);

        System.out.println("BoardService.글작성 끝");
    }

    public Page<Board> 글목록(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);
        return boards;
    }
}
