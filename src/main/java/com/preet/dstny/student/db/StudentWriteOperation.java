package com.preet.dstny.student.db;

import com.datastax.driver.core.utils.UUIDs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component(value = "student")
public class StudentWriteOperation implements WriteOperation {

  private final CassandraTemplate cassandraTemplate;

  @Autowired
  public StudentWriteOperation(CassandraTemplate cassandraTemplate) {
    this.cassandraTemplate = cassandraTemplate;
  }

  @Override
  public void createStudent(Student student) {
    UUID uuid = UUIDs.random();
    student.setId(uuid);
    StudentByCourseAndPhone studentByCourseAndPhone = new StudentByCourseAndPhone(student.getCourse(), student.getPhone(), student.getEmail(), student.getName(), uuid);
    StudentByCourseAndEmail studentByCourseAndEmail = new StudentByCourseAndEmail(student.getCourse(), student.getEmail(), student.getPhone(), student.getName(), uuid);

    CassandraBatchOperations batchOperations = cassandraTemplate.batchOps();
    batchOperations.insert(student);
    batchOperations.insert(studentByCourseAndPhone);
    batchOperations.insert(studentByCourseAndEmail);
    batchOperations.execute();
  }
}
