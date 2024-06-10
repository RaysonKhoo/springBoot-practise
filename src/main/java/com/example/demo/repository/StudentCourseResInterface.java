package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentCourseResInterface extends JpaRepository<StudentCourse, Integer> {
    Optional<StudentCourse> findByStudentAndCourse(Student student, Course course);
    @Query("SELECT sc.course.id, COUNT(sc.student.id) FROM StudentCourse sc GROUP BY sc.course.id")
    List<Object[]> countStudentsByCourse();

}
