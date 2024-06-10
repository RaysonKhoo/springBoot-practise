package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseResInterface extends JpaRepository<Course, Integer> {
    @Query("SELECT s FROM Course s WHERE s.courseCode =?1")
    Optional<Course> findCourseByCourseCode(String courseCode);

    @Query("SELECT s FROM Course s WHERE s.courseLocation =?1")
    Optional<Course> findCourseByCourseLocation(String courseLocation);
}
