package com.preet.dstny.student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

@Lazy
@TestConfiguration
@ComponentScan(basePackages = {"com.preet.dstny.student.data","com.preet.dstny.student.controller"})
@EnableReactiveCassandraRepositories
public class AppTestConfiguration extends AbstractReactiveCassandraConfiguration {

  private static final Logger LOG = LoggerFactory.getLogger(AppTestConfiguration.class);

  @Override
  protected String getKeyspaceName() {
    return CassandraTestContainer.getStudentKeyspace();
  }

  @Override
  protected int getPort() {
    try {
      return CassandraTestContainer.getPort();
    } catch (CassandraContainerException e) {
      LOG.error("Cannot get CQL port of Cassandra container.", e);
    }
    return -1;
  }

  @Override
  public CassandraClusterFactoryBean cluster() {
    try {
      CassandraClusterFactoryBean bean = super.cluster();
      String cassandraIp = CassandraTestContainer.getCassandraContainerHost();
      int cassandraPort = CassandraTestContainer.getPort();
      bean.setJmxReportingEnabled(false);
      bean.setContactPoints(cassandraIp);
      bean.setPort(cassandraPort);
      LOG.info("CASSANDRA CONNECT: {}:{} ", cassandraPort, cassandraIp);
      return bean;
    } catch (CassandraContainerException e) {
      LOG.error("Cannot setup Cassandra cluster.", e);
    }
    return null;
  }

}
