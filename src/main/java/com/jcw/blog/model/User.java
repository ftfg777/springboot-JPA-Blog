package com.jcw.blog.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@DynamicInsert insert 시에 null인 필드를 제외시켜줌
// ORM -> Java(다른 언어) Object -> 테이블로 매핑
@Entity //User Class가 Mysql에 테이블 생성됨
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라감
    private Long id; //시퀀스, auto_increment

    @NotBlank(message="NAME_IS_MANDATORY")
    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @NotBlank(message="PASSWORD_IS_MANDATORY")
    @Column(nullable = false)
    private String password;

    @Email(message = "NOT_VALID_EMAIL")
    @Column(nullable = false)
    private String emailAddress;

    //ColumnDefault("user)
    @Enumerated(EnumType.STRING)
    private RoleType role = RoleType.USER;

    @CreationTimestamp //데이터가 insert 될 때 시간 자동 입력
    private Timestamp createDate;

    private String oauth; //kakao, google 구분

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", role=" + role +
                ", createDate=" + createDate +
                ", oauth='" + oauth + '\'' +
                '}';
    }
}
