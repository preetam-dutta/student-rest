package com.preet.dstny.student.controller;

import com.preet.dstny.student.db.StudentByCourseAndEmail;
import com.preet.dstny.student.db.StudentByCourseAndEmailRepository;
import com.preet.dstny.student.db.StudentByCourseAndPhoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.cql.ReactiveCqlTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/student")
public class StudentController {

  private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

  private StudentByCourseAndEmailRepository studentByCourseAndEmailRepository;
  private StudentByCourseAndPhoneRepository studentByCourseAndPhoneRepository;
  private ReactiveCqlTemplate reactiveCqlTemplate;

  @Autowired
  public StudentController(StudentByCourseAndEmailRepository studentByCourseAndEmailRepository,
                           StudentByCourseAndPhoneRepository studentByCourseAndPhoneRepository,
                           ReactiveCqlTemplate reactiveCqlTemplate) {

    this.studentByCourseAndEmailRepository = studentByCourseAndEmailRepository;
    this.reactiveCqlTemplate = reactiveCqlTemplate;
    this.studentByCourseAndPhoneRepository = studentByCourseAndPhoneRepository;
  }

  @GetMapping("/count")
  @ResponseBody
  public Mono<Long> studentCount() {
    return studentByCourseAndEmailRepository.count();
  }

  @GetMapping("/")
  @ResponseBody
  public Flux<StudentByCourseAndEmail> getAllStudents() {
    return studentByCourseAndEmailRepository.findAll();
  }

  @GetMapping(value = "/course/{course}")
  @ResponseBody
  public Flux<StudentByCourseAndEmail> getStudentByCourse(@PathVariable("course") String course) {
    return studentByCourseAndEmailRepository.findByCourse(course);
  }

  @GetMapping(value = "/course/{course}/email/{email}")
  @ResponseBody
  public Flux<StudentByCourseAndEmail> getStudentByCourseAndEmail(@PathVariable("course") String course, @PathVariable("email") String email) {
    return studentByCourseAndEmailRepository.findByCourseAndEmail(course, email);
  }

  @GetMapping(value = "/course/{course}/phone/{phone}")
  @ResponseBody
  public Flux<StudentByCourseAndEmail> getStudentByCourseAndPhone(@PathVariable("course") String course, @PathVariable("phone") Double phone) {
    return studentByCourseAndPhoneRepository.findByCourseAndPhone(course, phone);
  }

}
