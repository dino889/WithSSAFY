package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.ClassManager;
import com.ssafy.withssafy.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassManagerRepository extends JpaRepository<ClassManager,Long> {
}
