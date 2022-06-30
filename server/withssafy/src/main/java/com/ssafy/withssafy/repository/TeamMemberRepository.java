package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember,Long> {
}
