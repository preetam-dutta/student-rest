package com.preet.dstny.student;

import com.preet.dstny.student.data.*;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    DefaultStudentWriteOpsTest.class,
    StudentReadOpsTest.class,
    StudentByCourseAndPhoneReadOpsTest.class,
    StudentByCourseAndEmailReadOpsTest.class})
public class TestSuite extends TestCase {
  // Stops the Cassandra container once all the tests are completed from all test classes
  @AfterClass
  public static void afterSuitCompletes() {
    TestUtil.stopContainer();
  }
}
