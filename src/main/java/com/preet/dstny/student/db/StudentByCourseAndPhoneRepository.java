package com.preet.dstny.student.db;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public interface StudentByCourseAndPhoneRepository extends ReactiveCrudRepository<StudentByCourseAndEmail, String> {

  @Query("select * from student_by_course_and_phone where course = ?0 and phone = ?1")
  Flux<StudentByCourseAndEmail> findByCourseAndPhone(String course, Double phone);
}
