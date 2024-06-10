package com.example.demo.controller;


import com.example.demo.repository.Course;
import com.example.demo.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path ="api/v1/course")
public class courseController {
    @Autowired
    private CourseService course;

    @Autowired

    private CourseService courseService;

    @PostMapping
    public void addCourse(@Valid @RequestBody Course course) {
        String courseCode = course.getCourseCode();
        if (courseCode == null || courseCode.isEmpty()) {
            throw new IllegalStateException("Course name cannot be null or empty");
        }
        courseService.addNewCourse(course) ;
    }
    @GetMapping
    public List<Course> getAllCourses() {
        return course.getAllCourses();
    }
    @DeleteMapping(path = "{courseId}")
    public void deleteCourse(@PathVariable("courseId") Integer courseId) {courseService.deleteCourse(courseId);}
    @PutMapping(path = "/{courseId}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable("courseId") Integer courseId,
            @RequestBody Course courseUpdate) {
        Course updatedCourse = courseService.updateCourse(courseId, courseUpdate);
        return ResponseEntity.ok(updatedCourse);
    }
    @GetMapping("/percentage")
    public ResponseEntity<Map<String, Double>> getPercentage(){
    Map<String,Double> percentage = courseService.calculatePercentage();
    return  ResponseEntity.ok(percentage);
    }
}
