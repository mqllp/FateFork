package org.example.fatefork.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理器
 *
 * 功能：
 * - 捕获应用中的所有异常
 * - 统一处理异常返回值
 * - 记录异常日志
 * - 提供友好的错误提示
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理认证异常
     *
     * @param e 认证异常
     * @return 错误视图
     */
    @ExceptionHandler(AuthenticationException.class)
    public ModelAndView handleAuthenticationException(AuthenticationException e) {
        logger.warn("认证异常: {}", e.getMessage());

        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", "认证失败，请检查用户名和密码");
        return mav;
    }

    /**
     * 处理非法参数异常
     *
     * @param e 非法参数异常
     * @return 错误视图
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleIllegalArgumentException(IllegalArgumentException e) {
        logger.warn("非法参数异常: {}", e.getMessage());

        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", "参数不符合要求: " + e.getMessage());
        return mav;
    }

    /**
     * 处理运行时异常
     *
     * @param e 运行时异常
     * @return 错误视图
     */
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleRuntimeException(RuntimeException e) {
        logger.error("运行时异常", e);

        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", "系统发生错误，请稍后重试");
        return mav;
    }

    /**
     * 处理所有异常（最后的兜底方案）
     *
     * @param e 异常
     * @return 错误视图
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        logger.error("未预期的异常", e);

        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", "发生了一个未知错误，请稍后重试");
        return mav;
    }
}