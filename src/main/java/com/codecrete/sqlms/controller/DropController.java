package com.codecrete.sqlms.controller;

import com.codecrete.sqlms.model.DropInstruction;
import com.codecrete.sqlms.model.DropReport;
import com.codecrete.sqlms.service.DropService;
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
 * @author Eliot Morris
 */
@RestController
@RequestMapping("/drop")
public class DropController {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(DropController.class);
    
    @Autowired
    private DropService dropService;

    @RequestMapping(method=RequestMethod.POST, consumes="application/json", produces="application/json")
    public ResponseEntity<DropReport> build(@RequestBody DropInstruction instruction) {

        String schema = instruction.getSchema();

        DropReport dropReport = this.dropService.drop(schema);

        return new ResponseEntity<>(dropReport, HttpStatus.OK);
    }
}
