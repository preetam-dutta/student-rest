package com.preet.dstny.student.db;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public interface StudentByCourseAndEmailRepository extends ReactiveCrudRepository<StudentByCourseAndEmail, String> {

  @Query("select * from student_by_course_and_email where course = ?0 and email = ?1")
  Flux<StudentByCourseAndEmail> findByCourseAndEmail(String course, String email);

  @Query("select * from student_by_course_and_email where course = ?0")
  Flux<StudentByCourseAndEmail> findByCourse(String course);

}
