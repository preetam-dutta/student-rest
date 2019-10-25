package com.preet.dstny.student;

import com.preet.dstny.student.data.Student;
import com.preet.dstny.student.data.StudentReadOps;
import com.preet.dstny.student.data.StudentWriteOps;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppTestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StudentTest {

  @Autowired
  private StudentWriteOps StudentWriteOps;

  @Autowired
  StudentReadOps studentReadOps;

  @BeforeClass
  public static void setup() throws CassandraContainerException {
    CassandraTestContainer.startContainer();
    CassandraTestContainer.setupStudentKeyspaceAndTables();
  }

  @AfterClass
  public static void cleanup() throws CassandraContainerException {
    CassandraTestContainer.dropStudentKeyspace();
  }

  @Test
  public void testStudentWrite() {
    UUID student1uuid = UUID.randomUUID();
    Student student1 = new Student(student1uuid, "CS", "preet@test.com", 3344556677d, "Preetam Dutta");
    StudentWriteOps.createStudent(student1);

    Assert.assertEquals(Long.valueOf(1), studentReadOps.count().block());
  }

}
