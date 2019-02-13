package com.example.app.domain.repository;

import com.example.app.infrastructure.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    List<UserEntity> selectAll();
}
