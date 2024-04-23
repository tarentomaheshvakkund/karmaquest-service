package com.igot.karmaquest.service;

import com.igot.karmaquest.cassandrautils.CassandraConnectionManager;
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

    public static Logger logger = LogManager.getLogger(KarmaQuestServiceImpl.class);

    @Override
    public void processInterests() {
        logger.info("Inside trial Run");
    }


}


