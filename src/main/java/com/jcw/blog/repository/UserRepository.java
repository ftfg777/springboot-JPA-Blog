package com.jcw.blog.repository;

import com.jcw.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// DAO
// 자동으로 bean 등록이 됨
public interface UserRepository extends JpaRepository<User, Long> {
}
