package com.preet.dstny.student.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "student_by_course_and_email")
public class StudentByCourseAndEmail {

  @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 1)
  private String course;

  @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 2)
  private String email;

  @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 3)
  private Double phone;

  @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 4)
  private String name;

  private UUID id;

  public StudentByCourseAndEmail(Student student) {
    this.course = student.getCourse();
    this.email = student.getEmail();
    this.phone = student.getPhone();
    this.name = student.getName();
    this.id = student.getId();
  }

}
