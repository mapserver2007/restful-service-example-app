package com.example.app.application.controller;

import com.example.app.application.session.SessionInfo;
import com.example.app.domain.object.LoginInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final SessionInfo sessionInfo;

    public LoginController(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    /**
     * このメソッドを実行すると、redisに値がセットされ、X-Auth-Tokenが返却される
     * id/pwに対して一意のTokenではなく、都度生成される(別セッション？)のがよくわからない。->そういう仕様っぽい。削除はきちんと自動でされてる。Expiredで。
     */
    @PostMapping("login")
    public ResponseEntity<Map<String, String>> login() {
//        String userId = loginInfo.getName();
//        String password = loginInfo.getPassword();

        // 実際はここで認証処理

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * このメソッドを実行すると、redisから"str"の値が削除され、X-Auth-Tokenが無効になる
     */
    @GetMapping("logout")
    public ResponseEntity<Void> logout(HttpSession httpSession) {
        httpSession.invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("info")
    public ResponseEntity<Map<String, String>> getInfo(Principal principal) {
        // Principalは認証ユーザ情報とかが取れる。
        // /loginでログインしたときに認証したユーザ名が取れる。-u test:testならtest, -u test2:test2ならtest2のようにちゃんと判別してる。

        Map<String, String> response = new HashMap<>();
        response.put("name", principal.getName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
