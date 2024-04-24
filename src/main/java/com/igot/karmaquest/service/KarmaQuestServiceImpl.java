package com.igot.karmaquest.service;
import com.igot.karmaquest.cassandrautils.CassandraConnectionManager;
import com.igot.karmaquest.cassandrautils.CassandraOperation;
import com.igot.karmaquest.util.Constants;
import java.util.ArrayList;
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
    public Object getInterest(String interestId) {
        logger.info("KarmaQuestServiceImpl::getUserDetails");
        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put(Constants.INTEREST_ID, interestId);
        List<String> fields = new ArrayList<>();
        return cassandraOperation.getRecordsByPropertiesByKey("sunbird", "interest_capture", propertyMap, fields,
            interestId);

    }

    @Override
    public Object insertInterest(Map<String, Object> createInterest) {
        logger.info("KarmaQuestServiceImpl::insertInterest");
        return cassandraOperation.insertRecord("sunbird", "interest_capture", createInterest);
    }

}


