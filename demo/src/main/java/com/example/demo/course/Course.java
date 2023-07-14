package com.example.demo.course;

import java.util.Set;

import jakarta.persistence.*;
import com.example.demo.student.Student;
 

@Entity
@Table
public class Course {
    
    @Id
    @SequenceGenerator (name = "course_sequence",
                        sequenceName= "course_sequence",
                        allocationSize =1)

    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "course_sequence"
    )



    private Long cid;
    private String cname;
    private int credit;
    
    public Course() {
    }

    public Course(Long cid, String cname, int credit) {
        this.cid = cid;
        this.cname = cname;
        this.credit = credit;
    }

    public Course(String cname, int credit) {
        this.cname = cname;
        this.credit = credit;
    }


    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "Course [cid=" + cid + ", cname=" + cname + ", credit=" + credit + "]";
    }


    @ManyToMany (mappedBy = "myCourses")
    Set <Student> likes;
    
    /* 
    public Set<Student> getLikes() {
        return likes;
    }

    public void setLikes(Set<Student> likes) {
        this.likes = likes;
    } 
     */
    

}
