package com.preet.dstny.student;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.preet.dstny.student.data.Student;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.CassandraContainer;

import java.net.InetSocketAddress;
import java.util.UUID;

@UtilityClass
public class TestUtil {

  private static final Logger LOG = LoggerFactory.getLogger(TestUtil.class);

  private static final String STUDENT_KEYSPACE = "student_test";

  private static final String CREATE_KEYSPACE_CQL = "create keyspace if not exists "+
      STUDENT_KEYSPACE
      +" with replication = {'class':'SimpleStrategy','replication_factor':1}";

  private static final String CREATE_STUDENT_CQL = "create table " + STUDENT_KEYSPACE + ".student (\n" +
      "  id uuid,\n" +
      "  name text,\n" +
      "  phone double,\n" +
      "  email text,\n" +
      "  course text,\n" +
      "  primary key(id)\n" +
      ");";

  private static final String CREATE_STUDENT_BY_COURSE_AND_EMAIL_CQL = "create table " + STUDENT_KEYSPACE + ".student_by_course_and_email (\n" +
      "  course text,\n" +
      "  email text,\n" +
      "  phone double,\n" +
      "  name text,\n" +
      "  id uuid,\n" +
      "  primary key((course), email, phone, name)\n" +
      ");";

  private static final String CREATE_STUDENT_BY_COURSE_AND_PHONE_CQL = "create table " + STUDENT_KEYSPACE + ".student_by_course_and_phone (\n" +
      "  course text,\n" +
      "  phone double,\n" +
      "  email text,\n" +
      "  name text,\n" +
      "  id uuid,\n" +
      "  primary key((course), phone, email, name)\n" +
      ");";

  private static final String DROP_STUDENT_KEYSPACE_CQL = "drop keyspace if exists " + STUDENT_KEYSPACE;
  private static final String DROP_STUDENT_CQL = "drop table if exists " + STUDENT_KEYSPACE + ".student";
  private static final String DROP_STUDENT_BY_COURSE_AND_EMAIL_CQL = "drop table if exists " + STUDENT_KEYSPACE + ".student_by_course_and_email";
  private static final String DROP_STUDENT_BY_COURSE_AND_PHONE_CQL = "drop table if exists " + STUDENT_KEYSPACE + ".student_by_course_and_phone";

  private static final String TRUNCATE_STUDENT_CQL = "truncate table " + STUDENT_KEYSPACE + ".student";
  private static final String TRUNCATE_STUDENT_BY_COURSE_AND_EMAIL_CQL = "truncate table " + STUDENT_KEYSPACE + ".student_by_course_and_email";
  private static final String TRUNCATE_STUDENT_BY_COURSE_AND_PHONE_CQL = "truncate table " + STUDENT_KEYSPACE + ".student_by_course_and_phone";

  private static volatile CassandraContainer cassandraContainer;

  public static final Student STUDENT_1 = new Student(UUID.randomUUID(), "CS", "preet@test.com", 3344556677d, "Preetam Dutta");
  public static final Student STUDENT_2 = new Student(UUID.randomUUID(), "CS", "vipin@test.com", 3344556666d, "Vipin Tondak");
  public static final Student STUDENT_3 = new Student(UUID.randomUUID(), "NS", "anuraag@test.com", 3344556655d, "Anuraag Bansal");
  public static final Student STUDENT_4 = new Student(UUID.randomUUID(), "NS", "amba@test.com", 3344556644d, "Amba Prasad");
  public static final Student STUDENT_5 = new Student(UUID.randomUUID(), "NS", "rajesh@test.com", 3344556633d, "Rajesh Sahay");

  private static void createContainer() {
    if (cassandraContainer == null) {
      cassandraContainer = new CassandraContainer();
    }
  }

  public static void startContainer() {
    if (cassandraContainer == null) {
      createContainer();
    }
    if (!cassandraContainer.isRunning()) {
      cassandraContainer.start();
    }
  }

  public static void stopContainer() {
    if (cassandraContainer != null && cassandraContainer.isRunning()) {
      cassandraContainer.stop();
    }
  }

  public static void setupStudentKeyspaceAndTables() throws CassandraContainerException {
    if (!cassandraContainer.isRunning()) {
      throw new CassandraContainerException("Cannot create keyspace[" + STUDENT_KEYSPACE + "], container not running");
    }
    InetSocketAddress cassandraContactPoint = new InetSocketAddress(cassandraContainer.getContainerIpAddress(), cassandraContainer.getMappedPort(9042));
    Cluster cluster = Cluster.builder()
        .addContactPointsWithPorts(cassandraContactPoint)
        .withoutJMXReporting()
        .build();
    Session session = cluster.connect();
    session.execute(CREATE_KEYSPACE_CQL);
    session.execute(CREATE_STUDENT_CQL);
    session.execute(CREATE_STUDENT_BY_COURSE_AND_EMAIL_CQL);
    session.execute(CREATE_STUDENT_BY_COURSE_AND_PHONE_CQL);
    session.close();
    cluster.close();
  }

  public static String getStudentKeyspace() {
    return STUDENT_KEYSPACE;
  }

  public static int getPort() throws CassandraContainerException {
    if (!cassandraContainer.isRunning()) {
      throw new CassandraContainerException("Cannot retrieve port, container not running");
    }
    return cassandraContainer.getMappedPort(9042);
  }

  public static String getCassandraContainerHost() throws CassandraContainerException {
    if (!cassandraContainer.isRunning()) {
      throw new CassandraContainerException("Cannot retrieve Cassandra container host, container not running");
    }
    return cassandraContainer.getContainerIpAddress();
  }

  public static void dropStudentKeyspace() throws CassandraContainerException {
    if (!cassandraContainer.isRunning()) {
      throw new CassandraContainerException("Cannot drop keyspace[" + STUDENT_KEYSPACE + "], container not running");
    }
    InetSocketAddress cassandraContactPoint = new InetSocketAddress(cassandraContainer.getContainerIpAddress(), cassandraContainer.getMappedPort(9042));
    Cluster cluster = Cluster.builder()
        .addContactPointsWithPorts(cassandraContactPoint)
        .withoutJMXReporting()
        .build();
    Session session = cluster.connect();
    session.execute(DROP_STUDENT_BY_COURSE_AND_EMAIL_CQL);
    session.execute(DROP_STUDENT_BY_COURSE_AND_PHONE_CQL);
    session.execute(DROP_STUDENT_CQL);
    session.execute(DROP_STUDENT_KEYSPACE_CQL);
    session.close();
    cluster.close();
  }

  public static void truncateTables() throws CassandraContainerException {
    if (!cassandraContainer.isRunning()) {
      throw new CassandraContainerException("Cannot drop keyspace[" + STUDENT_KEYSPACE + "], container not running");
    }
    InetSocketAddress cassandraContactPoint = new InetSocketAddress(cassandraContainer.getContainerIpAddress(), cassandraContainer.getMappedPort(9042));
    Cluster cluster = Cluster.builder()
        .addContactPointsWithPorts(cassandraContactPoint)
        .withoutJMXReporting()
        .build();
    Session session = cluster.connect();
    session.execute(TRUNCATE_STUDENT_CQL);
    session.execute(TRUNCATE_STUDENT_BY_COURSE_AND_EMAIL_CQL);
    session.execute(TRUNCATE_STUDENT_BY_COURSE_AND_PHONE_CQL);
    session.close();
    cluster.close();
  }

}
