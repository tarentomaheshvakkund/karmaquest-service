package com.igot.karmaquest.service;

import java.util.Map;

/**
 * @author mahesh.vakkund
 */
public interface KarmaQuestService {
    void processInterests();

    Object getInterest(String interestId);

    Object insertInterest(Map<String, Object> createInterest);

}
