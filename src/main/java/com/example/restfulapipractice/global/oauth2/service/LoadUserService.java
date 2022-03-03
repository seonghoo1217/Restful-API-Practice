package com.example.restfulapipractice.global.oauth2.service;

import com.example.restfulapipractice.global.oauth2.SocialType;
import com.example.restfulapipractice.global.oauth2.authentication.AccessTokenSocialTypeToken;
import com.example.restfulapipractice.global.oauth2.authentication.OAuth2UserDetails;
import com.example.restfulapipractice.global.oauth2.service.strategy.KakaoLoadStrategy;
import com.example.restfulapipractice.global.oauth2.service.strategy.NaverLoadStrategy;
import com.example.restfulapipractice.global.oauth2.service.strategy.SocialLoadStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class LoadUserService {

    private final RestTemplate restTemplate = new RestTemplate();


    public OAuth2UserDetails getOAuth2UserDetails(AccessTokenSocialTypeToken authentication)  {

        SocialType socialType = authentication.getSocialType();

        SocialLoadStrategy socialLoadStrategy = getSocialLoadStrategy(socialType);//SocialLoadStrategy 설정

        String socialPk = socialLoadStrategy.getSocialPk(authentication.getAccessToken());//PK 가져오기

        return OAuth2UserDetails.builder() //PK와 SocialType을 통해 회원 생성
                .socialId(socialPk)
                .socialType(socialType)
                .build();
    }

    private SocialLoadStrategy getSocialLoadStrategy(SocialType socialType) {
        if(socialType==SocialType.KAKAO){
            return new KakaoLoadStrategy();
        }else if(socialType==SocialType.NAVER){
            return new NaverLoadStrategy();
        }else {
            throw new IllegalArgumentException("지원하지않는 로그인 형식입니다");
        }
    }


}
