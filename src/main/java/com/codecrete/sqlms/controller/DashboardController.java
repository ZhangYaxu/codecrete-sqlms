package com.codecrete.sqlms.controller;

import com.codecrete.domain.model.User;
import com.codecrete.sqlms.model.DashboardReport;
import com.codecrete.sqlms.model.DashboardInstruction;
import com.codecrete.sqlms.service.DashboardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    
    private static final Logger LOG = LogManager.getLogger(DashboardController.class);

    @Autowired
    private DashboardService dashboardService;

    @RequestMapping(method=RequestMethod.POST, consumes="application/json", produces="application/json")
    public ResponseEntity<DashboardReport> dashboard(@RequestBody DashboardInstruction instruction) {

        User user = instruction.getUser();
        
        DashboardReport dashboardReport = this.dashboardService.getDashboard(user);

        return new ResponseEntity<>(dashboardReport, HttpStatus.OK);
    }
}