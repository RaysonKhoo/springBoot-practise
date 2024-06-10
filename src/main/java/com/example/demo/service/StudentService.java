package com.example.demo.service;

import com.example.demo.repository.Course;
import com.example.demo.repository.Student;
import com.example.demo.repository.StudentCourse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
   private final com.example.demo.repository.StudentResInterface studentResInterface;

    @Autowired
    private com.example.demo.repository.CourseResInterface courseResInterface;
    @Autowired
    private com.example.demo.repository.StudentCourseResInterface studentCourseResInterface;
    @Autowired
    public StudentService(com.example.demo.repository.StudentResInterface studentResInterface) {
        this.studentResInterface = studentResInterface;
    }
    public List<com.example.demo.repository.Student> getStudents() {
        return studentResInterface.findAll();
    }

    public void addNewStudent(com.example.demo.repository.Student student) {
        Optional<Student> studentOptional = studentResInterface.findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }
        studentResInterface.save(student);
    }
    public void deleteStudent(Integer studentId) {
       boolean exits= studentResInterface.existsById(studentId);
       if(!exits){
           throw new IllegalStateException("student with id " +  studentId + " does not exist");

       }
       studentResInterface .deleteById(studentId);
    }
    @Transactional
    public void updateStudent(Integer studentId, String name, String email) {
        Student student = studentResInterface.findById(studentId).orElseThrow(() -> new IllegalStateException("Student with id " +  studentId + " does not exist"));
        if(name !=null && !name.isEmpty() && !name.equals(student.getName())){
            student.setName(name);
        }
        if(email !=null && !email.isEmpty() && !email.equals(student.getEmail())){
            Optional<Student> studentOptional = studentResInterface.findStudentByEmail(email);
            if(studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            student.setEmail(email);
        }

    }
    public StudentCourse enrollStudentCourse(Integer studentId, Integer courseId) {
        Student student = studentResInterface.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " does not exist"));
        Course course = courseResInterface.findById(courseId)
                .orElseThrow(() -> new IllegalStateException("Course with id " + courseId + " does not exist"));

        Optional<StudentCourse> existingEnrollment = studentCourseResInterface.findByStudentAndCourse(student, course);
        if (existingEnrollment.isPresent()) {
            throw new IllegalStateException("Student is already enrolled in this course");
        }

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudent(student);
        studentCourse.setCourse(course);
        return studentCourseResInterface.save(studentCourse);
    }

    public List<Student> getStudentsByCourseId(Integer courseId) {
        return studentResInterface.findStudentsByCourseId(courseId);
    }
@Transactional
    public StudentCourse editEnrollStudentCourse(Integer studentCourseId, StudentCourse updateCourse) {
        StudentCourse existingStudentCourse = studentCourseResInterface.findById(studentCourseId)
                .orElseThrow(() -> new IllegalStateException("StudentCourse with id " + studentCourseId + " does not exist"));
        Course newCourse = courseResInterface.findById(updateCourse.getCourse().getId())
                .orElseThrow(() -> new IllegalStateException("Course with code " + updateCourse.getCourse().getId() + " does not exist"));
    // Check if the student already has this course
        boolean courseAlreadyEnrolled = existingStudentCourse.getStudent().getStudentCourses().stream()
                .anyMatch(sc -> sc.getCourse().getId() == newCourse.getId());

    if (courseAlreadyEnrolled) {
        throw new IllegalStateException("Student is already enrolled in the course with id " + newCourse.getId());
    }
        existingStudentCourse.setCourse(newCourse);
        return studentCourseResInterface.save(existingStudentCourse);
    }
}
