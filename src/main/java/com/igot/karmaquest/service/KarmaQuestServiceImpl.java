package com.igot.karmaquest.service;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Update;
import com.datastax.driver.core.querybuilder.Update.Assignments;
import com.igot.karmaquest.cassandrautils.CassandraConnectionManager;
import com.igot.karmaquest.cassandrautils.CassandraOperation;
import com.igot.karmaquest.util.Constants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public void getUserDetails(String userId) {
        logger.info("KarmaQuestServiceImpl::getUserDetails");
        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put(Constants.USER_ID_LOWER, userId);
        List<String> fields = null;
        cassandraOperation.getRecordsByPropertiesByKey("sunbird", "user", propertyMap, fields, userId);

    }

    @Override
    public String insertInterest(Map<String, Object> createInterest) {
        logger.info("KarmaQuestServiceImpl::insertInterest");
        return cassandraOperation.insertRecord("sunbird", "intesrest_capture", createInterest);
    }

}


