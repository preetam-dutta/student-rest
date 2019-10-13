package com.preet.dstny.student.model;

import com.preet.dstny.student.view.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IStudentService {
  Flux<Student> listAll();
  Mono<Student> findById(String id);

}
