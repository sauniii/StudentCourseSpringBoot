package com.example.demo.student;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
        return studentService.getStudents();

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
    public Student getOneStudent(@PathVariable("studentId") Long studentId) {
        return studentService.getOneStudent(studentId);
    }

    @PostMapping
    public void createNewStudent(@RequestBody Student student) {
        studentService.createNewStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(
            @PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) LocalDate dob,
            @RequestParam(required = false) String phone) {

        studentService.updateStudent(studentId, name, email, dob, phone);
    }

    @PutMapping(path = "courses/{studentId}")
    public void addCourse(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String cid) {

        studentService.addCourse(studentId, cid);
    }

}
