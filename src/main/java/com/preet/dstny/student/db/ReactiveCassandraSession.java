package com.preet.dstny.student.db;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.preet.dstny.student.api.CassandraSession;
import org.springframework.data.cassandra.core.cql.ReactiveCqlTemplate;
import org.springframework.data.cassandra.core.cql.session.DefaultBridgedReactiveSession;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class ReactiveCassandraSession implements CassandraSession {

  private static AtomicReference<Session> sessionAtomicReference = new AtomicReference<>();

  private Session getSession() {
    Cluster cluster = Cluster.builder()
        .addContactPointsWithPorts(new InetSocketAddress("cassandra", 9042))
        .withClusterName("preet-cluster")
        .withoutJMXReporting()
        .build();
    return cluster.connect("test1");
  }

  @Override
  public ReactiveCqlTemplate cql() {
    Session ses = sessionAtomicReference.updateAndGet(session -> {
      if (session == null || session.isClosed()) {
        return getSession();
      } else {
        return session;
      }
    });
    return new ReactiveCqlTemplate(new DefaultBridgedReactiveSession(ses));
  }
  
}
