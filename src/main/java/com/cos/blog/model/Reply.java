package com.cos.blog.model;

import javax.persistence.*;

import com.cos.blog.dto.ReplySaveRequestDto;
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

    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", board=" + board +
                ", user=" + user +
                ", createDate=" + createDate +
                '}';
    }
// BoardService 댓글쓰기에서 이 메소드를 이용하면
    // Reply reply = new Reply();
    // reply.update(user, board, replySaveRequestDto.getContent()); 로 한번에 업데이트 할 수 있다.
//    public void update(User user, Board board, String content) {
//        setUser(user);
//        setBoard(board);
//        setContent(content);
//    }
}
