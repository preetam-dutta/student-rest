package com.preet.dstny.student.db;

public interface WriteOperation {
  void createStudent(Student student);

  void updateStudent(Student student);
}
