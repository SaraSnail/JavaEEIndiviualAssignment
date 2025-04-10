package com.example.assignmentindividualjavaee.services;

import com.example.assignmentindividualjavaee.entities.Member;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MemberService {
    List<Member>getAllMembers();
    Member getMemberById(Long id);
    ResponseEntity<String> updateMember(Member member, Long id);
    Member addMember(Member member);
    ResponseEntity<String> deleteMember(Long id);
    void deleteMemberById(Long id);
}
