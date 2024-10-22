package edu.du.sb1021_2.service;

import edu.du.sb1021_2.repository.Member;
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
