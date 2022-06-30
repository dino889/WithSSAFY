package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.Consultant;
import com.ssafy.withssafy.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultantRepository extends JpaRepository<Consultant,Long> {
}
