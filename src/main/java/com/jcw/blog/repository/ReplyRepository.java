package com.jcw.blog.repository;

import com.jcw.blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying // db 업데이트 할 때 사용
    @Query(value = "INSERT INTO reply (userId, boardId, content, createDate) VALUES (?1, ?2, ?3, now())", nativeQuery = true)
    void mSave(Long userId, Long boardId, String content);
}
