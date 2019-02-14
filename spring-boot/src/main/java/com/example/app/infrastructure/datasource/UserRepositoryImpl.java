package com.example.app.infrastructure.datasource;

import com.example.app.domain.repository.UserRepository;
import com.example.app.domain.object.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserRepositoryImpl extends UserRepository {
    @Override
    List<User> selectAll();
}
