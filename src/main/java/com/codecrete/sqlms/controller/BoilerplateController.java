package com.codecrete.sqlms.controller;

import com.codecrete.sqlms.model.AfterInsertInstruction;
import com.codecrete.sqlms.model.DeleteInstruction;
import com.codecrete.sqlms.service.BoilerplateService;
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
@RequestMapping("/boilerplate")
public class BoilerplateController {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(BoilerplateController.class);
    
    @Autowired
    private BoilerplateService boilerplateService;
    
    @RequestMapping(path="procedures/delete", method=RequestMethod.POST, consumes="application/json", produces="application/json")
    public ResponseEntity<String> deleteProcedure(@RequestBody DeleteInstruction instruction) throws Exception {
    
        String table = instruction.getTable();
        
        String json = this.boilerplateService.deleteProcedure(table);
        
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
    
    @RequestMapping(path="triggers/after/insert", method=RequestMethod.POST, consumes="application/json", produces="application/json")
    public ResponseEntity<String> afterInsertTrigger(@RequestBody AfterInsertInstruction instruction) {
        
        String table = instruction.getTable();
        
        String json = this.boilerplateService.afterInsertTrigger(table);
        
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
}
