package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom,Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE FROM tbl_classroom SET generation=:generation, area=:area, classDescription=:classDescription WHERE id=:id", nativeQuery = true)
    void update(Long id, int generation, String area, String classDescription);
}
