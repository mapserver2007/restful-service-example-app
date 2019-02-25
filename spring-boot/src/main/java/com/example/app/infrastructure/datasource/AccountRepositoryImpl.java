package com.example.app.infrastructure.datasource;

import com.example.app.domain.object.Account;
import com.example.app.domain.repository.AccountRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AccountRepositoryImpl extends AccountRepository {
    @Override
    List<Account> accountList(@Param("loginId") String loginId);
}
