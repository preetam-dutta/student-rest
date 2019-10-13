package com.preet.dstny.student.model;

import com.preet.dstny.student.view.Student;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentService implements IStudentService {
  @Override
  public Flux<Student> listAll() {
    Student student1 = new Student("preetam", "std1");
    Student student2 = new Student("joshika", "std2");
    Flux<Student> fluxOfStudents = Flux.just(student1, student2);
    return fluxOfStudents;
  }

  @Override
  public Mono<Student> findById(String id) {
    Student student1 = new Student("preetam", "std1");
    Mono<Student> monoOfStudent = Mono.just(student1);
    return monoOfStudent;
  }
}
