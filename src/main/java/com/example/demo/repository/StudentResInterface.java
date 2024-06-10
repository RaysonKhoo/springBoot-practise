package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentResInterface extends JpaRepository<Student, Integer> {
        @Query("SELECT s FROM Student s WHERE s.email =?1")
        Optional<Student> findStudentByEmail(String email);
        @Query("SELECT sc.student FROM StudentCourse sc WHERE sc.course.id = :courseId")
        List<Student> findStudentsByCourseId(Integer courseId);
        @Query("SELECT COUNT(s) FROM Student s")
        long countStudents();

}
