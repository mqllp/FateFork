package org.example.fatefork.controller;

import org.example.fatefork.entity.User;
import org.example.fatefork.service.GameService;
import org.example.fatefork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页控制器
 */
@Controller
@RequestMapping("/")
public class HomeController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private GameService gameService;
    
    /**
     * 首页
     */
    @GetMapping
    public String home(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.findByUsername(username).orElse(null);
            if (user != null) {
                model.addAttribute("user", user);
                model.addAttribute("gameCount", gameService.getUserGameCount(user));
                model.addAttribute("maxScore", gameService.getUserMaxScore(user));
            }
        }
        return "index";
    }
    
    /**
     * 登录页面
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    /**
     * 注册页面
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }
}

