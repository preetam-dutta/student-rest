package com.preet.dstny.student.db;

import java.util.UUID;

public interface WriteOperation {
  void createStudent(Student student);

  void updateStudent(Student student);

  void deleteStudent(UUID id);
}
