package com.preet.dstny.student.data;

import com.preet.dstny.student.AppTestConfiguration;
import com.preet.dstny.student.CassandraContainerException;
import com.preet.dstny.student.CassandraInitAndCleanupRule;
import com.preet.dstny.student.TestUtil;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppTestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StudentReadOpsTest {

  @Autowired
  private StudentWriteOps studentWriteOps;

  @Autowired
  private StudentReadOps studentReadOps;

  @ClassRule
  public static CassandraInitAndCleanupRule cassandraInitAndCleanupRule = new CassandraInitAndCleanupRule();

  @Before
  public void setUp() {
    studentWriteOps.createStudent(TestUtil.STUDENT_1);
    studentWriteOps.createStudent(TestUtil.STUDENT_2);
  }

  @After
  public void tearDown() throws CassandraContainerException {
    TestUtil.truncateTables();
  }

  @Test
  public void findStudentById() {
    Assert.assertEquals(Long.valueOf(2), studentReadOps.count().block());
    Assert.assertEquals(TestUtil.STUDENT_1, studentReadOps.findStudentById(TestUtil.STUDENT_1.getId()).block());
    Assert.assertEquals(TestUtil.STUDENT_2, studentReadOps.findStudentById(TestUtil.STUDENT_2.getId()).block());
  }
}