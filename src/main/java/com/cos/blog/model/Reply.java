package com.cos.blog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Reply {

    @Id //Primary Key가 된다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 200)
    private String content; // 섬머노트 라이브러리 사용할 것. <html> 태그가 섞여서 디자인됨

    @ManyToOne  // 하나의 게시글에는 여러개의 답변이 있어도 된다.
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne  // 여러개의 답변을 하나의 유저가 쓸 수 있다.
    @JoinColumn(name = "userId")
    private User user;

    @CreationTimestamp
    private Timestamp createDate;
}
