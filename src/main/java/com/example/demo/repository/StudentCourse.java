package com.example.demo.repository;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="student_course")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentCourse {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private  Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id",referencedColumnName = "id")
    @JsonBackReference("student-studentCourse")
    private  Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id",referencedColumnName = "id")
    @JsonBackReference("course-studentCourse")
    private Course course;

    @Override
    public String toString() {
        return "StudentCourse [id=" + id + ", student=" + student + ", course=" + course + "]";
    }
}

