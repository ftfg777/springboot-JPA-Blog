package com.jcw.blog.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
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

    @NotBlank(message="아이디를 입력해주세요.")
    @Size(min = 2, message = "2글자 이상 입력해주세요.")
    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @NotBlank(message="비밀번호를 입력해주세요.")
    @Size(min = 8, message = "8글자 이상 입력해주세요.")
    @Column(nullable = false)
    private String password;

    @Email(message = "올바른 이메일 형식이 아닙니다.")
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
