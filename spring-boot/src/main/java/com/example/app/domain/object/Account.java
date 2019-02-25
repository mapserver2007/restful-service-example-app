package com.example.app.domain.object;

import lombok.Data;

import java.io.Serializable;

@Data
public class Account implements Serializable {
    int accountId;
    String loginId;
    String password;
    String mailAddress;
}
