package com.project.zoopiter.web;

import com.project.zoopiter.domain.bbsc.svc.BbscSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/bbsc")
@RequiredArgsConstructor
public class BbscController {

    private final BbscSVC bbscSVC;

    // 커뮤니티 게시판 목록
    @GetMapping("/list")
    public String listForm(){
        return "community/board_com";
    }





}
