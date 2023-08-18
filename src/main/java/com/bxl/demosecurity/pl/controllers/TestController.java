package com.bxl.demosecurity.pl.controllers;

import com.bxl.demosecurity.dal.enums.RoleEnum;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/testAuthenticated")
    private String testAuthenticated() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return "Authenticated !";
    }

    @GetMapping("/testAdmin")
    @Secured("ROLE_ADMIN")
    private String testAdmin() {
        return "You're an Admin !";
    }

    @GetMapping("/testPermitAll")
    private String testPermitAll() {
        return "Permit all !";
    }
}
