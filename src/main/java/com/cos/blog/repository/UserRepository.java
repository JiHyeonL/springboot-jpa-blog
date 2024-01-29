package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

// JpaRepository를 extends해서 UserRepository에서 모든 기능을 사용할 수 있게 함
// DAO 역할
// 자동으로 bean 등록이 된다. -> @Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer> {
    // SELECT * FROM user WHERE username = 1?;
    Optional<User> findByUsername(String username); // 변수명은 규칙이다
}


// JPA 네이밍 쿼리 전략
// 이렇게 메소드 이름을 정하면 SELECT * FROM user WHERE username = ?1 AND password =?2; 가 시행됨.
// 첫번째 물음표는 username, 두번째 물음표는 password (메소드의 각각 파라미터)
//    User findByUsernameAndPassword(String username, String password);

// 위의 코드와 같은 동작을 함. 이 코드는 네이티브 쿼리를 이용한 방법.
//    @Query(value = "SELECT * FROM user WHERE username = ?1 AND password =?2", nativeQuery = true)
//    User login(String username, String password);