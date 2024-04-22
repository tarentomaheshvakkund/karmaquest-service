package com.igot.karmaquest.cassandrautils;

import com.datastax.driver.core.*;
import com.datastax.driver.core.policies.DefaultRetryPolicy;

import com.igot.karmaquest.exceptions.ProjectCommonException;
import com.igot.karmaquest.util.Constants;
import com.igot.karmaquest.util.PropertiesCache;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mahesh RV
 */
@Component
public class CassandraConnectionManagerImpl implements CassandraConnectionManager {
    private static Cluster cluster;
    private static final Map<String, Session> cassandraSessionMap = new ConcurrentHashMap<>(2);
    public static final Logger logger = LogManager.getLogger(CassandraConnectionManagerImpl.class);
    ;
    List<String> keyspacesList = Arrays.asList(Constants.KEYSPACE_SUNBIRD, Constants.KEYSPACE_SUNBIRD_COURSES);

    @PostConstruct
    private void addPostConstruct() {
        logger.info("CassandraConnectionManagerImpl:: Initiating...");
        registerShutDownHook();
        createCassandraConnection();
        for (String keyspace : keyspacesList) {
            getSession(keyspace);
        }
        logger.info("CassandraConnectionManagerImpl:: Initiated.");
    }

    @Override
    public Session getSession(String keyspace) {
        Session session = cassandraSessionMap.get(keyspace);
        if (null != session) {
            return session;
        } else {
            logger.info("CassandraConnectionManagerImpl:: Creating connection for :: " + keyspace);
            Session session2 = cluster.connect(keyspace);
            cassandraSessionMap.put(keyspace, session2);
            return session2;
        }
    }

    private void createCassandraConnection() {
        try {
            PropertiesCache cache = PropertiesCache.getInstance();
            PoolingOptions poolingOptions = new PoolingOptions();
            poolingOptions.setCoreConnectionsPerHost(HostDistance.LOCAL, Integer.parseInt(cache.getProperty(Constants.CORE_CONNECTIONS_PER_HOST_FOR_LOCAL)));
            poolingOptions.setMaxConnectionsPerHost(HostDistance.LOCAL, Integer.parseInt(cache.getProperty(Constants.MAX_CONNECTIONS_PER_HOST_FOR_LOCAL)));
            poolingOptions.setCoreConnectionsPerHost(HostDistance.REMOTE, Integer.parseInt(cache.getProperty(Constants.CORE_CONNECTIONS_PER_HOST_FOR_REMOTE)));
            poolingOptions.setMaxConnectionsPerHost(HostDistance.REMOTE, Integer.parseInt(cache.getProperty(Constants.MAX_CONNECTIONS_PER_HOST_FOR_REMOTE)));
            poolingOptions.setMaxRequestsPerConnection(HostDistance.LOCAL, Integer.parseInt(cache.getProperty(Constants.MAX_REQUEST_PER_CONNECTION)));
            poolingOptions.setHeartbeatIntervalSeconds(Integer.parseInt(cache.getProperty(Constants.HEARTBEAT_INTERVAL)));
            poolingOptions.setPoolTimeoutMillis(Integer.parseInt(cache.getProperty(Constants.POOL_TIMEOUT)));
            String cassandraHost = (cache.getProperty(Constants.CASSANDRA_CONFIG_HOST));
            String[] hosts = null;
            if (StringUtils.isNotBlank(cassandraHost)) {
                hosts = cassandraHost.split(",");
            }
            cluster = createCluster(hosts, poolingOptions);

            final Metadata metadata = cluster.getMetadata();
            String msg = String.format("Connected to cluster: %s", metadata.getClusterName());
            logger.info(msg);

            for (final Host host : metadata.getAllHosts()) {
                msg = String.format("Datacenter: %s; Host: %s; Rack: %s", host.getDatacenter(), host.getAddress(), host.getRack());
                logger.info(msg);
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            throw new ProjectCommonException("Internal Server Error", e.getMessage(), 500);
        }
    }

    private static Cluster createCluster(String[] hosts, PoolingOptions poolingOptions) {
        Cluster.Builder builder = Cluster.builder().addContactPoints(hosts).withProtocolVersion(ProtocolVersion.V3).withRetryPolicy(DefaultRetryPolicy.INSTANCE).withTimestampGenerator(new AtomicMonotonicTimestampGenerator()).withPoolingOptions(poolingOptions);

        ConsistencyLevel consistencyLevel = getConsistencyLevel();
        logger.info("CassandraConnectionManagerImpl:createCluster: Consistency level = " + consistencyLevel);

        if (consistencyLevel != null) {
            builder.withQueryOptions(new QueryOptions().setConsistencyLevel(consistencyLevel));
        }

        return builder.build();
    }

    private static ConsistencyLevel getConsistencyLevel() {
        String consistency = PropertiesCache.getInstance().readProperty(Constants.SUNBIRD_CASSANDRA_CONSISTENCY_LEVEL);

        logger.info("CassandraConnectionManagerImpl:getConsistencyLevel: level = " + consistency);

        if (StringUtils.isBlank(consistency)) return null;

        try {
            return ConsistencyLevel.valueOf(consistency.toUpperCase());
        } catch (IllegalArgumentException exception) {
            logger.info("CassandraConnectionManagerImpl:getConsistencyLevel: Exception occurred with error message = " + exception.getMessage());
        }
        return null;
    }


    public static void registerShutDownHook() {
        Runtime runtime = Runtime.getRuntime();
        runtime.addShutdownHook(new ResourceCleanUp());
        logger.info("Cassandra ShutDownHook registered.");
    }


    static class ResourceCleanUp extends Thread {
        @Override
        public void run() {
            try {
                logger.info("started resource cleanup Cassandra.");
                for (Map.Entry<String, Session> entry : cassandraSessionMap.entrySet()) {
                    cassandraSessionMap.get(entry.getKey()).close();
                }
                if (cluster != null) {
                    cluster.close();
                }
                logger.info("completed resource cleanup Cassandra.");
            } catch (Exception ex) {
                logger.error(String.valueOf(ex));
            }
        }
    }
}