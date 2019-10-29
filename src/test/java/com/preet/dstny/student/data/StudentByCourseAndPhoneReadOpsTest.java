package com.preet.dstny.student.data;

import com.preet.dstny.student.AppTestConfiguration;
import com.preet.dstny.student.CassandraContainerException;
import com.preet.dstny.student.CassandraInitAndCleanupRule;
import com.preet.dstny.student.TestUtil;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppTestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StudentByCourseAndPhoneReadOpsTest {

  @Autowired
  private StudentWriteOps studentWriteOps;

  @Autowired
  private StudentByCourseAndPhoneReadOps studentByCourseAndPhoneReadOps;

  @ClassRule
  public static CassandraInitAndCleanupRule cassandraInitAndCleanupRule = new CassandraInitAndCleanupRule();

  @Before
  public void setUp() {
    studentWriteOps.createStudent(TestUtil.STUDENT_1);
    studentWriteOps.createStudent(TestUtil.STUDENT_3);
    studentWriteOps.createStudent(TestUtil.STUDENT_5);
  }

  @After
  public void tearDown() throws CassandraContainerException {
    TestUtil.truncateTables();
  }


  @Test
  public void findByCourseAndPhone() {
    Assert.assertEquals(Long.valueOf(3), studentByCourseAndPhoneReadOps.count().block());

    Assert.assertEquals(TestUtil.STUDENT_1.getEmail(), Objects.requireNonNull(studentByCourseAndPhoneReadOps.findByCourseAndPhone(TestUtil.STUDENT_1.getCourse(), TestUtil.STUDENT_1.getPhone()).block()).getEmail());
    Assert.assertEquals(TestUtil.STUDENT_1.getPhone(), Objects.requireNonNull(studentByCourseAndPhoneReadOps.findByCourseAndPhone(TestUtil.STUDENT_1.getCourse(), TestUtil.STUDENT_1.getPhone()).block()).getPhone());
    Assert.assertEquals(TestUtil.STUDENT_1.getCourse(),Objects.requireNonNull(studentByCourseAndPhoneReadOps.findByCourseAndPhone(TestUtil.STUDENT_1.getCourse(), TestUtil.STUDENT_1.getPhone()).block()).getCourse());

    Assert.assertEquals(TestUtil.STUDENT_3.getEmail(), Objects.requireNonNull(studentByCourseAndPhoneReadOps.findByCourseAndPhone(TestUtil.STUDENT_3.getCourse(), TestUtil.STUDENT_3.getPhone()).block()).getEmail());
    Assert.assertEquals(TestUtil.STUDENT_3.getPhone(), Objects.requireNonNull(studentByCourseAndPhoneReadOps.findByCourseAndPhone(TestUtil.STUDENT_3.getCourse(), TestUtil.STUDENT_3.getPhone()).block()).getPhone());
    Assert.assertEquals(TestUtil.STUDENT_3.getCourse(),Objects.requireNonNull(studentByCourseAndPhoneReadOps.findByCourseAndPhone(TestUtil.STUDENT_3.getCourse(), TestUtil.STUDENT_3.getPhone()).block()).getCourse());

    Assert.assertEquals(TestUtil.STUDENT_5.getEmail(), Objects.requireNonNull(studentByCourseAndPhoneReadOps.findByCourseAndPhone(TestUtil.STUDENT_5.getCourse(), TestUtil.STUDENT_5.getPhone()).block()).getEmail());
    Assert.assertEquals(TestUtil.STUDENT_5.getPhone(), Objects.requireNonNull(studentByCourseAndPhoneReadOps.findByCourseAndPhone(TestUtil.STUDENT_5.getCourse(), TestUtil.STUDENT_5.getPhone()).block()).getPhone());
    Assert.assertEquals(TestUtil.STUDENT_5.getCourse(),Objects.requireNonNull(studentByCourseAndPhoneReadOps.findByCourseAndPhone(TestUtil.STUDENT_5.getCourse(), TestUtil.STUDENT_5.getPhone()).block()).getCourse());

  }
}