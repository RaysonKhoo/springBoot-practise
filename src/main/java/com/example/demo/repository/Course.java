package com.example.demo.repository;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "course")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Course {
    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    private int id;
    private String courseName;
    private String courseCode;
    private String courseDescription;
    private String courseLocation;
    private String day;
    private String startTime;
    private String endTime;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("course-studentCourse")
    private Set<StudentCourse> studentCourses = new HashSet<>();

    @Override
    public String toString() {
        return "Course [id=" + id + ", courseName=" + courseName + ", courseCode=" +courseCode + ", courseDescription=" + courseDescription + ", courseLocation=" + courseLocation + ", day=" + day  + ", startTime=" + startTime  + ", endTime=" + endTime  +"]";
    }
}
