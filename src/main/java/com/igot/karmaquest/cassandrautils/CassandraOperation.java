package com.igot.karmaquest.cassandrautils;

import java.util.List;
import java.util.Map;

public interface CassandraOperation {

  List<Map<String, Object>> getRecordsByPropertiesByKey(String keyspaceName, String tableName,
      Map<String, Object> propertyMap, List<String> fields, String key);

  public Object insertRecord(String keyspaceName, String tableName, Map<String, Object> request);
}
