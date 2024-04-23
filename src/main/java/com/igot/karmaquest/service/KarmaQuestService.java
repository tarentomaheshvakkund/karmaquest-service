package com.igot.karmaquest.service;

import java.util.Map;

/**
 * @author mahesh.vakkund
 */
public interface KarmaQuestService {
    void processInterests();

    void getUserDetails(String userId);

    Object insertInterest(Map<String, Object> createInterest);

}
