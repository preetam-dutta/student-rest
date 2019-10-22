package com.preet.dstny.student.controller;

import com.preet.dstny.student.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/student")
public class StudentController {

  private StudentByCourseAndEmailReadOps studentByCourseAndEmailReadOps;
  private StudentByCourseAndPhoneReadOps studentByCourseAndPhoneReadOps;
  private StudentWriteOperations studentWriteOps;
  private StudentReadOps studentReadOps;

  @Autowired
  public StudentController(StudentByCourseAndEmailReadOps studentByCourseAndEmailReadOps,
                           StudentByCourseAndPhoneReadOps studentByCourseAndPhoneReadOps,
                           StudentReadOps studentReadOps,
                           @Qualifier(value = "student-write-ops") StudentWriteOperations studentWriteOps) {
    this.studentByCourseAndEmailReadOps = studentByCourseAndEmailReadOps;
    this.studentReadOps = studentReadOps;
    this.studentByCourseAndPhoneReadOps = studentByCourseAndPhoneReadOps;
    this.studentWriteOps = studentWriteOps;
  }

  @GetMapping("/count")
  @ResponseBody
  public Mono<Long> studentCount() {
    return studentByCourseAndEmailReadOps.count();
  }

  @GetMapping("")
  @ResponseBody
  public Flux<Student> getAllStudents() {
    return studentReadOps.findAll();
  }

  @GetMapping(value = "/id/{id}")
  @ResponseBody
  public Mono<Student> getStudentById(@PathVariable("id") UUID id) {
    return studentReadOps.findStudentById(id);
  }

  @GetMapping(value = "/course/{course}")
  @ResponseBody
  public Flux<StudentByCourseAndEmail> getStudentByCourse(@PathVariable("course") String course) {
    return studentByCourseAndEmailReadOps.findByCourse(course);
  }

  @GetMapping(value = "/course/{course}/email/{email}")
  @ResponseBody
  public Mono<StudentByCourseAndEmail> getStudentByCourseAndEmail(@PathVariable("course") String course, @PathVariable("email") String email) {
    return studentByCourseAndEmailReadOps.findByCourseAndEmail(course, email);
  }

  @GetMapping(value = "/course/{course}/phone/{phone}")
  @ResponseBody
  public Mono<StudentByCourseAndEmail> getStudentByCourseAndPhone(@PathVariable("course") String course, @PathVariable("phone") Double phone) {
    return studentByCourseAndPhoneReadOps.findByCourseAndPhone(course, phone);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createStudent(@RequestBody Student student) {
    this.studentWriteOps.createStudent(student);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.OK)
  public void updateStudent(@RequestBody Student student) {
    this.studentWriteOps.updateStudent(student);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteStudent(@PathVariable UUID id) {
    this.studentWriteOps.deleteStudent(id);
  }
}
