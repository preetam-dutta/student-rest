package com.preet.dstny.student.api;

import com.preet.dstny.student.model.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IStudentService {
  Flux<Student> listAll();
  Mono<Student> findById(String id);

}
