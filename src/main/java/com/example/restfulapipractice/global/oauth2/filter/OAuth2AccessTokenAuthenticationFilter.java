package com.example.restfulapipractice.global.oauth2.filter;

import com.example.restfulapipractice.global.oauth2.authentication.AccessTokenSocialTypeToken;
import com.example.restfulapipractice.global.oauth2.SocialType;
import com.example.restfulapipractice.global.oauth2.provider.AccessTokenAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
@Slf4j
public class OAuth2AccessTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String DEFAULT_OAUTH2_LOGIN_REQUEST_URL_PREFIX = "/login/oauth2/";  // /login/oauth2/ + ????? 로 오는 요청을 처리할 것이다

    private static final String HTTP_METHOD = "GET";    //HTTP 메서드의 방식은 GET이다.

    private static final String ACCESS_TOKEN_HEADER_NAME = "Authorization";  //AccessToken을 해더에 보낼 때, 해더의 key는 Authorization이다.





    private static final AntPathRequestMatcher DEFAULT_OAUTH2_LOGIN_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(DEFAULT_OAUTH2_LOGIN_REQUEST_URL_PREFIX +"*", HTTP_METHOD); //=>   /oauth2/login/* 의 요청에, GET으로 온 요청에 매칭된다.

    public OAuth2AccessTokenAuthenticationFilter(AccessTokenAuthenticationProvider accessTokenAuthenticationProvider, AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler) { //로그인 실패 시 처리할 handler이다.

        super(DEFAULT_OAUTH2_LOGIN_PATH_REQUEST_MATCHER);   // 위에서 설정한  /oauth2/login/* 의 요청에, GET으로 온 요청을 처리하기 위해 설정한다.

        this.setAuthenticationManager(new ProviderManager(accessTokenAuthenticationProvider));
        //AbstractAuthenticationProcessingFilter를 커스터마이징 하려면  ProviderManager를 꼭 지정해 주어야 한다(안그러면 예외남!!!)

        this.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        this.setAuthenticationFailureHandler(authenticationFailureHandler);
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        SocialType socialType = extractSocialType(request);

        String accessToken = request.getHeader(ACCESS_TOKEN_HEADER_NAME); //헤더의 AccessToken에 해당하는 값을 가져온다.
        log.info("{}",socialType.getSocialName());


        return this.getAuthenticationManager().authenticate(new AccessTokenSocialTypeToken(accessToken, socialType));
    }


    private SocialType extractSocialType(HttpServletRequest request) {//요청을 처리하는 코드이다
        return Arrays.stream(SocialType.values())//SocialType.values() -> GOOGLE, KAKAO, NAVER 가 있다.
                .filter(socialType ->
                        socialType.getSocialName()
                                .equals(request.getRequestURI().substring(DEFAULT_OAUTH2_LOGIN_REQUEST_URL_PREFIX.length())))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 URL 주소입니다"));
    }
}