package org.example.fatefork.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.example.fatefork.entity.User;
import org.example.fatefork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 认证控制器
 *
 * 改进点：
 * - 删除了冗余的 @PostMapping("/login") 方法
 *   （Spring Security 的表单登录通过 loginProcessingUrl("/auth/login") 自动处理）
 * - 添加参数验证（@NotBlank, @Size）
 * - 改进错误处理和返回值
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 处理用户注册
     *
     * @param username 用户名（3-20字符）
     * @param password 密码（至少6字符）
     * @param nickname 昵称（可选，最多20字符）
     * @param result 验证结果
     * @param redirectAttributes 重定向属性
     * @return 重定向到登录页或注册页
     */
    @PostMapping("/register")
    public String register(
            @NotBlank(message = "用户名不能为空")
            @Size(min = 3, max = 20, message = "用户名长度必须在3-20个字符之间")
            @RequestParam String username,

            @NotBlank(message = "密码不能为空")
            @Size(min = 6, message = "密码长度至少6个字符")
            @RequestParam String password,

            @Size(max = 20, message = "昵称长度不能超过20个字符")
            @RequestParam(required = false) String nickname,

            BindingResult result,
            RedirectAttributes redirectAttributes) {

        // 检查验证错误
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error",
                    result.getFieldError() != null ?
                            result.getFieldError().getDefaultMessage() :
                            "输入参数不符合要求");
            return "redirect:/register";
        }

        try {
            User user = userService.registerUser(username, password, nickname);
            redirectAttributes.addFlashAttribute("success", "注册成功！请登录。");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }

    /**
     * 用户登出
     *
     * 说明：Spring Security 会自动处理 /auth/logout 请求
     * 此方法是备用方法，通常不需要
     */
    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }
}