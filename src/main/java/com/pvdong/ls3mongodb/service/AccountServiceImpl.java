package com.pvdong.ls3mongodb.service;

import com.pvdong.ls3mongodb.config.JwtTokenProvider;
import com.pvdong.ls3mongodb.config.WebSecurityConfig;
import com.pvdong.ls3mongodb.entity.Account;
import com.pvdong.ls3mongodb.entity.JwtRequest;
import com.pvdong.ls3mongodb.entity.JwtResponse;
import com.pvdong.ls3mongodb.entity.SingletonCollector;
import com.pvdong.ls3mongodb.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    WebSecurityConfig webSecurityConfig;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public Account findAccountById(String id) {
        return accountRepository.findById(id).get();
    }

    @Override
    public Account registration(Account account) {
        if (accountExist(account.getUsername())) {
            throw new RuntimeException("There is an account with that username: " + account.getUsername());
        }
        account.setPassword(webSecurityConfig.passwordEncoder().encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public Account changePassword(String id, String password) {
        String newPassword = webSecurityConfig.passwordEncoder().encode(password);
        Account foundAccount = findAccountById(id);
        foundAccount.setPassword(newPassword);
        accountRepository.save(foundAccount);
        return foundAccount;
    }

    @Override
    public JwtResponse login(JwtRequest jwtRequest) {
        if (accountExist(jwtRequest.getUsername())) {
            Account account = accountRepository.findByUsername(jwtRequest.getUsername());
            if (passwordEncoder.matches(jwtRequest.getPassword(), account.getPassword())) {
                try {
                    String accountId = account.getId();
                    SingletonCollector singleton = SingletonCollector.getInstance();
                    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String jwtToken = jwtTokenProvider.generateToken((AccountDetails) authentication.getPrincipal());
                    String str = generateRandomStr(jwtToken);
                    singleton.put(str, accountId);
                    return new JwtResponse(jwtToken, str);
                } catch (AuthenticationException e) {
                    throw new RuntimeException("Invalid username/password");
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                throw new RuntimeException("Invalid password");
            }
        } else {
            throw new RuntimeException("Invalid username");
        }
    }

    private boolean accountExist(String username) {
        return accountRepository.findByUsername(username) != null;
    }

    private String generateRandomStr(String token) {
        String str = "";
        for (int i = 0; i < 30; i++) {
            str += token.charAt((int) (Math.random() * token.length()));
        }
        return str;
    }
}
