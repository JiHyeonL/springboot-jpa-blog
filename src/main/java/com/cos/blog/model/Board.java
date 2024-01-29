package com.cos.blog.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // ORM 클래스임을 명시(테이블에 매핑되는 클래스다)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 사용
    private int id;

    @Column(nullable = false, length=100)
    private String title;

    @Lob   // 대용량 데이터를 사용할 때 쓰는 어노테이션
    @Column(columnDefinition = "longtext")
    private String content; // 섬머노트 라이브러리 사용할 것. <html> 태그가 섞여서 디자인됨

    @ColumnDefault("0") // int니까 홑따옴표 없이 사용
    private int count;

    // fetch = FetchType.EAGER : 유저는 한건밖에 없으니 바로 join해서 가져온다
    @ManyToOne(fetch = FetchType.EAGER)  // 연관관계, Board = many, User = one. 한 명의 유저는 여러개의 개시글을 쓸 수 있다는 뜻.
    @JoinColumn(name="userId")  // userId라는 필드이름으로 저장된다.
    private User user;  // DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다.

    // 하나의 게시글에 달릴 수 있는 댓글은 여러개가 가능하기 때문에 리스트를 사용해 댓글 전체를 저장할 수 있게 함
    // fetch = FetchType.LAZY: 가져 올게 여러 건 이니까 필요 하면 들고 오고, 필요 하지 않으면 안 들고 온다.(EX: 댓글 펼치기 버튼을 눌러야 댓글이 보임)
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)  // mappedBy를 사용해서 연관관계의 주인이 아니다(난 FK가 아니다), DB에 컬럼을 만들지 말라고 설정한다.
    private List<Reply> reply;      // 그럼 FK는 누구? Reply 테이블의 board. reply는 join문을 통해 단순히 값을 얻을 때 사용할 것이다.

    @CreationTimestamp
    private Timestamp createDate;
}
