package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

// restcontroller는 html 파일이 아니라 데이터를 반환하는 컨트롤러이다.
@RestController
public class DummyControllerTest {
    // Autowired를 사용하면 DummyControllerTest가 메모리에 뜰 때 UserRepository 객체도 뜬다.
    @Autowired  // 의존성 주입(DI)
    private UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable("id") int id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
        }

        return "삭제되었습니다. id:" + id;
    }
    // save 함수는 id를 전달하지 않으면 insert를 해주고
    // id를 전달하면 해당 id에 대한 데이터가 있을 경우 update 하고
    // 해당 id에 대한 데이터가 없을 경우 insert를 한다.
    // email, password 받아야 함
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable("id") int id, @RequestBody User requsetUser) {   // json 데이터를 요청 -> java 오브젝트(MessageConverter의 jackson라이브러리가 변환해서 받아줌)
        System.out.println("id : " + id);
        System.out.println("password : " + requsetUser.getPassword());
        System.out.println("email : " + requsetUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("수정에 실패하였습니다");
        });
        // 이런 방식으로 user 정보를 수정하면 null이 생기지 않는다.
        user.setPassword(requsetUser.getPassword());
        user.setEmail(requsetUser.getEmail());
//        user.setId(requsetUser.getId());
//        user.setUsername(requsetUser.getUsername());

//        requsetUser.setId(id);
//        requsetUser.setUsername("sdfasdgawgasd");   // 위 id의 이름을 이것으로 바꿔줌. 이 방법은 null이 생긴다
//        userRepository.save(requsetUser);   // save는 id가 있으면 update하고, 없으면 insert한다.
        // 더티 체킹 -> 변경 감지(db와 jpa에서 더티체킹의 의미가 다름)
        return user;
    }

    // http://localhost:8000/blog/dummy/user
    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();    // 모든 데이터 보기
    }

    // 한 페이지당 두 건의 데이터를 리턴
    // 2개 데이터를 id 기준으로 내림차순으로 보여줌
    @GetMapping("/dummy/user")  // size = 2라는 뜻은 한 페이지당 데이터 2개를 보여준다는 뜻
    public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        Page<User> pagingUser = userRepository.findAll(pageable);

        // if (pagingUser.isLast()) {} 와 같이 Page는 다양한 메소드 제공한다.
        List<User> users = pagingUser.getContent();
        return users;
    }

    // {id} 주소로 파라미터를 전달받을 수 있음
    // http://localhost:8000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {  // 매핑에 썼던 것과 같은 변수명으로 해야한다(id)
        // findById의 리턴 타입은 optional이다. 이유?
        // 만약 user/4로 검색했는데 데이터베이스에서 해당 id를 못찾아오면 user가 null이 된다.
        // 그럼 return null이 되기 때문에 Optional로 User 객체를 감싸서 가져온다.
        // 그럼 내가 null인지 아닌지 판단해서 return할 수 있따.
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            // Supplier를 들어가보면 Supplier는 추상메소드(인터페이스)이고, get 메소드를 갖고있다.
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 없습니다. id: " + id);
            }
        });
        // Supplier 인터페이스는 단일추상메소드인 get을 갖기 때문에 람다를 사용해도 됨.
        // orElseThrow(() -> { return new IllegalArguementException("해당 사용자는 없습니다.");});

        // 요청: 웹브라우저
        // user 객체 = 자바 오브젝트
        // 그래서 자바 오브젝트를 웹 브라우저기 이해할 수 있는 데이터로 변환하기 위해 json을 사용한다.
        // 스트링부트는 MessageConverter가 응답시에 자동 작동해서 Jackson 라이브러리를 호출한다. -> user 오브젝트를 json으로 변환해 브라우저에게 던진다.
        return user;
    }

    // http://localhost:8000/blog/dummy/join (요청)
    // http의 body에 username, password, email 데이터를 가지고 요청
    @PostMapping("/dummy/join")
    // key = value 형태의 데이터를 받는다.(약속된 규칙)
    public String join(User user) {
        System.out.println("id:" + user.getId());
        System.out.println("username:" + user.getUsername());
        System.out.println("password:" + user.getPassword());
        System.out.println("email:" + user.getEmail());
        System.out.println("role:" + user.getRole());
        System.out.println("createDate:" + user.getCreateDate());

        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}

