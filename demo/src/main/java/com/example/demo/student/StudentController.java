package com.example.demo.student;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        try {
            return studentService.getStudents();
        } catch (Exception e) {
            ResponseEntity.ok("Unsuccessful");
            return null;
        }

    }
    /*
     * @GetMapping("{id}")
     * public Student getStudentById (@PathVariable("id") Long id) {
     * return studentService.getStudentById(id);
     * }
     */

    @GetMapping("address/{address}")
    public Student getStudentByAddress(@PathVariable("address") String address) {
        return studentService.getStudentByAddress(address);
    }

    @GetMapping("phone/{phone}")
    public Student getStudentByPhone(@PathVariable("phone") String phone) {
        return studentService.getStudentByPhone(phone);
    }

    @GetMapping(path = "{studentId}")
    public ResponseEntity<Student> getOneStudent(@PathVariable("studentId") Long studentId) {
        try {
            Student student = studentService.getOneStudent(studentId);
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping
    public ResponseEntity<String> createNewStudent(@RequestBody Student student) {
        try {
            studentService.createNewStudent(student);
            return ResponseEntity.ok("Student inserted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to insert student");
        }
    }

    @DeleteMapping(path = "{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable("studentId") Long studentId) {
        try {
            studentService.deleteStudent(studentId);
            return ResponseEntity.ok("Deleted Successfully");
        } catch (Exception e) {
            return ResponseEntity.ok("Deleting unuccessful");
        }

    }

    @PutMapping(path = "{studentId}")
    public ResponseEntity<String> updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) LocalDate dob,
            @RequestParam(required = false) String phone) {

        try {
            studentService.updateStudent(studentId, name, email, dob, phone);
            return ResponseEntity.ok("Updated Successfuly");
        } catch (Exception e) {
            return ResponseEntity.ok("Updating unsuccessful");
        }

    }

    @PutMapping(path = "courses/{studentId}")
    public void addCourse(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String cid) {

        studentService.addCourse(studentId, cid);
    }

}
