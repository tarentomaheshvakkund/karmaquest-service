package com.igot.karmaquest.util;

/**
 * @author Mahesh RV
 */
public class Constants {
    public static final String KEYSPACE_SUNBIRD = "sunbird";
    public static final String KEYSPACE_SUNBIRD_COURSES = "sunbird_courses";
    public static final String CORE_CONNECTIONS_PER_HOST_FOR_LOCAL = "coreConnectionsPerHostForLocal";
    public static final String CORE_CONNECTIONS_PER_HOST_FOR_REMOTE = "coreConnectionsPerHostForRemote";
    public static final String MAX_CONNECTIONS_PER_HOST_FOR_LOCAL = "maxConnectionsPerHostForLocal";
    public static final String MAX_CONNECTIONS_PER_HOST_FOR_REMOTE = "maxConnectionsPerHostForRemote";
    public static final String MAX_REQUEST_PER_CONNECTION = "maxRequestsPerConnection";
    public static final String HEARTBEAT_INTERVAL = "heartbeatIntervalSeconds";
    public static final String POOL_TIMEOUT = "poolTimeoutMillis";
    public static final String CASSANDRA_CONFIG_HOST = "cassandra.config.host";
    public static final String SUNBIRD_CASSANDRA_CONSISTENCY_LEVEL = "sunbird_cassandra_consistency_level";

    private Constants() {
    }
}
