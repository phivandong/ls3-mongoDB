package com.pvdong.ls3mongodb.controller;

import com.pvdong.ls3mongodb.entity.Account;
import com.pvdong.ls3mongodb.entity.JwtRequest;
import com.pvdong.ls3mongodb.entity.JwtResponse;
import com.pvdong.ls3mongodb.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(path = "/account/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable String id) {
        return new ResponseEntity<>(accountService.findAccountById(id), HttpStatus.OK);
    }

    @PostMapping(path = "/account")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        return new ResponseEntity<>(accountService.registration(account), HttpStatus.CREATED);
    }

    @PostMapping(path = "/account/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody JwtRequest jwtRequest) {
        return new ResponseEntity<>(accountService.login(jwtRequest), HttpStatus.OK);
    }

    @PostMapping(path = "/account/change_password/{id}")
    public ResponseEntity<Account> changePassword(@PathVariable String id, @RequestBody String password) {
        return new ResponseEntity<>(accountService.changePassword(id, password), HttpStatus.OK);
    }
}
