package com.pvdong.ls3mongodb.service;

import com.pvdong.ls3mongodb.entity.Account;
import com.pvdong.ls3mongodb.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }
        return new AccountDetails(account);
    }

    @Transactional
    public UserDetails loadUserById(String id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Account not found with id: " + id)
        );
        return new AccountDetails(account);
    }
}
