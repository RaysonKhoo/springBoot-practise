package com.example.demo.service;

import com.example.demo.repository.Course;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.StudentResInterface;
import com.example.demo.repository.StudentCourseResInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    public final com.example.demo.repository.CourseResInterface courseResInterface;
    @Autowired
    private StudentResInterface studentResInterface;
    @Autowired
    private StudentCourseResInterface studentCourseResInterface;


    public CourseService(com.example.demo.repository.CourseResInterface CourseResInterface, com.example.demo.repository.CourseResInterface courseResInterface) {
        this.courseResInterface = courseResInterface;
    }
    public void addNewCourse(com.example.demo.repository.Course course) {
        Optional<Course> courseOptional =courseResInterface.findCourseByCourseCode(course.getCourseCode());
        if (courseOptional.isPresent()) {
            throw new IllegalStateException("Course code already exists");
        }
        courseResInterface.save(course);
    }
    public List<com.example.demo.repository.Course> getAllCourses() {
        return courseResInterface.findAll();
    }
    public void deleteCourse(Integer courseId) {
        boolean exits = courseResInterface.existsById(courseId);
        if(!exits){
            throw new IllegalStateException("course with id " +  courseId + " does not exist");
        }
        courseResInterface.deleteById(courseId);
    }
    @Transactional
    public Course updateCourse( Integer courseId, Course courseUpdate) {
        Course course = courseResInterface.findById(courseId).orElseThrow(() -> new IllegalStateException("course with id " + courseId + " does not exist"));

        if (courseUpdate.getCourseDescription() != null && !courseUpdate.getCourseDescription().isEmpty() && !courseUpdate.getCourseDescription().equals(course.getCourseDescription())) {
            course.setCourseDescription(courseUpdate.getCourseDescription());
        }
        if (courseUpdate.getCourseLocation() != null && !courseUpdate.getCourseLocation().isEmpty() && !courseUpdate.getCourseLocation().equals(course.getCourseLocation())) {
            Optional<Course> courseOptional= courseResInterface.findCourseByCourseLocation(courseUpdate.getCourseLocation());
            if(courseOptional.isPresent()){
                throw new IllegalStateException("Course Location taken");
            }
            course.setCourseLocation(courseUpdate.getCourseLocation());
        }
        if (courseUpdate.getCourseName() != null && !courseUpdate.getCourseName().isEmpty() && !courseUpdate.getCourseName().equals(course.getCourseName())) {
            course.setCourseName(courseUpdate.getCourseName());
        }
        if (courseUpdate.getCourseCode() != null && !courseUpdate.getCourseCode().isEmpty() && !courseUpdate.getCourseCode().equals(course.getCourseCode())) {
            Optional<Course> courseOptional= courseResInterface.findCourseByCourseCode(courseUpdate.getCourseCode());
            if(courseOptional.isPresent()){
                throw new IllegalStateException("Course code is taken");
            }
            course.setCourseCode(courseUpdate.getCourseCode());
        }
        if (courseUpdate.getDay() != null && !courseUpdate.getDay().isEmpty() && !courseUpdate.getDay().equals(course.getDay())) {
            course.setDay(courseUpdate.getDay());
        }
        if (courseUpdate.getStartTime() != null && !courseUpdate.getStartTime().isEmpty() && !courseUpdate.getStartTime().equals(course.getStartTime())) {
            course.setStartTime(courseUpdate.getStartTime());
        }
        if (courseUpdate.getEndTime() != null && !courseUpdate.getEndTime().isEmpty() && !courseUpdate.getEndTime().equals(course.getEndTime())) {
            course.setEndTime(courseUpdate.getEndTime());
        }

            return courseResInterface.save(course);
        }
        public Map<String,Double> calculatePercentage(){
            long totalStudents = studentResInterface.countStudents();
            List<Object[]> percentage = studentCourseResInterface.countStudentsByCourse();

            Map<String,Double> coursePercentageMap = new HashMap<>();

            for (Object[] entry : percentage ){
                Integer courseId = (Integer) entry[0];
                long studentCount = (long) entry[1];
                Double percentages =(studentCount/(double) totalStudents)*100;

                courseResInterface.findById(courseId).ifPresent(course -> {
                    coursePercentageMap.put(course.getCourseName(),percentages);
                });
            }
            return coursePercentageMap;
        }
    }

