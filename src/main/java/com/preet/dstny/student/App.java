package com.preet.dstny.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration(exclude={CassandraDataAutoConfiguration.class})
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
