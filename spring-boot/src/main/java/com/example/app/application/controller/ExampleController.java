package com.example.app.application.controller;

import com.example.app.domain.object.User;
import com.example.app.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/example")
public class ExampleController {
    @Autowired
    private UserService userService;

    /**
     * curl -X GET http://localhost:8080/example/test1 -u test:test -i
     *  -> 通常のBasic認証。X-Auth-Tokenの値を取得する。
     * curl -X GET http://localhost:8080/example/test1 -H "X-Auth-Token: 42f00c49-e136-4428-a309-c1849dbf57c1"
     *  -> トークンを使って認証済みアクセス。
     */
    @GetMapping("test1")
    public ResponseEntity<Map<String, String>> example1() {
        Map<String, String> response = new HashMap<>();
        response.put("name", "test");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * curl -X GET http://localhost:8080/example1 -u test:test
     */
    @GetMapping("/db1")
    public ResponseEntity<List<User>> example2() {
        List<User> users = userService.selectAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping("/error500")
    public List<User> error500() {
        throw new RuntimeException("500");
    }

    @RequestMapping("/error400")
    public List<User> error400() {
        throw new RuntimeException("400");
    }
}
