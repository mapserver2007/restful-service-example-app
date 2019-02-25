package com.example.app.domain.service;

import com.example.app.domain.object.Account;
import com.example.app.domain.object.AccountDetails;
import com.example.app.domain.repository.AccountRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AccountService extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {}

    @Override
    protected UserDetails retrieveUser(String userName, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        List<Account> account = accountRepository.accountList(userName);

        UserDetails accountDetails = null;
        if (!account.isEmpty()) {
            String password = authentication.getCredentials().toString();
            if (BCrypt.checkpw(password, account.get(0).getPassword())) {
                Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("USER");
                accountDetails = new AccountDetails(account.get(0), authorities);
            }
        }

        if (accountDetails == null) {
            throw new BadCredentialsException("Login failure");
        }

        return accountDetails;
    }
}
