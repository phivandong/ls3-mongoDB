package com.pvdong.ls3mongodb.service;

import com.pvdong.ls3mongodb.entity.Account;
import com.pvdong.ls3mongodb.entity.JwtRequest;
import com.pvdong.ls3mongodb.entity.JwtResponse;

public interface AccountService {
    Account registration(Account account);
    Account findAccountById(String id);
    Account changePassword(String id, String password);
    JwtResponse login(JwtRequest jwtRequest);
}
