package com.preet.dstny.student.data;

import java.util.UUID;

public interface StudentWriteOperations {
  void createStudent(Student student);

  void updateStudent(Student student);

  void deleteStudent(UUID id);
}
