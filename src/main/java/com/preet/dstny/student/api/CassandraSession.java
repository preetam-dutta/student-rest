package com.preet.dstny.student.api;

import org.springframework.data.cassandra.core.cql.ReactiveCqlTemplate;

public interface CassandraSession {
  ReactiveCqlTemplate cql();
}
