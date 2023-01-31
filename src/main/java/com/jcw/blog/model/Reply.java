package com.jcw.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String content;


    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;


    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @CreationTimestamp
    private Timestamp createDate;
}
