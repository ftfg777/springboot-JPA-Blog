package com.jcw.blog.model;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

// ORM -> Java(다른 언어) Object -> 테이블로 매핑
@Entity //User Class가 Mysql에 테이블 생성됨
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라감
    private Long id; //시퀀스, auto_increment

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String emailAddress;

    @Enumerated(EnumType.STRING)
    private ROLE role = ROLE.USER;

    @CreationTimestamp //데이터가 insert 될 때 시간 자동 입력
    private Timestamp createDate;

}
