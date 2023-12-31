package com.example.demo.student;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.course.Course;
import com.example.demo.course.CourseRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // get the list of students
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    // get only one student
    public Student getOneStudent(Long studentId) {

        // check if the student exits
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException(
                    "Student with id " + studentId + " does not exists");
        }

        return studentRepository.findById(studentId).get();

    }

    public Student getStudentByAddress(String Address) {
        return studentRepository.findStudentByAddress(Address).get();
    }

    public void createNewStudent(Student student) {

        Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());

        if (studentOptional.isPresent()) {
            throw new IllegalStateException("Email is taken");
        }

        studentRepository.save(student);

        System.out.println(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException(
                    "Student with id " + studentId + " does not exists");
        }

        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId,
            String name,
            String email,
            LocalDate dob,
            String phone) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "student with id " + studentId + " does not exists"));

        if (name != null &&
                name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null &&
                email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptional = studentRepository
                    .findStudentByEmail(email);

            if (studentOptional.isPresent()) {
                throw new IllegalStateException("Email is taken");
            }
            student.setEmail(email);
        }

        if (phone != null &&
                phone.length() > 0 && !Objects.equals(student.getPhone(), phone)) {
            student.setPhone(phone);
        }

        if (dob != null &&
                dob.lengthOfYear() > 0 && !Objects.equals(student.getDob(), dob)) {
            student.setDob(dob);
        }

        studentRepository.save(student);
    }

    @Transactional
    public void addCourse(
            Long studentId,
            String courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "student with id " + studentId + " does not exists"));

        // Get the course with the specified courseId
        Course course = courseRepository.findCourseByCname(courseId)
                .orElseThrow(() -> new IllegalStateException("Course with cid " + courseId + " does not exist"));

        // Add the course to the student's set of courses
        student.getMyCourses().add(course);

        studentRepository.save(student);
    }

    public Student getStudentByPhone(String phone) {
        return studentRepository.findStudentByPhone(phone).get();
    }

}
