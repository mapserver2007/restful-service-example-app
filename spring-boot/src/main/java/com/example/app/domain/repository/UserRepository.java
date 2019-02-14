package com.example.app.domain.repository;

import com.example.app.domain.object.User;

import java.util.List;

public interface UserRepository {
    List<User> selectAll();
}
