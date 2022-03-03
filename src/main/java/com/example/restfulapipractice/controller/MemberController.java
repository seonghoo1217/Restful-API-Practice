package com.example.restfulapipractice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    /*private final MemberRepository memberRepository;

    @GetMapping("/test")
    public Member memberTest(){
        return new Member(0L,"Leetc",24,"하남",new Date());
    }

    @GetMapping("/test2")
    public ArrayList<Member> memberTest2(){
        return new ArrayList(Arrays.asList(
                new Member(1L,"dummy1",21,"강원",new Date()),
                new Member(2L,"dummy2",22,"경기",new Date()),
                new Member(3L,"dummy3",23,"서울",new Date()),
                new Member(4L,"dummy4",24,"전북",new Date()),
                new Member(5L,"dummy5",25,"경남",new Date())
        ));
    }

    @GetMapping("/insert") // CREATE
    public Member insert(){
        return memberRepository.save(
                new Member("Leeseongho", 24, "하남",new Date())
        );
    }*/
}
