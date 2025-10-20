package org.example.fatefork.service;

import org.example.fatefork.entity.*;
import org.example.fatefork.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * 游戏服务类
 *
 * 改进点：
 * - 添加日志记录
 * - 添加 @Transactional 确保事务完整性
 * - 改进随机事件处理
 * - 添加游戏时长参数
 */
@Service
public class GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

    @Autowired
    private StoryNodeRepository storyNodeRepository;

    @Autowired
    private StoryChoiceRepository storyChoiceRepository;

    @Autowired
    private GameRecordRepository gameRecordRepository;

    private final Random random = new Random();

    /**
     * 获取起始节点
     *
     * @return 起始节点
     * @throws RuntimeException 如果未找到起始节点
     */
    public StoryNode getStartNode() {
        return storyNodeRepository.findByIsStartNodeTrue()
                .orElseThrow(() -> {
                    logger.error("未找到起始节点");
                    return new RuntimeException("未找到起始节点");
                });
    }

    /**
     * 根据节点ID获取剧情节点
     *
     * @param nodeId 节点ID
     * @return 剧情节点
     * @throws RuntimeException 如果未找到节点
     */
    public StoryNode getStoryNode(String nodeId) {
        return storyNodeRepository.findByNodeId(nodeId)
                .orElseThrow(() -> {
                    logger.warn("未找到剧情节点: {}", nodeId);
                    return new RuntimeException("未找到剧情节点: " + nodeId);
                });
    }

    /**
     * 获取节点的所有选择
     *
     * @param node 节点
     * @return 选择列表
     */
    public List<StoryChoice> getChoicesForNode(StoryNode node) {
        List<StoryChoice> choices = storyChoiceRepository.findByFromNode(node);
        logger.debug("节点 {} 有 {} 个选择", node.getNodeId(), choices.size());
        return choices;
    }

    /**
     * 处理随机事件
     *
     * 根据选择的随机事件概率判断是否触发
     *
     * @param choice 选择
     * @return 是否触发随机事件
     */
    public boolean triggerRandomEvent(StoryChoice choice) {
        if (choice.getIsRandomEvent() && choice.getRandomEventProbability() > 0) {
            double randomValue = random.nextDouble();
            boolean triggered = randomValue < choice.getRandomEventProbability();

            logger.debug("随机事件检查: 概率={}, 随机值={}, 触发={}",
                    choice.getRandomEventProbability(), randomValue, triggered);

            return triggered;
        }
        return false;
    }

    /**
     * 保存游戏记录
     *
     * @param user 玩家用户
     * @param endingType 结局类型
     * @param endingDescription 结局描述
     * @param choicesMade 做出的选择
     * @param score 游戏分数
     * @return 保存的游戏记录
     */
    @Transactional
    public GameRecord saveGameRecord(User user, String endingType, String endingDescription,
                                     String choicesMade, Integer score) {
        return saveGameRecord(user, endingType, endingDescription, choicesMade, score, null);
    }

    /**
     * 保存游戏记录（包含时长）
     *
     * @param user 玩家用户
     * @param endingType 结局类型
     * @param endingDescription 结局描述
     * @param choicesMade 做出的选择
     * @param score 游戏分数
     * @param playTimeMinutes 游戏时长（分钟）
     * @return 保存的游戏记录
     */
    @Transactional
    public GameRecord saveGameRecord(User user, String endingType, String endingDescription,
                                     String choicesMade, Integer score, Integer playTimeMinutes) {
        try {
            GameRecord record = new GameRecord();
            record.setUser(user);
            record.setEndingType(endingType);
            record.setEndingDescription(endingDescription);
            record.setChoicesMade(choicesMade);
            record.setGameScore(score);
            record.setPlayTimeMinutes(playTimeMinutes);

            GameRecord savedRecord = gameRecordRepository.save(record);
            logger.info("游戏记录已保存: 用户={}, 结局={}, 分数={}, 耗时={}分钟",
                    user.getUsername(), endingType, score, playTimeMinutes);

            return savedRecord;
        } catch (Exception e) {
            logger.error("保存游戏记录失败", e);
            throw e;
        }
    }

    /**
     * 获取用户游戏记录
     *
     * @param user 用户
     * @return 用户的游戏记录列表
     */
    public List<GameRecord> getUserGameRecords(User user) {
        return gameRecordRepository.findByUserOrderByCompletedAtDesc(user);
    }

    /**
     * 获取排行榜（前100名）
     *
     * @return 排行榜记录列表
     */
    public List<GameRecord> getLeaderboard() {
        return gameRecordRepository.findTopScores();
    }

    /**
     * 获取用户最高分
     *
     * @param user 用户
     * @return 最高分数，如果没有记录则返回null
     */
    public Integer getUserMaxScore(User user) {
        return gameRecordRepository.findMaxScoreByUser(user);
    }

    /**
     * 获取用户游戏次数
     *
     * @param user 用户
     * @return 游戏次数
     */
    public long getUserGameCount(User user) {
        return gameRecordRepository.countByUser(user);
    }

    /**
     * 获取所有结局类型
     *
     * @return 结局节点列表
     */
    public List<StoryNode> getAllEndings() {
        return storyNodeRepository.findByIsEndingNodeTrue();
    }
}