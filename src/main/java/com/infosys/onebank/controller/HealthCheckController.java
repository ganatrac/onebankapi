package com.infosys.onebank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.infosys.onebank.OneBankConstants.API_ROOT;
import static com.infosys.onebank.OneBankConstants.API_VERSION;

@RestController
@RequestMapping("/"+API_ROOT+"/"+API_VERSION)
public class HealthCheckController {

    @GetMapping(path = "/health", produces = "application/json")
    public ResponseEntity health() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "Ok");
        return new ResponseEntity(health, HttpStatus.OK);
    }
}
