package com.igot.karmaquest.controller;

import com.igot.karmaquest.service.KarmaQuestServiceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mahesh RV
 * @author Ruksana
 */
@RestController
@RequestMapping("/karma/quests")
public class KarmaQuestController {
    @Autowired
    KarmaQuestServiceImpl serviceClass;

    @GetMapping("/getInterest/{interestId}")
    public Object processInterests(@PathVariable String interestId) {
        return serviceClass.getInterest(interestId);
    }

    @PostMapping("/create")
    public Object createInterest(@RequestBody Map<String, Object> requestBodyMap) {
        return serviceClass.insertInterest(requestBodyMap);
    }

}
