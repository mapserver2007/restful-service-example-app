package com.example.app.domain.service;

import com.example.app.domain.repository.UserMapper;
import com.example.app.infrastructure.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<UserEntity> selectAll() {
        return userMapper.selectAll();
    }
}
