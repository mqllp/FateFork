package org.example.fatefork.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 游戏记录实体类
 */
@Entity
@Table(name = "game_records")
public class GameRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "game_score")
    private Integer gameScore;
    
    @Column(name = "ending_type", length = 50)
    private String endingType;
    
    @Column(name = "ending_description", length = 500)
    private String endingDescription;
    
    @Column(name = "choices_made", length = 1000)
    private String choicesMade; // JSON格式存储选择路径
    
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
    
    @Column(name = "play_time_minutes")
    private Integer playTimeMinutes;
    
    // 构造函数
    public GameRecord() {
        this.completedAt = LocalDateTime.now();
    }
    
    public GameRecord(User user, String endingType, String endingDescription, String choicesMade) {
        this();
        this.user = user;
        this.endingType = endingType;
        this.endingDescription = endingDescription;
        this.choicesMade = choicesMade;
    }
    
    // Getter和Setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Integer getGameScore() {
        return gameScore;
    }
    
    public void setGameScore(Integer gameScore) {
        this.gameScore = gameScore;
    }
    
    public String getEndingType() {
        return endingType;
    }
    
    public void setEndingType(String endingType) {
        this.endingType = endingType;
    }
    
    public String getEndingDescription() {
        return endingDescription;
    }
    
    public void setEndingDescription(String endingDescription) {
        this.endingDescription = endingDescription;
    }
    
    public String getChoicesMade() {
        return choicesMade;
    }
    
    public void setChoicesMade(String choicesMade) {
        this.choicesMade = choicesMade;
    }
    
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
    
    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
    
    public Integer getPlayTimeMinutes() {
        return playTimeMinutes;
    }
    
    public void setPlayTimeMinutes(Integer playTimeMinutes) {
        this.playTimeMinutes = playTimeMinutes;
    }
}
