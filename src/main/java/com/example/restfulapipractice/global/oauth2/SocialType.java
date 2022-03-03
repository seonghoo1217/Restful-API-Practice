package com.example.restfulapipractice.global.oauth2;

import org.springframework.http.HttpMethod;

public enum SocialType {


    KAKAO(
            "kakao",
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.GET
    ),

    NAVER(
            "naver",
            "https://openapi.naver.com/v1/nid/me",
            HttpMethod.GET
    );



    private String socialName;
    private String userInfoUrl;
    private HttpMethod method;

    SocialType(String socialName, String userInfoUrl, HttpMethod method) {
        this.socialName = socialName;
        this.userInfoUrl = userInfoUrl;
        this.method = method;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getSocialName() {
        return socialName;
    }

    public String getUserInfoUrl() {
        return userInfoUrl;
    }
}
