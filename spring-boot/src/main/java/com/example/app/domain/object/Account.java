package com.example.app.domain.object;

import lombok.Data;

import java.io.Serializable;

@Data
public class Account implements Serializable {
    String loginId;
    String password;
}
