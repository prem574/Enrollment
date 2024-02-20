package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.EnrollmentService;
import com.example.demo.entity.Course;
import com.example.demo.entity.Enrollment;
import com.example.demo.entity.Student;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        return new ResponseEntity<>(enrollments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable("id") Long id) {
        Enrollment enrollment = enrollmentService.getEnrollmentById(id);
        if (enrollment != null) {
            return new ResponseEntity<>(enrollment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping
//    public ResponseEntity<Enrollment> createEnrollment(@RequestBody Enrollment enrollment) {
//        Enrollment createdEnrollment = enrollmentService.createEnrollment(enrollment);
//        return new ResponseEntity<>(createdEnrollment, HttpStatus.CREATED);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Enrollment> updateEnrollment(@PathVariable("id") Long id, @RequestBody Enrollment enrollment) {
        Enrollment updatedEnrollment = enrollmentService.updateEnrollment(id, enrollment);
        if (updatedEnrollment != null) {
            return new ResponseEntity<>(updatedEnrollment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable("id") Long id) {
        enrollmentService.deleteEnrollment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/student/{studentId}")
    public List<Enrollment> getEnrollmentsByStudentId(@PathVariable Long studentId) {
        return enrollmentService.getEnrollmentsByStudentId(studentId);
    }

    @GetMapping("/unselected-courses/{studentId}")
    public List<Course> getUnselectedCoursesByStudentId(@PathVariable Long studentId) {
        return enrollmentService.getUnselectedCoursesByStudentId(studentId);
    }

    @PostMapping("/enroll/{studentId}/{courseId}")
    public String enrollStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        return enrollmentService.enrollStudent(studentId, courseId);
    }
    
    @GetMapping("/course/{courseId}")
    public List<Student> getStudentsEnrolledByCourseId(@PathVariable Long courseId) {
        return enrollmentService.findStudentsEnrolledByCourseId(courseId);
    }
    
    @GetMapping("/teacher/{teacherId}")
    public List<Enrollment> getStudentsEnrolledByTeacherId(@PathVariable Long teacherId) {
        return enrollmentService.getStudentsEnrolledByTeacherId(teacherId);
    }
}

