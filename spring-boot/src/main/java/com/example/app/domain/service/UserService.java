package com.example.app.domain.service;

import com.example.app.domain.object.User;
import com.example.app.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public List<User> selectAll() {
        return userRepository.selectAll();
    }
}
