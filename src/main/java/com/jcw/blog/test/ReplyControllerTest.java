package com.jcw.blog.test;

import com.jcw.blog.model.Board;
import com.jcw.blog.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReplyControllerTest {
    @Autowired
    private BoardRepository boardRepository;
    @GetMapping("/test/board/{id}")
    public Board getBoard(@PathVariable Long id) {
        return boardRepository.findById(id).get(); //jackson 라이브러리 발동 (오브젝트를 json으로 리턴)
    }
}
