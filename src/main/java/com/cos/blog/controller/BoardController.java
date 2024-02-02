package com.cos.blog.controller;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping({"", "/"})
    public String index(Model model, @PageableDefault(size=3,sort="id",direction= Sort.Direction.DESC) Pageable pageable) { // @AuthenticationPrincipal PrincipalDetail principal
        // model -> request 정보. 모델에 정보를 담으면 뷰(index.html)까지 데이터를 끌고감
        model.addAttribute("boards", boardService.글목록(pageable));   // 게시글 id 기준으로 최근 게시글이 제일 위에있는 pageable을 글목록에 전달
        return "index"; // viewResolver 작동! 글목록이 인덱스 페이지(index.html)로 넘어감
    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable int id, Model model) {
        model.addAttribute("board", boardService.글상세보기(id));
        return "board/detail";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model) {
        model.addAttribute("board", boardService.글상세보기(id));
        return "board/updateForm";
    }

    // user 권한이 필요
    @GetMapping("/board/saveForm")
    public String saveFrom() {
        return "board/saveForm";
    }
}
