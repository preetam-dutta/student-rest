package com.preet.dstny.student.controller;

import com.preet.dstny.student.api.CassandraSession;
import com.preet.dstny.student.api.IStudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
public class StudentController {

  private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
  private final IStudentService studentService;
  private CassandraSession cassandra;

  @Autowired
  public StudentController(IStudentService studentService, CassandraSession cassandra) {
    this.studentService = studentService;
    this.cassandra = cassandra;
  }

  @RequestMapping("/student")
  @ResponseBody
  public Mono<Long> helloWorld() {
    Mono<Long> data = cassandra.cql().queryForObject("select count(column1) from table1", Long.class);
    LOGGER.info("Yaaa hooo");

    return data;
  }
}
