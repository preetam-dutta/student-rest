package com.preet.dstny.student.data;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public interface StudentByCourseAndPhoneReadOps extends ReactiveCrudRepository<StudentByCourseAndEmail, String> {

  @Query("select * from student_by_course_and_phone where course = ?0 and phone = ?1")
  Mono<StudentByCourseAndEmail> findByCourseAndPhone(String course, Double phone);
}
