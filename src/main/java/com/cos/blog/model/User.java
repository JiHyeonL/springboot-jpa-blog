package com.cos.blog.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;

// ORM -> Java 뿐만 아니라 모든 언어 object를 테이블로 매핑해주는 기술
// 클래스를 테이블화 하는 어노테이션
@Entity // User 클래스가 필드를 읽어서 자동으로 MySQL에 테이블을 생성해줌
@Data
@NoArgsConstructor  // 파라미터가 없는 생성자 자동 생성
@AllArgsConstructor // 생성자 자동 생성
@Builder    // 빌더 패턴
// @DynamicInsert  // insert할 때 null인 필드를 제외시켜 준다.
public class User {

    @Id //Primary Key가 된다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
    private int id; // 시퀀스(오라클), auto_increment(MySQL) 전략 사용

    @Column(nullable=false, length=30, unique = true)
    private String username;    // 아이디

    @Column(nullable=false, length=100) // 나중에 비밀번호 해쉬로 암호화 할 것임
    private String password;

    @Column(nullable=false, length=50)
    private String email;

    // @ColumnDefault("user")
    // DB는 RoleType이란게 없다. 그래서 Enumerated를 통해 알려줘야 한다.
    @Enumerated(EnumType.STRING)
    private RoleType role;  // Enum 사용: ADMIN, USER

    @CreationTimestamp  // 시간이 자동 입력됨
    private Timestamp createDate;
}
