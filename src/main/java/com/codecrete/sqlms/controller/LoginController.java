package com.codecrete.sqlms.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Eliot Morris
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(LoginController.class);
    
    
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<String> login() {
        return new ResponseEntity<>("foo", HttpStatus.OK);
    }
}
