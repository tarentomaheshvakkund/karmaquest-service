package com.igot.karmaquest.cassandrautils;

import com.datastax.driver.core.Session;

/**
 * @author Mahesh RV
 */
public interface CassandraConnectionManager {

    Session getSession(String keyspaceName);

}