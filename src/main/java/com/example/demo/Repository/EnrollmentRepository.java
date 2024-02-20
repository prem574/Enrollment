package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Course;
import com.example.demo.entity.Enrollment;
import com.example.demo.entity.Student;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	// Method to get enrollment details by studentId
    List<Enrollment> findByStudent_StudentId(Long studentId);

    // Method to get unselected courses for a particular student
    @Query("SELECT c FROM Course c WHERE c.courseId NOT IN (SELECT e.course.courseId FROM Enrollment e WHERE e.student.studentId = ?1)")
    List<Course> findUnselectedCoursesByStudentId(Long studentId);

    // Method to check if course capacity is reached
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.course.courseId = ?1")
    int countEnrollmentsByCourseId(Long courseId);
    
    @Query("SELECT e.student FROM Enrollment e WHERE e.course.courseId = :courseId")
    List<Student> findStudentsEnrolledByCourseId(@Param("courseId") Long courseId);
    
    @Query("SELECT e FROM Enrollment e WHERE e.course.teacher.teacherId = :teacherId")
    List<Enrollment> findStudentsEnrolledByTeacherId(@Param("teacherId") Long teacherId);
}

