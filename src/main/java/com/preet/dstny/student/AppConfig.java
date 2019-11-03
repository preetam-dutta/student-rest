package com.preet.dstny.student;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableReactiveCassandraRepositories
public class AppConfig extends AbstractReactiveCassandraConfiguration {

  private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);

  @Value("${cassandra.keyspace}")
  private String cassandraKeyspace;

  @Value("${cassandra.clusterPort}")
  private int cassandraClusterPort;

  @Value("${cassandra.serviceContactPoint}")
  private String cassandraServiceContactPoint;

  @Value("${cassandra.connectTimeoutInSeconds}")
  private int cassandraConnectTimeout;

  @Override
  protected String getKeyspaceName() {
    return cassandraKeyspace;
  }

  @Override
  protected int getPort() {
    return cassandraClusterPort;
  }

  @Override
  public CassandraClusterFactoryBean cluster() {
    CassandraClusterFactoryBean bean =  super.cluster();
    bean.setJmxReportingEnabled(false);

    boolean isCassandraAvailable = false;
    while (!isCassandraAvailable) {
//    bean.setClusterName("preet-cluster");
      try {
        try (Socket socket = new Socket()) {
          socket.connect(new InetSocketAddress(cassandraServiceContactPoint, cassandraClusterPort), cassandraConnectTimeout);
          isCassandraAvailable = true;
        }
      } catch (IOException e) {
        LOG.error("Cassandra not available at [{}:{}], will try again after 30 seconds", cassandraServiceContactPoint, cassandraClusterPort);
        try {
          TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException ex) {
          LOG.error("Interrupted waiting for Cassandra connection, checking again.. ", ex);
        }
      }
      bean.setContactPoints(cassandraServiceContactPoint); //kubernetes service name
    }
    return bean;
  }
}
