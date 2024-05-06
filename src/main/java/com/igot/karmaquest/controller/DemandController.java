package com.igot.karmaquest.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.igot.karmaquest.dto.CustomResponse;
import com.igot.karmaquest.elasticsearch.dto.SearchCriteria;
import com.igot.karmaquest.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demand")
public class DemandController {
  @Autowired
  private DemandService demandService;

  @PostMapping("/create")
  public ResponseEntity<CustomResponse> create(@RequestBody JsonNode demandsDetails) {
    CustomResponse response = demandService.createDemand(demandsDetails);
    return new ResponseEntity<>(response, response.getResponseCode());
  }

  @GetMapping("/read/{id}")
  public ResponseEntity<?> read(@PathVariable String id) {
    CustomResponse response = demandService.readDemand(id);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
  @PostMapping("/filter")
  public ResponseEntity<?> search(@RequestBody SearchCriteria searchCriteria) {
    CustomResponse response = demandService.searchDemand(searchCriteria);
    return new ResponseEntity<>(response, response.getResponseCode());
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> delete(@PathVariable String id) {
    String response = demandService.delete(id);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
