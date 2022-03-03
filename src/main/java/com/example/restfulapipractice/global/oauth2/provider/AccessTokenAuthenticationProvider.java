package com.example.restfulapipractice.global.oauth2.provider;

import com.example.restfulapipractice.global.oauth2.authentication.AccessTokenSocialTypeToken;
import com.example.restfulapipractice.global.oauth2.authentication.OAuth2UserDetails;
import com.example.restfulapipractice.global.oauth2.service.LoadUserService;
import com.example.restfulapipractice.model.Member;
import com.example.restfulapipractice.model.Role;
import com.example.restfulapipractice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccessTokenAuthenticationProvider implements AuthenticationProvider {//AuthenticationProvider을 구현받아 authenticate와 supports를 구현해야 한다.

    private final LoadUserService loadUserService;  //restTemplate를 통해서 AccessToken을 가지고 회원의 정보를 가져오는 역할을 한다.
    private final MemberRepository memberRepository;//받아온 정보를 통해 DB에서 회원을 조회하는 역할을 한다.


    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        OAuth2UserDetails oAuth2User = loadUserService.getOAuth2UserDetails((AccessTokenSocialTypeToken) authentication);



        Member member = saveOrGet(oAuth2User);
        oAuth2User.setRoles(member.getRole().name());

        return AccessTokenSocialTypeToken.builder().principal(oAuth2User).authorities(oAuth2User.getAuthorities()).build();

    }


    private Member saveOrGet(OAuth2UserDetails oAuth2User) {
        return memberRepository.findBySocialTypeAndSocialId(oAuth2User.getSocialType(),
                        oAuth2User.getSocialId())  //socailID(식별값)과 어떤 소셜 로그인 유형인지를 통해 DB에서 조회
                .orElseGet(() -> memberRepository.save(Member.builder()
                        .socialType(oAuth2User.getSocialType())
                        .socialId(oAuth2User.getSocialId())
                        .role(Role.GUEST).build()));//없다면 멤버를 새로 만드는데, USER가 아니라 GUEST로 설정했다. 이는 아래해서 설명한다
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return AccessTokenSocialTypeToken.class.isAssignableFrom(authentication); //AccessTokenSocialTypeToken타입의  authentication 객체이면 해당 Provider가 처리한다.
    }
}
