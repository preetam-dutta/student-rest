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
public class StudentByCourseAndEmailReadOpsTest {

  @Autowired
  private StudentWriteOps studentWriteOps;

  @Autowired
  private StudentByCourseAndEmailReadOps studentByCourseAndEmailReadOps;

  @ClassRule
  public static CassandraInitAndCleanupRule cassandraInitAndCleanupRule = new CassandraInitAndCleanupRule();

  @Before
  public void setUp() {
    studentWriteOps.createStudent(TestUtil.STUDENT_1);
    studentWriteOps.createStudent(TestUtil.STUDENT_2);
    studentWriteOps.createStudent(TestUtil.STUDENT_3);
    studentWriteOps.createStudent(TestUtil.STUDENT_5);
  }

  @After
  public void tearDown() throws CassandraContainerException {
    TestUtil.truncateTables();
  }

  @Test
  public void findByCourseAndEmail() {
    Assert.assertEquals(TestUtil.STUDENT_1.getEmail(), Objects.requireNonNull(studentByCourseAndEmailReadOps.findByCourseAndEmail(TestUtil.STUDENT_1.getCourse(), TestUtil.STUDENT_1.getEmail()).block()).getEmail());
    Assert.assertEquals(TestUtil.STUDENT_1.getPhone(), Objects.requireNonNull(studentByCourseAndEmailReadOps.findByCourseAndEmail(TestUtil.STUDENT_1.getCourse(), TestUtil.STUDENT_1.getEmail()).block()).getPhone());
    Assert.assertEquals(TestUtil.STUDENT_1.getCourse(),Objects.requireNonNull(studentByCourseAndEmailReadOps.findByCourseAndEmail(TestUtil.STUDENT_1.getCourse(), TestUtil.STUDENT_1.getEmail()).block()).getCourse());

    Assert.assertEquals(TestUtil.STUDENT_2.getEmail(), Objects.requireNonNull(studentByCourseAndEmailReadOps.findByCourseAndEmail(TestUtil.STUDENT_2.getCourse(), TestUtil.STUDENT_2.getEmail()).block()).getEmail());
    Assert.assertEquals(TestUtil.STUDENT_2.getPhone(), Objects.requireNonNull(studentByCourseAndEmailReadOps.findByCourseAndEmail(TestUtil.STUDENT_2.getCourse(), TestUtil.STUDENT_2.getEmail()).block()).getPhone());
    Assert.assertEquals(TestUtil.STUDENT_2.getCourse(),Objects.requireNonNull(studentByCourseAndEmailReadOps.findByCourseAndEmail(TestUtil.STUDENT_2.getCourse(), TestUtil.STUDENT_2.getEmail()).block()).getCourse());

    Assert.assertEquals(TestUtil.STUDENT_3.getEmail(), Objects.requireNonNull(studentByCourseAndEmailReadOps.findByCourseAndEmail(TestUtil.STUDENT_3.getCourse(), TestUtil.STUDENT_3.getEmail()).block()).getEmail());
    Assert.assertEquals(TestUtil.STUDENT_3.getPhone(), Objects.requireNonNull(studentByCourseAndEmailReadOps.findByCourseAndEmail(TestUtil.STUDENT_3.getCourse(), TestUtil.STUDENT_3.getEmail()).block()).getPhone());
    Assert.assertEquals(TestUtil.STUDENT_3.getCourse(),Objects.requireNonNull(studentByCourseAndEmailReadOps.findByCourseAndEmail(TestUtil.STUDENT_3.getCourse(), TestUtil.STUDENT_3.getEmail()).block()).getCourse());

    Assert.assertEquals(TestUtil.STUDENT_5.getEmail(), Objects.requireNonNull(studentByCourseAndEmailReadOps.findByCourseAndEmail(TestUtil.STUDENT_5.getCourse(), TestUtil.STUDENT_5.getEmail()).block()).getEmail());
    Assert.assertEquals(TestUtil.STUDENT_5.getPhone(), Objects.requireNonNull(studentByCourseAndEmailReadOps.findByCourseAndEmail(TestUtil.STUDENT_5.getCourse(), TestUtil.STUDENT_5.getEmail()).block()).getPhone());
    Assert.assertEquals(TestUtil.STUDENT_5.getCourse(),Objects.requireNonNull(studentByCourseAndEmailReadOps.findByCourseAndEmail(TestUtil.STUDENT_5.getCourse(), TestUtil.STUDENT_5.getEmail()).block()).getCourse());

  }

  @Test
  public void findByCourse() {
    Assert.assertEquals(Long.valueOf(4), studentByCourseAndEmailReadOps.count().block());
    Assert.assertEquals(2, Objects.requireNonNull(studentByCourseAndEmailReadOps.findByCourse("NS").collectList().block()).size());
    Assert.assertEquals(2, Objects.requireNonNull(studentByCourseAndEmailReadOps.findByCourse("CS").collectList().block()).size());
  }
}