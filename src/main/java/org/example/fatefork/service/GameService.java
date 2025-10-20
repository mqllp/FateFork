package org.example.fatefork.service;

import org.example.fatefork.entity.*;
import org.example.fatefork.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * 游戏服务类
 */
@Service
public class GameService {
    
    @Autowired
    private StoryNodeRepository storyNodeRepository;
    
    @Autowired
    private StoryChoiceRepository storyChoiceRepository;
    
    @Autowired
    private GameRecordRepository gameRecordRepository;
    
    private final Random random = new Random();
    
    /**
     * 获取起始节点
     */
    public StoryNode getStartNode() {
        return storyNodeRepository.findByIsStartNodeTrue()
                .orElseThrow(() -> new RuntimeException("未找到起始节点"));
    }
    
    /**
     * 根据节点ID获取剧情节点
     */
    public StoryNode getStoryNode(String nodeId) {
        return storyNodeRepository.findByNodeId(nodeId)
                .orElseThrow(() -> new RuntimeException("未找到剧情节点: " + nodeId));
    }
    
    /**
     * 获取节点的所有选择
     */
    public List<StoryChoice> getChoicesForNode(StoryNode node) {
        return storyChoiceRepository.findByFromNode(node);
    }
    
    /**
     * 处理随机事件
     */
    public boolean triggerRandomEvent(StoryChoice choice) {
        if (choice.getIsRandomEvent() && choice.getRandomEventProbability() > 0) {
            return random.nextDouble() < choice.getRandomEventProbability();
        }
        return false;
    }
    
    /**
     * 保存游戏记录
     */
    public GameRecord saveGameRecord(User user, String endingType, String endingDescription, 
                                   String choicesMade, Integer score) {
        GameRecord record = new GameRecord();
        record.setUser(user);
        record.setEndingType(endingType);
        record.setEndingDescription(endingDescription);
        record.setChoicesMade(choicesMade);
        record.setGameScore(score);
        
        return gameRecordRepository.save(record);
    }
    
    /**
     * 获取用户游戏记录
     */
    public List<GameRecord> getUserGameRecords(User user) {
        return gameRecordRepository.findByUserOrderByCompletedAtDesc(user);
    }
    
    /**
     * 获取排行榜
     */
    public List<GameRecord> getLeaderboard() {
        return gameRecordRepository.findTopScores();
    }
    
    /**
     * 获取用户最高分
     */
    public Integer getUserMaxScore(User user) {
        return gameRecordRepository.findMaxScoreByUser(user);
    }
    
    /**
     * 获取用户游戏次数
     */
    public long getUserGameCount(User user) {
        return gameRecordRepository.countByUser(user);
    }
    
    /**
     * 获取所有结局类型
     */
    public List<StoryNode> getAllEndings() {
        return storyNodeRepository.findByIsEndingNodeTrue();
    }
}
