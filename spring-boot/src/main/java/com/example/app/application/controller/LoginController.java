package com.example.app.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
//    private final ProviderSignInUtils signInUtils;

//    @Autowired
//    public LoginController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository connectionRepository) {
//        signInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
//    }
//
//    @GetMapping("signup")
//    public String signUp(WebRequest request) {
//        Connection<?> connection = signInUtils.getConnectionFromSession(request);
//        if (connection != null) {
//            // TODO
//        }
//
//
//
//        return "owata";
//    }

    /**
     * このメソッドを実行すると、redisに値がセットされ、X-Auth-Tokenが返却される
     * id/pwに対して一意のTokenではなく、都度生成される(別セッション？)のがよくわからない。->そういう仕様っぽい。削除はきちんと自動でされてる。Expiredで。
     */
    @PostMapping("login")
    public ResponseEntity<Map<String, String>> login(Principal principal) {
        // ログインが成功するとここに到達する。失敗した場合は到達前に401を返して終了。
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
        Map<String, String> response = new HashMap<>();

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

//        response.put("name_from_session", auth.getName());
        response.put("name", principal.getName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
