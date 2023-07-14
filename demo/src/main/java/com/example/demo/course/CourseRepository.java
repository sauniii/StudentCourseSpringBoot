package com.example.demo.course;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
     
    @Query("SELECT c FROM Course c WHERE c.cname = ?1")
    Optional<Course> findCourseByCname(String cname);


}
