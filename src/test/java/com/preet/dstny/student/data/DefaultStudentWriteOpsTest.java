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

import java.util.Objects;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppTestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DefaultStudentWriteOpsTest {

  @Autowired
  private StudentWriteOps studentWriteOps;

  @Autowired
  private StudentReadOps studentReadOps;

  @Autowired
  private StudentByCourseAndEmailReadOps studentByCourseAndEmailReadOps;

  @Autowired
  private StudentByCourseAndPhoneReadOps studentByCourseAndPhoneReadOps;

  @ClassRule
  public static CassandraInitAndCleanupRule cassandraInitAndCleanupRule = new CassandraInitAndCleanupRule();

  @Before
  public void setUp() {
    studentWriteOps.createStudent(TestUtil.STUDENT_1);
    studentWriteOps.createStudent(TestUtil.STUDENT_2);
    studentWriteOps.createStudent(TestUtil.STUDENT_3);
    studentWriteOps.createStudent(TestUtil.STUDENT_4);
    studentWriteOps.createStudent(TestUtil.STUDENT_5);
  }

  @After
  public void tearDown() throws CassandraContainerException {
    TestUtil.truncateTables();
  }

  @Test
  public void createStudent() {
    Student student6 = new Student(UUID.randomUUID(), "NS", "mohit@test.com", 3344556622d, "Mohit Bhardwaj");
    Student student7 = new Student(UUID.randomUUID(), "CS", "shivendu@test.com", 3344556611d, "Shivendu Gautam");
    studentWriteOps.createStudent(student6);
    studentWriteOps.createStudent(student7);

    Assert.assertEquals(Long.valueOf(7), studentReadOps.count().block());

    Assert.assertEquals(TestUtil.STUDENT_1, studentReadOps.findStudentById(TestUtil.STUDENT_1.getId()).block());
    Assert.assertEquals(TestUtil.STUDENT_2, studentReadOps.findStudentById(TestUtil.STUDENT_2.getId()).block());
    Assert.assertEquals(TestUtil.STUDENT_3, studentReadOps.findStudentById(TestUtil.STUDENT_3.getId()).block());
    Assert.assertEquals(TestUtil.STUDENT_4, studentReadOps.findStudentById(TestUtil.STUDENT_4.getId()).block());
    Assert.assertEquals(TestUtil.STUDENT_5, studentReadOps.findStudentById(TestUtil.STUDENT_5.getId()).block());
    Assert.assertEquals(student6, studentReadOps.findStudentById(student6.getId()).block());
    Assert.assertEquals(student7, studentReadOps.findStudentById(student7.getId()).block());
  }

  @Test
  public void updateStudent() {
    Student aStudent = studentReadOps.findStudentById(TestUtil.STUDENT_1.getId()).block();
    Assert.assertNotNull(aStudent);
    aStudent.setEmail("preetam.dutta@test.com");
    aStudent.setPhone(1100220033d);
    aStudent.setCourse("UC");
    studentWriteOps.updateStudent(aStudent);

    Assert.assertEquals(Long.valueOf(5), studentReadOps.count().block());
    Assert.assertEquals(aStudent.getEmail(), Objects.requireNonNull(studentReadOps.findStudentById(aStudent.getId()).block()).getEmail());
    Assert.assertEquals(aStudent.getPhone(), Objects.requireNonNull(studentReadOps.findStudentById(aStudent.getId()).block()).getPhone());
    Assert.assertEquals(aStudent.getCourse(), Objects.requireNonNull(studentReadOps.findStudentById(aStudent.getId()).block()).getCourse());

    Assert.assertEquals(Long.valueOf(5), studentByCourseAndEmailReadOps.count().block());
    Assert.assertEquals(1, Objects.requireNonNull(studentByCourseAndEmailReadOps.findByCourse("CS").collectList().block()).size());
    Assert.assertEquals(1, Objects.requireNonNull(studentByCourseAndEmailReadOps.findByCourse("UC").collectList().block()).size());
    Assert.assertEquals(aStudent.getEmail(), Objects.requireNonNull(studentByCourseAndEmailReadOps.findByCourseAndEmail(aStudent.getCourse(), aStudent.getEmail()).block()).getEmail());
    Assert.assertEquals(aStudent.getPhone(), Objects.requireNonNull(studentByCourseAndEmailReadOps.findByCourseAndEmail(aStudent.getCourse(), aStudent.getEmail()).block()).getPhone());
    Assert.assertEquals(aStudent.getCourse(), Objects.requireNonNull(studentByCourseAndEmailReadOps.findByCourseAndEmail(aStudent.getCourse(), aStudent.getEmail()).block()).getCourse());

    Assert.assertEquals(Long.valueOf(5), studentByCourseAndPhoneReadOps.count().block());
    Assert.assertEquals(aStudent.getEmail(), Objects.requireNonNull(studentByCourseAndPhoneReadOps.findByCourseAndPhone(aStudent.getCourse(), aStudent.getPhone()).block()).getEmail());
    Assert.assertEquals(aStudent.getPhone(), Objects.requireNonNull(studentByCourseAndPhoneReadOps.findByCourseAndPhone(aStudent.getCourse(), aStudent.getPhone()).block()).getPhone());
    Assert.assertEquals(aStudent.getCourse(),Objects.requireNonNull(studentByCourseAndPhoneReadOps.findByCourseAndPhone(aStudent.getCourse(), aStudent.getPhone()).block()).getCourse());
  }

  @Test
  public void deleteStudent() {
    Student aStudent = studentReadOps.findStudentById(TestUtil.STUDENT_1.getId()).block();
    Assert.assertNotNull(aStudent);
    studentWriteOps.deleteStudent(aStudent.getId());

    Assert.assertEquals(Long.valueOf(4), studentReadOps.count().block());
    Assert.assertEquals(Long.valueOf(4), studentByCourseAndEmailReadOps.count().block());
    Assert.assertEquals(Long.valueOf(4), studentByCourseAndPhoneReadOps.count().block());
    Assert.assertEquals(1, Objects.requireNonNull(studentByCourseAndEmailReadOps.findByCourse("CS").collectList().block()).size());
  }
  
}