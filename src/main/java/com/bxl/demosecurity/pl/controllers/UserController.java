package com.bxl.demosecurity.pl.controllers;

import com.bxl.demosecurity.bl.service.UserDetailServiceImpl;
import com.bxl.demosecurity.dal.entities.UserEntity;
import com.bxl.demosecurity.dal.enums.RoleEnum;
import com.bxl.demosecurity.pl.dto.AuthResponse;
import com.bxl.demosecurity.pl.form.LoginForm;
import com.bxl.demosecurity.pl.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final JwtUtil utils;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailServiceImpl securityService;

    public UserController(JwtUtil utils, PasswordEncoder passwordEncoder, UserDetailServiceImpl securityService) {
        this.utils = utils;
        this.passwordEncoder = passwordEncoder;
        this.securityService = securityService;
    }

    @PostMapping(path = {"/signIn"})
    public ResponseEntity<AuthResponse> signInAction(
            HttpServletRequest request,
            @RequestBody LoginForm form
    ) {
        System.out.println(request);
        UserDetails user = this.securityService.loadUserByUsername(form.username);

        if (passwordEncoder.matches(form.password, user.getPassword())) {
            return ResponseEntity.ok(new AuthResponse(utils.generateToken(user), user));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping(path = {"/register"})
    public ResponseEntity<AuthResponse> registerAction(
            @RequestBody LoginForm form
    ) {
//        return ResponseEntity.ok(null);
        UserEntity entity = new UserEntity();
        entity.setUsername(form.username);
        entity.setPassword(passwordEncoder.encode(form.password));
        entity.setRole(RoleEnum.USER);

        UserDetails user = this.securityService.insert(entity);
        return ResponseEntity.ok(new AuthResponse(utils.generateToken(user), user));
    }

    @PostMapping(path = {"/registerAdmin"})
    public ResponseEntity<AuthResponse> registerAdminAction(
            @RequestBody LoginForm form
    ) {
//        return ResponseEntity.ok(null);
        UserEntity entity = new UserEntity();
        entity.setUsername(form.username);
        entity.setPassword(passwordEncoder.encode(form.password));
        entity.setRole(RoleEnum.ADMIN);

        UserDetails user = this.securityService.insert(entity);
        return ResponseEntity.ok(new AuthResponse(utils.generateToken(user), user));
    }
}
