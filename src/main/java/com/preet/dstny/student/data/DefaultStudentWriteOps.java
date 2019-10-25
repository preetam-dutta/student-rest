package com.preet.dstny.student.data;

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

@Component
public class DefaultStudentWriteOps implements StudentWriteOps {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultStudentWriteOps.class);

  private final CassandraTemplate cassandraTemplate;

  @Autowired
  public DefaultStudentWriteOps(CassandraTemplate cassandraTemplate) {
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
    if (deleteStudent(student, true)) {
      StudentByCourseAndPhone newStudentByCourseAndPhone = new StudentByCourseAndPhone(student);
      StudentByCourseAndEmail newStudentByCourseAndEmail = new StudentByCourseAndEmail(student);
      CassandraBatchOperations batchOperations = cassandraTemplate.batchOps();
      batchOperations.update(student);
      batchOperations.update(newStudentByCourseAndPhone);
      batchOperations.update(newStudentByCourseAndEmail);
      batchOperations.execute();
      LOGGER.info("student[{}] updated", student.getId());
    } else {
      LOGGER.warn("update not done, student do not exist");
    }
  }

  @Override
  public void deleteStudent(UUID id) {
    Student student = new Student(id, null, null, null, null);
    if (deleteStudent(student, false)) {
      LOGGER.info("student[{}] deleted", student.getId());
    } else {
      LOGGER.warn("delete NOT done, student do not exist");
    }
  }

  private boolean deleteStudent(Student student, boolean isBatchUpdateOperation) {
    if (student.getId() == null) {
      if (isBatchUpdateOperation) LOGGER.warn("update NOT done, student.id missing in request");
      else LOGGER.warn("delete NOT done, student.id missing in request");
      return false;
    }

    Select query = QueryBuilder.select().from("student").where(QueryBuilder.eq("id", student.getId())).allowFiltering();
    Student existingStudent = cassandraTemplate.selectOne(query, Student.class);

    if (existingStudent != null) {
      CassandraBatchOperations deleteOperation = cassandraTemplate.batchOps();
      if (!isBatchUpdateOperation) deleteOperation.delete(existingStudent);
      deleteOperation.delete(new StudentByCourseAndPhone(existingStudent));
      deleteOperation.delete(new StudentByCourseAndEmail(existingStudent));
      deleteOperation.execute();
      return true;
    }
    return false;
  }
}
