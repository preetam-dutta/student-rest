package com.preet.dstny.student;

import com.datastax.driver.core.policies.ReconnectionPolicy;
import com.datastax.driver.core.policies.RetryPolicy;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

@Configuration
@EnableReactiveCassandraRepositories
public class AppConfig extends AbstractReactiveCassandraConfiguration {

  @Override
  protected String getKeyspaceName() {
    return "test1";
  }

  @Override
  protected int getPort() {
    return 9042;
  }

  @Override
  public CassandraClusterFactoryBean cluster() {
    CassandraClusterFactoryBean bean =  super.cluster();
    bean.setJmxReportingEnabled(false);
    bean.setClusterName("preet-cluster");
    bean.setContactPoints("cassandra"); //kubernetes service name
    return bean;
  }

}
