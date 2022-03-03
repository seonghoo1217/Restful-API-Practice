package com.example.restfulapipractice.controller;

import com.example.restfulapipractice.service.OAuthService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
@Log4j2
public class OauthController {

    @Autowired
    private OAuthService oAuthService;

    @ResponseBody
    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam String code) throws Exception {
        log.info("code={}",code);
        String kakaoAccessToken = oAuthService.getKakaoAccessToken(code);
        oAuthService.createKakaoUser(kakaoAccessToken);
    }
}
