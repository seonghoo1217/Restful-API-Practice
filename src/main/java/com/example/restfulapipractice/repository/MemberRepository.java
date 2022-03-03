package com.example.restfulapipractice.repository;

import com.example.restfulapipractice.model.Member;
import com.example.restfulapipractice.model.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findBySocialTypeAndSocialId(SocialType socialType,String socialId);
}
