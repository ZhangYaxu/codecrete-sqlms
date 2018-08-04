package com.codecrete.sqlms.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

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
    
//    @Autowired
//    AuthenticationManager manager;
    
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<String> login(HttpServletRequest request, String user, String pass) {
    
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, pass);
//        Authentication authentication = manager.authenticate(token);
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(authentication);
//        HttpSession session = request.getSession(true);
//        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, context);
        
        return new ResponseEntity<>("yeup", HttpStatus.OK);
    }
}
