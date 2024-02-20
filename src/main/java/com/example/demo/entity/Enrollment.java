package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;
    
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;
    
    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;
    
    private Date enrollmentDate;
    
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

	public Long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Date getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	public EnrollmentStatus getStatus() {
		return status;
	}

	public void setStatus(EnrollmentStatus status) {
		this.status = status;
	}

	public Enrollment() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Enrollment [enrollmentId=" + enrollmentId + ", student=" + student + ", course=" + course
				+ ", enrollmentDate=" + enrollmentDate + ", status=" + status + "]";
	}

   
}
