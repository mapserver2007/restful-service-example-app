package com.example.app.domain.repository;

import com.example.app.domain.object.Account;

import java.util.List;

public interface AccountRepository {
    List<Account> accountList(String loginId);
}
