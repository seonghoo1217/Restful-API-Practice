package com.example.restfulapipractice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
@Log4j2
public class OauthController {

    @ResponseBody
    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam String code){
        log.info("code={}",code);
    }
}
