package com.preet.dstny.student;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

  @RequestMapping("/student")
  public Student helloWorld() {
    return new Student("Preetam", "123");
  }
}
