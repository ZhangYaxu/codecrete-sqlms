package com.codecrete.sqlms.controller;

import com.codecrete.sqlms.model.BuildInstruction;
import com.codecrete.sqlms.model.BuildReport;
import com.codecrete.sqlms.service.BuildService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;

/**
 * @author Eliot Morris
 */
@RestController
@RequestMapping("/build")
public class BuildController {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(BuildController.class);
    
    @Autowired
    private BuildService buildService;
    
    @RequestMapping(method=RequestMethod.POST, consumes="application/json", produces="application/json")
    public ResponseEntity<BuildReport> build(@RequestBody BuildInstruction instruction) throws Exception {
        
        Path source = instruction.getSource();
        String domain = instruction.getDomain();
        
        BuildReport buildReport = this.buildService.build(source, domain);
        
        return new ResponseEntity<>(buildReport, HttpStatus.OK);
    }
}
