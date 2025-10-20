package org.example.fatefork.controller;

import org.example.fatefork.entity.*;
import org.example.fatefork.service.GameService;
import org.example.fatefork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 游戏控制器
 */
@Controller
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    /**
     * 处理换行符：将系统换行符替换为 HTML <br/> 标签
     */
    private String processContent(String content) {
        if (content == null) {
            return "";
        }
        return content.replace(System.lineSeparator(), "<br/>")
                .replace("\r\n", "<br/>")
                .replace("\n", "<br/>")
                .replace("\r", "<br/>");
    }

    /**
     * 开始游戏
     */
    @GetMapping("/start")
    public String startGame(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        User user = userService.findByUsername(username).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        StoryNode startNode = gameService.getStartNode();
        List<StoryChoice> choices = gameService.getChoicesForNode(startNode);

        // 处理内容中的换行符
        startNode.setContent(processContent(startNode.getContent()));

        model.addAttribute("user", user);
        model.addAttribute("currentNode", startNode);
        model.addAttribute("choices", choices);
        model.addAttribute("gameScore", 0);
        model.addAttribute("choicesPath", "");

        return "game";
    }

    /**
     * 处理选择
     */
    @PostMapping("/choice")
    public String makeChoice(@RequestParam String choiceId,
                             @RequestParam String currentNodeId,
                             @RequestParam Integer gameScore,
                             @RequestParam String choicesPath,
                             Authentication authentication,
                             Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        User user = userService.findByUsername(username).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        try {
            // 获取当前节点
            StoryNode currentNode = gameService.getStoryNode(currentNodeId);

            // 获取选择
            List<StoryChoice> choices = gameService.getChoicesForNode(currentNode);
            StoryChoice selectedChoice = choices.stream()
                    .filter(choice -> choice.getId().toString().equals(choiceId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("未找到选择"));

            // 更新分数
            int newScore = gameScore + selectedChoice.getScoreImpact();

            // 更新选择路径
            String newChoicesPath = choicesPath.isEmpty() ?
                    choiceId : choicesPath + "," + choiceId;

            // 检查是否是结局节点
            if (selectedChoice.getToNode() != null && selectedChoice.getToNode().getIsEndingNode()) {
                // 游戏结束，保存记录
                StoryNode endingNode = selectedChoice.getToNode();
                endingNode.setContent(processContent(endingNode.getContent()));

                gameService.saveGameRecord(user, endingNode.getEndingType(),
                        endingNode.getContent(), newChoicesPath, newScore);

                model.addAttribute("user", user);
                model.addAttribute("endingNode", endingNode);
                model.addAttribute("finalScore", newScore);
                model.addAttribute("choicesPath", newChoicesPath);

                return "ending";
            } else if (selectedChoice.getToNode() != null) {
                // 继续游戏
                StoryNode nextNode = selectedChoice.getToNode();

                // 处理内容中的换行符
                nextNode.setContent(processContent(nextNode.getContent()));

                List<StoryChoice> nextChoices = gameService.getChoicesForNode(nextNode);

                model.addAttribute("user", user);
                model.addAttribute("currentNode", nextNode);
                model.addAttribute("choices", nextChoices);
                model.addAttribute("gameScore", newScore);
                model.addAttribute("choicesPath", newChoicesPath);

                return "game";
            } else {
                // 错误情况
                return "redirect:/game/start";
            }

        } catch (Exception e) {
            model.addAttribute("error", "游戏过程中发生错误: " + e.getMessage());
            return "error";
        }
    }

    /**
     * 排行榜页面
     */
    @GetMapping("/leaderboard")
    public String leaderboard(Model model) {
        List<GameRecord> leaderboard = gameService.getLeaderboard();
        model.addAttribute("leaderboard", leaderboard);
        return "leaderboard";
    }

    /**
     * 用户历史记录
     */
    @GetMapping("/history")
    public String userHistory(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        User user = userService.findByUsername(username).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        List<GameRecord> userRecords = gameService.getUserGameRecords(user);
        model.addAttribute("user", user);
        model.addAttribute("userRecords", userRecords);

        return "history";
    }
}