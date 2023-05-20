package com.gnilapon.anywr.group.repositories;

import com.gnilapon.anywr.group.models.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends JpaRepository<Student,String> {
    Page<Student> findByClassroomNameContaining(String name, Pageable pageable);
    @Query("SELECT e FROM Student e WHERE :fullName IS NULL OR CONCAT(e.classroom.teacher.lName, e.classroom.teacher.fName)  LIKE CONCAT('%', :fullName, '%')")
    Page<Student> findByClassroomTeacherFullNameContaining(@Param("fullName")String fullName,Pageable pageable);
    @Query("SELECT e FROM Student e WHERE (:name IS NULL OR e.classroom.name LIKE CONCAT('%', :name, '%')) AND (:fullName IS NULL OR  CONCAT(e.classroom.teacher.lName, e.classroom.teacher.fName)  LIKE CONCAT('%', :fullName, '%'))")
    Page<Student> findByClassroomNameAndClassroomTeacherFullName(@Param("name")String name, @Param("fullName")String fullName, Pageable pageable);
}
