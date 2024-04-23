package com.igot.karmaquest.cassandrautils;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.igot.karmaquest.util.Constants;


import java.util.*;
import java.util.stream.Collectors;


public final class CassandraUtil {

    private static CassandraPropertyReader propertiesCache = CassandraPropertyReader.getInstance();


    public static String getPreparedStatement(
            String keyspaceName, String tableName, Map<String, Object> map) {
        StringBuilder query = new StringBuilder();
        query.append(
                Constants.INSERT_INTO + keyspaceName + Constants.DOT + tableName + Constants.OPEN_BRACE);
        Set<String> keySet = map.keySet();
        query.append(String.join(",", keySet) + Constants.VALUES_WITH_BRACE);
        StringBuilder commaSepValueBuilder = new StringBuilder();
        for (int i = 0; i < keySet.size(); i++) {
            commaSepValueBuilder.append(Constants.QUE_MARK);
            if (i != keySet.size() - 1) {
                commaSepValueBuilder.append(Constants.COMMA);
            }
        }
        query.append(commaSepValueBuilder + Constants.CLOSING_BRACE);
        return query.toString();
    }


    public static List<Map<String, Object>> createResponse(ResultSet results) {
        List<Map<String, Object>> responseList = new ArrayList<>();
        Map<String, String> columnsMapping = fetchColumnsMapping(results);
        Iterator<Row> rowIterator = results.iterator();
        rowIterator.forEachRemaining(
                row -> {
                    Map<String, Object> rowMap = new HashMap<>();
                    columnsMapping
                            .entrySet()
                            .stream()
                            .forEach(entry -> rowMap.put(entry.getKey(), row.getObject(entry.getValue())));
                    responseList.add(rowMap);
                });
        return responseList;
    }

    public static Map<String, Object> createResponse(ResultSet results, String key) {
        Map<String, Object> responseList = new HashMap<>();
        Map<String, String> columnsMapping = fetchColumnsMapping(results);
        Iterator<Row> rowIterator = results.iterator();
        rowIterator.forEachRemaining(
                row -> {
                    Map<String, Object> rowMap = new HashMap<>();
                    columnsMapping
                            .entrySet()
                            .stream()
                            .forEach(entry -> {
                                rowMap.put(entry.getKey(), row.getObject(entry.getValue()));
                            });

                    responseList.put((String) rowMap.get(key), rowMap);
                });
        return responseList;
    }

    public static Map<String, String> fetchColumnsMapping(ResultSet results) {
        return results
                .getColumnDefinitions()
                .asList()
                .stream()
                .collect(
                        Collectors.toMap(
                                d -> propertiesCache.readProperty(d.getName()).trim(),
                                d -> d.getName()));
    }
}
