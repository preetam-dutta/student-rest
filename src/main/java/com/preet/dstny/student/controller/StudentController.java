package com.preet.dstny.student.controller;

import com.preet.dstny.student.db.*;
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

  private StudentByCourseAndEmailRepository studentByCourseAndEmailRepository;
  private StudentByCourseAndPhoneRepository studentByCourseAndPhoneRepository;
  private WriteOperation writeOperation;
  private StudentRepository studentRepository;

  @Autowired
  public StudentController(StudentByCourseAndEmailRepository studentByCourseAndEmailRepository,
                           StudentByCourseAndPhoneRepository studentByCourseAndPhoneRepository,
                           StudentRepository studentRepository,
                           @Qualifier(value = "student") WriteOperation writeOperation) {
    this.studentByCourseAndEmailRepository = studentByCourseAndEmailRepository;
    this.studentRepository = studentRepository;
    this.studentByCourseAndPhoneRepository = studentByCourseAndPhoneRepository;
    this.writeOperation = writeOperation;
  }

  @GetMapping("/count")
  @ResponseBody
  public Mono<Long> studentCount() {
    return studentByCourseAndEmailRepository.count();
  }

  @GetMapping("")
  @ResponseBody
  public Flux<Student> getAllStudents() {
    return studentRepository.findAll();
  }

  @GetMapping(value = "/id/{id}")
  @ResponseBody
  public Mono<Student> getStudentById(@PathVariable("id") UUID id) {
    return studentRepository.findStudentById(id);
  }

  @GetMapping(value = "/course/{course}")
  @ResponseBody
  public Flux<StudentByCourseAndEmail> getStudentByCourse(@PathVariable("course") String course) {
    return studentByCourseAndEmailRepository.findByCourse(course);
  }

  @GetMapping(value = "/course/{course}/email/{email}")
  @ResponseBody
  public Mono<StudentByCourseAndEmail> getStudentByCourseAndEmail(@PathVariable("course") String course, @PathVariable("email") String email) {
    return studentByCourseAndEmailRepository.findByCourseAndEmail(course, email);
  }

  @GetMapping(value = "/course/{course}/phone/{phone}")
  @ResponseBody
  public Mono<StudentByCourseAndEmail> getStudentByCourseAndPhone(@PathVariable("course") String course, @PathVariable("phone") Double phone) {
    return studentByCourseAndPhoneRepository.findByCourseAndPhone(course, phone);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createStudent(@RequestBody Student student) {
    this.writeOperation.createStudent(student);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.OK)
  public void updateStudent(@RequestBody Student student) {
    this.writeOperation.updateStudent(student);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteStudent(@PathVariable UUID id) {
    this.writeOperation.deleteStudent(id);
  }
}
