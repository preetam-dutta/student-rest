package com.preet.dstny.student.controller;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
//import com.datastax.oss.driver.api.core.CqlSession;
//import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.preet.dstny.student.model.IStudentService;
//import com.preet.dstny.student.view.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate;
import org.springframework.data.cassandra.core.cql.ReactiveCqlTemplate;
import org.springframework.data.cassandra.core.cql.session.DefaultBridgedReactiveSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;


@RestController
public class StudentController {

  private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
  private final IStudentService studentService;

  @Autowired
  public StudentController(IStudentService studentService) {
    this.studentService = studentService;
  }

  @RequestMapping("/student")
  @ResponseBody
//  public Flux<Student> helloWorld() {
  public Mono<Long> helloWorld() throws UnknownHostException {
    Cluster cluster = Cluster.builder()
        .addContactPointsWithPorts(new InetSocketAddress("cassandra", 9042))
        .withClusterName("preet-cluster")
        .withoutJMXReporting()
        .build();
    Session session = cluster.connect("test1");
//    ReactiveCassandraOperations template = new ReactiveCassandraTemplate(new DefaultBridgedReactiveSession(session));
    ReactiveCqlTemplate reactiveCqlTemplate = new ReactiveCqlTemplate(new DefaultBridgedReactiveSession(session));
    Mono<Long> data = reactiveCqlTemplate.queryForObject("select count(column1) from table1", Long.class);
    LOGGER.info("Yaaa hooo");

    return data;

//    LOGGER.info("creating/updating CQL session");
//    CqlSession session = CqlSession.builder()
//        .addContactPoint(new InetSocketAddress("cassandra", 9042))
//        .withLocalDatacenter("dc1")
//        .build();
//    ResultSet resultSet = session.execute("select count(column1) from test1.table1");
//    resultSet.forEach(row -> {
//      LOGGER.info("result: {}", row.getLong(0));
//    });

//    return studentService.listAll();
  }
}
