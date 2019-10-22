package com.preet.dstny.student.db;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public interface StudentRepository extends ReactiveCrudRepository<Student, String> {

  @Query("select * from student where id = ?0")
  Mono<Student> findStudentById(UUID id);

}
