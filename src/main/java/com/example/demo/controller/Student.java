package com.example.demo.controller;

import com.example.demo.repository.StudentCourse;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(path="api/v1/student")
public class Student {

    @Autowired
    private StudentService student;
    @Autowired
    private StudentService studentService;


    @GetMapping
    public List<com.example.demo.repository.Student> getStudents() {
        return student.getStudents();
    }
    @PostMapping
    public void  registerStudent(@RequestBody com.example.demo.repository.Student student) {studentService.addNewStudent(student);}
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Integer studentId ) { studentService.deleteStudent(studentId);}
    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestParam(required = false) String name, @RequestParam(required = false) String email){studentService.updateStudent(studentId,name,email);}
    @PostMapping(path = "{studentId}/{courseId}")
    public ResponseEntity<StudentCourse> enrollStudentCourse(@PathVariable Integer studentId, @PathVariable Integer courseId){
        StudentCourse studentCourse  =studentService.enrollStudentCourse(studentId,courseId) ;
        return ResponseEntity.ok(studentCourse);
    }
    @GetMapping("/course/{courseId}")
    public List<com.example.demo.repository.Student> getStudentsByCourseId(@PathVariable Integer courseId) {
        return studentService.getStudentsByCourseId(courseId);
    }
    @PutMapping("/studentCourse/{studentCourseId}")
    public ResponseEntity<StudentCourse> editEnrollStudentCourse(@PathVariable("studentCourseId") Integer studentCourseId, @RequestBody StudentCourse updateCourse ){
        StudentCourse studentCourse  =studentService.editEnrollStudentCourse(studentCourseId,updateCourse) ;
        return ResponseEntity.ok(studentCourse);
    }
}
