package com.igot.karmaquest.service;

import java.util.Map;

/**
 * @author Mahesh RV
 * @author Ruksana
 */
public interface KarmaQuestService {

    Object getInterest(String interestId);

    Object insertInterest(Map<String, Object> requestBodyMap);

}
