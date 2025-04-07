package com.example.assignmentindividualjavaee.repositories;

import com.example.assignmentindividualjavaee.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
