package com.preet.dstny.student;

import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CassandraInitAndCleanupRule extends ExternalResource {

  private static final Logger LOG = LoggerFactory.getLogger(CassandraInitAndCleanupRule.class);

  @Override
  protected void before() throws Throwable {
    LOG.info("starting cassandra container and setting up keyspace");
    TestUtil.startContainer();
    TestUtil.setupStudentKeyspaceAndTables();
  }

  @Override
  protected void after() {
    try {
      LOG.info("cleaning up keyspace");
      TestUtil.dropStudentKeyspace();
    } catch (CassandraContainerException e) {
      LOG.error("Unable to drop keyspace.", e);
    }
  }

}
