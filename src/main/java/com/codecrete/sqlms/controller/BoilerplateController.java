package com.codecrete.sqlms.controller;

import com.codecrete.sqlms.model.BoilerplateInstruction;
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
    
    //
    @Autowired
    private BoilerplateService boilerplateService;
    
    @RequestMapping(path="function/system", method=RequestMethod.POST, consumes="application/json", produces="application/json")
    public ResponseEntity<String> systemFunction(@RequestBody BoilerplateInstruction instruction) {
        
        String json = this.boilerplateService.systemFunction();
    
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
    
    //
    @RequestMapping(path="table/audit", method=RequestMethod.POST, consumes="application/json", produces="application/json")
    public ResponseEntity<String> auditTable(@RequestBody BoilerplateInstruction instruction) {
    
        String table = instruction.getTable();
        String json = this.boilerplateService.auditTable(table);
    
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
    
    //
    @RequestMapping(path="trigger/after/insert", method=RequestMethod.POST, consumes="application/json", produces="application/json")
    public ResponseEntity<String> afterInsertTrigger(@RequestBody BoilerplateInstruction instruction) {
        
        String table = instruction.getTable();
        String json = this.boilerplateService.afterInsertTrigger(table);
        
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
    
    //
    @RequestMapping(path="trigger/before/delete", method=RequestMethod.POST, consumes="application/json", produces="application/json")
    public ResponseEntity<String> beforeDeleteTrigger(@RequestBody BoilerplateInstruction instruction) {
       
        String table = instruction.getTable();
        String json = this.boilerplateService.beforeDeleteTrigger(table);
    
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
    
    //
    @RequestMapping(path="trigger/before/update", method=RequestMethod.POST, consumes="application/json", produces="application/json")
    public ResponseEntity<String> beforeUpdateTrigger(@RequestBody BoilerplateInstruction instruction) {
    
        String table = instruction.getTable();
        String json = this.boilerplateService.beforeUpdateTrigger(table);
    
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    //
    @RequestMapping(path="procedure/delete", method=RequestMethod.POST, consumes="application/json", produces="application/json")
    public ResponseEntity<String> deleteProcedure(@RequestBody BoilerplateInstruction instruction) throws Exception {
    
        String table = instruction.getTable();
        String json = this.boilerplateService.deleteProcedure(table);
        
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
    
    //
    @RequestMapping(path="procedure/insert", method=RequestMethod.POST, consumes="application/json", produces="application/json")
    public ResponseEntity<String> insertProcedure(@RequestBody BoilerplateInstruction instruction) throws Exception {
        
        String table = instruction.getTable();
        String json = this.boilerplateService.insertProcedure(table);
    
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
    
    //
    @RequestMapping(path="procedure/select", method=RequestMethod.POST, consumes="application/json", produces="application/json")
    public ResponseEntity<String> selectProcedure(@RequestBody BoilerplateInstruction instruction) throws Exception {
        
        String table = instruction.getTable();
        String json = this.boilerplateService.selectProcedure(table);

        return new ResponseEntity<>(json, HttpStatus.OK);
    }
    
    //
    @RequestMapping(path="procedure/update", method=RequestMethod.POST, consumes="application/json", produces="application/json")
    public ResponseEntity<String> updateProcedure(@RequestBody BoilerplateInstruction instruction) throws Exception {
        
        String table = instruction.getTable();
        String json = this.boilerplateService.updateProcedure(table);
        
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
}
