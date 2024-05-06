package com.igot.karmaquest.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.igot.karmaquest.cassandrautils.CassandraConnectionManager;
import com.igot.karmaquest.cassandrautils.CassandraOperation;
import com.igot.karmaquest.exceptions.ProjectCommonException;
import com.igot.karmaquest.util.Constants;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Mahesh RV
 * @author Ruksana
 */

@Component
public class KarmaQuestServiceImpl implements KarmaQuestService {

    @Autowired
    private CassandraConnectionManager connectionManager;

    @Autowired
    private CassandraOperation cassandraOperation;

    @Autowired
    private ObjectMapper objectMapper;

    private final Logger logger = LogManager.getLogger(getClass());

    @Override
    public Object getInterest(String interestId) {
        logger.info("KarmaQuestServiceImpl::getUserDetails");
        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put(Constants.INTEREST_ID, interestId);
        List<String> fields = new ArrayList<>();
        List<Map<String, Object>> response = cassandraOperation.getRecordsByPropertiesByKey(Constants.DATABASE, Constants.TABLE, propertyMap, fields, interestId);
        // Parse the "data" field from its string representation into a JSON object
        for (Map<String, Object> record : response) {
            parseStringifiedField(record, Constants.DATA);
            parseStringifiedField(record, Constants.DEMAND_ID);
            parseStringifiedField(record, Constants.USER_ID);
        }
        return response;
    }

    @Override
    public Object insertInterest(JsonNode requestBodyMap) {
        logger.info("KarmaQuestServiceImpl::insertInterest");
        Map<String, Object> parameterisedMap = new HashMap<>();
        UUID uuid = UUID.randomUUID();
        parameterisedMap.put(Constants.INTEREST_ID, uuid.toString());
        parameterisedMap.put(Constants.USER_ID,
            requestBodyMap.get(Constants.USER_ID_RQST).toString());
        parameterisedMap.put(Constants.INTEREST_FLAG,
            requestBodyMap.get(Constants.INTEREST_FLAG_RQST).booleanValue());
        parameterisedMap.put(Constants.DEMAND_ID,
            requestBodyMap.get(Constants.DEMAND_ID_RQST).toString());
        parameterisedMap.put(Constants.CREATED_ON,
            new Timestamp(Calendar.getInstance().getTime().getTime()));
        parameterisedMap.put(Constants.UPDATED_ON,
            new Timestamp(Calendar.getInstance().getTime().getTime()));
        try {
            String dataString = objectMapper.writeValueAsString(requestBodyMap);
            parameterisedMap.put(Constants.DATA, dataString);
            logger.info(dataString);
        } catch (JsonProcessingException e) {
            throw new ProjectCommonException("ERROR01", "Exception while mapping JsonNode",
                500);
        }
        return cassandraOperation.insertRecord(Constants.DATABASE, Constants.TABLE, parameterisedMap);
    }

    private void parseStringifiedField(Map<String, Object> record, String fieldName) {
        if (record.containsKey(fieldName)) {
            String fieldValue = (String) record.get(fieldName);
            try {
                if (fieldName.equals(Constants.DATA)) {
                    Map<String, Object> dataMap = objectMapper.readValue(fieldValue, new TypeReference<Map<String,Object>>(){});
                    record.put(fieldName, dataMap);
                } else {
                    // For user_id and other stringified fields, simply remove leading and trailing quotation marks
                    fieldValue = fieldValue.replaceAll(Constants.REGEX, "");
                    record.put(fieldName, fieldValue);
                }
            } catch (Exception e) {
                logger.error("Error parsing JSON field {}: {}", fieldName, e.getMessage());
                // Handle parsing error
            }
        }
    }

}


