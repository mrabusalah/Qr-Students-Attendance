package com.bau.graduateprojects.qrstudentsattendance.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ATTENDANCE")
public class AttendanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String status;
    @OneToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;
}
