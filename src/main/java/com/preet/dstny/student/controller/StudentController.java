package com.preet.dstny.student.controller;

import com.preet.dstny.student.view.Student;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

  @RequestMapping("/student")
  public Student helloWorld() {
    return new Student("Preetam", "123");
  }
}
