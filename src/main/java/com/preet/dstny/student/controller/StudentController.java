package com.preet.dstny.student.controller;

import com.preet.dstny.student.model.IStudentService;
import com.preet.dstny.student.view.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class StudentController {

  private final IStudentService studentService;

  @Autowired
  public StudentController(IStudentService studentService) {
    this.studentService = studentService;
  }

  @RequestMapping("/student")
  @ResponseBody
  public Flux<Student> helloWorld() {
    return studentService.listAll();
  }
}
