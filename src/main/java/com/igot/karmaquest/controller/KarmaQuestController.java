package com.igot.karmaquest.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.igot.karmaquest.service.KarmaQuestServiceImpl;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mahesh.vakkund
 */
@RestController
@RequestMapping("/karma/quests")
public class KarmaQuestController {
    @Autowired
    KarmaQuestServiceImpl serviceClass;

    @GetMapping("/processInterests/getUser/{userId}")
    public void processInterests(@PathVariable String userId) {
        serviceClass.getUserDetails(userId);
    }

    @PostMapping("/create")
    public String createInterest(@RequestBody Map<String, Object> requestBody) {
        return serviceClass.insertInterest(requestBody);
    }

}
