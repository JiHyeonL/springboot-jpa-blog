package com.cos.blog;

import com.cos.blog.model.Reply;
import org.junit.jupiter.api.Test;

public class ReplyObjectTest {

    @Test
    public void toString테스트() {
        Reply reply = Reply.builder()
                .id(1)
                .user(null)
                .board(null)
                .content("안녕")
                .build();

        System.out.println(reply);  // 오브젝트 출력시 toString이 자동으로 호출된다.
    }
}
