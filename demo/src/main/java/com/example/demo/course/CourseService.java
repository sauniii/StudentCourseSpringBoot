package com.example.demo.course;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class CourseService {
    
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getCourses(){
        return courseRepository.findAll();
    }

    public void addNewCourse(Course course) {

        Optional <Course> courseOptional = courseRepository
        .findCourseByCname(course.getCname());

        if (courseOptional.isPresent()) {
            throw new IllegalStateException("Already available");
        }

        courseRepository.save(course);

        System.out.println(course);
    }

    public void deleteCourse(Long cid) {
        boolean exists = courseRepository.existsById(cid);
        if(!exists){
            throw new IllegalStateException (
                "Course with Id " + cid + " deoes not exits"
            );
        }

        courseRepository.deleteById(cid);
    }

    @Transactional
    public void updateCourse(Long cid, String cname, int credit) {
        Course course = courseRepository.findById(cid)
        .orElseThrow(() -> new IllegalStateException (
            "Course with Id " + cid + " doesn't exists"
        ));

        if (!Objects.equals(course.getCredit(), credit)){
                course.setCredit(credit);
            }

        if (cname != null && 
                cname.length() > 0 && !Objects.equals(course.getCname(), cname)){
                    Optional<Course> courseOptional = courseRepository
                    .findCourseByCname(cname);

                    if(courseOptional.isPresent()){
                        throw new IllegalStateException("Course Name is Taken");
                    }
                course.setCname(cname);
        }

        courseRepository.save(course);
}

    
    
}
