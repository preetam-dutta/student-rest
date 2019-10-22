package com.preet.dstny.student.data;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public interface StudentByCourseAndEmailReadOps extends ReactiveCrudRepository<StudentByCourseAndEmail, String> {

  @Query("select * from student_by_course_and_email where course = ?0 and email = ?1")
  Mono<StudentByCourseAndEmail> findByCourseAndEmail(String course, String email);

  @Query("select * from student_by_course_and_email where course = ?0")
  Flux<StudentByCourseAndEmail> findByCourse(String course);

}
