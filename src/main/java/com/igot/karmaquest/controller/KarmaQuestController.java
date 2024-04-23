package com.igot.karmaquest.controller;

import com.igot.karmaquest.service.KarmaQuestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/processInterests")
    public void processInterests() {
        serviceClass.processInterests();
    }
}
