package edu.du.sb1023_prob.service;


import edu.du.sb1023_prob.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository {
    void insert(Member member);
    void update(Member member);
//    void delete(Long uid);
    List<Member> selectAll();
    Member selectByEmail(String email);
    int userCount();
}
