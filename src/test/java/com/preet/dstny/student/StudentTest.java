package com.preet.dstny.student;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfigTest.class)
public class StudentTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(StudentTest.class);

  @BeforeClass
  public static void startCassandraEmbedded() throws InterruptedException, TTransportException, ConfigurationException, IOException {
//    EmbeddedCassandraServerHelper.startEmbeddedCassandra(EmbeddedCassandraServerHelper.CASSANDRA_RNDPORT_YML_FILE);
    try {
      EmbeddedCassandraServerHelper.startEmbeddedCassandra("embedded-cassandra.yaml", "build/embeddedCassandra",
          TimeUnit.SECONDS.toMillis(60));
    } catch (Throwable e) {
      LOGGER.error("Embedded Cassandra Failed!");
      e.printStackTrace();
    }
//    EmbeddedCassandraServerHelper.getClusterName();
//    Cluster cluster = Cluster.builder()
//        .addContactPoints("127.0.0.1").withPort(9142).build();
//    Session session = cluster.connect();
  }

  @Test
  public void test() throws InterruptedException {
    TimeUnit.SECONDS.sleep(30);
    Assert.assertTrue(true);
  }

  @AfterClass
  public static void stopCassandraEmbedded() {
//    EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
  }
}
