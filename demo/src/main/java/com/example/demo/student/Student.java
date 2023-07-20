package com.example.demo.student;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;
import com.example.demo.course.Course;
import jakarta.persistence.*;

@Entity
@Table
public class Student {
    @Id
    @SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")

    private Long id;
    private String name;
    private String email;
    private String phone;
    private LocalDate dob;
    private String address;

    @Transient
    private Integer age;

    public Student() {

    }

    public Student(Long id, String name, String email, String phone, LocalDate dob, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.address = address;
    }

    public Student(String name, String email, String phone, LocalDate dob, String address) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.address = address;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @ManyToMany
    @JoinTable(name = "student_course", joinColumns = @JoinColumn(name = "cid"))
    Set<Course> myCourses;

    public Set<Course> getMyCourses() {
        return myCourses;
    }

    public void setMyCourses(Set<Course> myCourses) {
        this.myCourses = myCourses;
    }

    @Override
    public String toString() {
        return "Student [id=" + id + ", name=" + name + ", email=" + email + ", dob=" + dob + ", age=" + age + "]";
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
