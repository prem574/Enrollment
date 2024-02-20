package com.example.demo.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.CourseRepository;
import com.example.demo.Repository.EnrollmentRepository;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.entity.Course;
import com.example.demo.entity.Enrollment;
import com.example.demo.entity.EnrollmentStatus;
import com.example.demo.entity.Student;

@Service
public class EnrollmentService {
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }
    
    public Enrollment getEnrollmentById(Long id) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(id);
        return optionalEnrollment.orElse(null);
    }
    
    public Enrollment createEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }
    
    public Enrollment updateEnrollment(Long id, Enrollment enrollment) {
        if (enrollmentRepository.existsById(id)) {
            enrollment.setEnrollmentId(id);
            return enrollmentRepository.save(enrollment);
        }
        return null;
    }
    
    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }
    
    public List<Enrollment> getEnrollmentsByStudentId(Long studentId) {
        return enrollmentRepository.findByStudent_StudentId(studentId);
    }

    public List<Course> getUnselectedCoursesByStudentId(Long studentId) {
        return enrollmentRepository.findUnselectedCoursesByStudentId(studentId);
    }

    public String enrollStudent(Long studentId, Long courseId) {
        int capacity = enrollmentRepository.countEnrollmentsByCourseId(courseId);
        if (capacity < getMaxCapacity(courseId)) {
            Student student = studentRepository.findById(studentId).orElse(null);
            if (student != null) {
                Course course = courseRepository.findById(courseId).orElse(null);
                if (course != null) {
                    Enrollment enrollment = new Enrollment();
                    enrollment.setStudent(student);
                    enrollment.setCourse(course);
                    enrollment.setEnrollmentDate(new Date(System.currentTimeMillis()));
                    if (capacity < getCapacityThreshold(course)) {
                        enrollment.setStatus(EnrollmentStatus.CONFIRMED);
                    } else {
                        enrollment.setStatus(EnrollmentStatus.CANCELLED);
                    }
                    enrollmentRepository.save(enrollment);
                    return "Enrollment successful!";
                } else {
                    return "Enrollment failed. Course not found!";
                }
            } else {
                return "Enrollment failed. Student not found!";
            }
        } else {
            return "Enrollment failed. Course capacity reached!";
        }
    }

    private int getMaxCapacity(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        return courseOptional.map(Course::getCapacity).orElse(0);
    }

    private int getCapacityThreshold(Course course) {
        return (int) (course.getCapacity() * 0.8); // 80% threshold
    }
    

	public List<Student> findStudentsEnrolledByCourseId(Long courseId) {
		return enrollmentRepository.findStudentsEnrolledByCourseId(courseId);
	}
	
	public List<Enrollment> getStudentsEnrolledByTeacherId(Long teacherId) {
        return enrollmentRepository.findStudentsEnrolledByTeacherId(teacherId);
    }
}
