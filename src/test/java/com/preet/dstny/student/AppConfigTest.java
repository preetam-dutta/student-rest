package com.preet.dstny.student;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

import java.io.IOException;

//@Configuration
//@EnableReactiveCassandraRepositories
public class AppConfigTest extends AbstractReactiveCassandraConfiguration {



  @Override
  protected String getKeyspaceName() {
    return "test";
  }

  @Override
  protected int getPort() {
    return 9142;
  }

  @Override
  public CassandraClusterFactoryBean cluster() {
    CassandraClusterFactoryBean bean =  super.cluster();
    bean.setJmxReportingEnabled(false);
    bean.setClusterName("test-cluster");
    bean.setContactPoints("127.0.0.1");
//    try {
//      EmbeddedCassandraServerHelper.startEmbeddedCassandra();
//    } catch (TTransportException | IOException | InterruptedException | ConfigurationException e) {
//      e.printStackTrace();
//    }
    return bean;
  }


}
