package com.preet.dstny.student;

import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({StudentTest.class})
public class TestSuite extends TestCase {
//  @BeforeClass
//  public static void beforeSuitStarts() {
//    CassandraTestContainer.startContainer();
//  }

  @AfterClass
  public static void afterSuitCompletes() {
    CassandraTestContainer.stopContainer();
  }
}
