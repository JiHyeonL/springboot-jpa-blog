package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository를 extends해서 UserRepository에서 모든 기능을 사용할 수 있게 함
// DAO 역할
// 자동으로 bean 등록이 된다. -> @Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer> {


}
