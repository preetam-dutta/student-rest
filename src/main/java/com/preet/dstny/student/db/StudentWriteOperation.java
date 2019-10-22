package com.preet.dstny.student.db;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.utils.UUIDs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component(value = "student")
public class StudentWriteOperation implements WriteOperation {

  private static final Logger LOGGER = LoggerFactory.getLogger(StudentWriteOperation.class);

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
    LOGGER.info("student[{}] created", uuid);
  }

  @Override
  public void updateStudent(Student student) {

    if (student.getId() == null) {
      LOGGER.warn("update not done, student.id missing in request");
      return;
    }

    Select query = QueryBuilder.select()
        .from("student")
        .where(QueryBuilder.eq("id", student.getId())).allowFiltering();
    Student existingStudent = cassandraTemplate.selectOne(query, Student.class);

    if (existingStudent != null) {
      //delete existing supporting tables
      StudentByCourseAndPhone existingStudentByCourseAndPhone = new StudentByCourseAndPhone(existingStudent);
      StudentByCourseAndEmail existingStudentByCourseAndEmail = new StudentByCourseAndEmail(existingStudent);
      CassandraBatchOperations deleteOperation = cassandraTemplate.batchOps();
      deleteOperation.delete(existingStudentByCourseAndPhone);
      deleteOperation.delete(existingStudentByCourseAndEmail);
      deleteOperation.execute();

      //update student and create supporting tables
      StudentByCourseAndPhone newStudentByCourseAndPhone = new StudentByCourseAndPhone(student);
      StudentByCourseAndEmail newStudentByCourseAndEmail = new StudentByCourseAndEmail(student);
      CassandraBatchOperations batchOperations = cassandraTemplate.batchOps();
      batchOperations.update(student);
      batchOperations.update(newStudentByCourseAndPhone);
      batchOperations.update(newStudentByCourseAndEmail);
      batchOperations.execute();
      LOGGER.info("student[{}] updated", existingStudent.getId());
    } else {
      LOGGER.warn("update not done, student do not exist");
    }
  }
}
