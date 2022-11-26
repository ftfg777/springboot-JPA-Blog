package com.jcw.blog.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터 처리
    private String content; // 섬머노트 라이브러리 사용

    @ColumnDefault("0")
    private int count;      //조회수

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;      //DB는 오브젝트를 저장할 수 없음

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER) //mappedBy 연관관계의 주인이 아니다 (난 FK가 아님) DB에 칼럼을 만들지 마
    // EAGER = 한 번에 데이터 가져오기
    private List<Reply> reply;

    @CreationTimestamp      // insert 혹은 update 시간 자동 저장
    private Timestamp createDate;

}
