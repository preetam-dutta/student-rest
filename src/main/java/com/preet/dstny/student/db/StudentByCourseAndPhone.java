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
@Table(value = "student_by_course_and_phone")
public class StudentByCourseAndPhone {

  @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 1)
  private String course;

  @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 2)
  private Double phone;

  @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 3)
  private String email;

  @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 4)
  private String name;

  private UUID id;
}
