package com.preet.dstny.student.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "student")
public class Student {

  @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
  private UUID id;

  private String course;

  private String email;

  private Double phone;

  private String name;
}
