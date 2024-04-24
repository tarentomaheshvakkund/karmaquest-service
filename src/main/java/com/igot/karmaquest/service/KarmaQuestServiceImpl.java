package com.igot.karmaquest.service;
import com.igot.karmaquest.cassandrautils.CassandraConnectionManager;
import com.igot.karmaquest.cassandrautils.CassandraOperation;
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
 * @author mahesh.vakkund
 */
@Component
public class KarmaQuestServiceImpl implements KarmaQuestService {

    @Autowired
    private CassandraConnectionManager connectionManager;

    @Autowired
    private CassandraOperation cassandraOperation;

    public static Logger logger = LogManager.getLogger(KarmaQuestServiceImpl.class);

    @Override
    public void processInterests() {
        logger.info("KarmaQuestServiceImpl::processInterests");

    }

    @Override
    public Object getInterest(String interestId) {
        logger.info("KarmaQuestServiceImpl::getUserDetails");
        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put(Constants.INTEREST_ID, interestId);
        List<String> fields = new ArrayList<>();
        return cassandraOperation.getRecordsByPropertiesByKey("sunbird", "interest_capture", propertyMap, fields,
            interestId);

    }

    @Override
    public Object insertInterest(Map<String, Object> requestBodyMap) {
        logger.info("KarmaQuestServiceImpl::insertInterest");
        Map<String, Object> parameterisedMap = new HashMap<>();
        UUID uuid = UUID.randomUUID();
        parameterisedMap.put(Constants.INTEREST_ID, uuid.toString());
        parameterisedMap.put(Constants.USER_ID, requestBodyMap.get(Constants.USER_ID_RQST));
        parameterisedMap.put(Constants.INTEREST_FLAG, requestBodyMap.get(Constants.INTEREST_FLAG_RQST));
        parameterisedMap.put(Constants.DEMAND_ID, requestBodyMap.get(Constants.DEMAND_ID_RQST));
        parameterisedMap.put(Constants.CREATED_ON, new Timestamp(Calendar.getInstance().getTime().getTime()));
        parameterisedMap.put(Constants.UPDATED_ON, new Timestamp(Calendar.getInstance().getTime().getTime()));
        return cassandraOperation.insertRecord("sunbird", "interest_capture", parameterisedMap);
    }

}


