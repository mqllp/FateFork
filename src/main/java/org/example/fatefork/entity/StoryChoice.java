package org.example.fatefork.entity;

import jakarta.persistence.*;

/**
 * 剧情选择实体类
 */
@Entity
@Table(name = "story_choices")
public class StoryChoice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_node_id", nullable = false)
    private StoryNode fromNode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_node_id")
    private StoryNode toNode;
    
    @Column(name = "choice_text", length = 200, nullable = false)
    private String choiceText;
    
    @Column(name = "choice_description", length = 500)
    private String choiceDescription;
    
    @Column(name = "score_impact")
    private Integer scoreImpact = 0; // 选择对分数的影响
    
    @Column(name = "is_random_event")
    private Boolean isRandomEvent = false;
    
    @Column(name = "random_event_probability")
    private Double randomEventProbability = 0.0; // 随机事件触发概率
    
    // 构造函数
    public StoryChoice() {}
    
    public StoryChoice(StoryNode fromNode, StoryNode toNode, String choiceText) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.choiceText = choiceText;
    }
    
    // Getter和Setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public StoryNode getFromNode() {
        return fromNode;
    }
    
    public void setFromNode(StoryNode fromNode) {
        this.fromNode = fromNode;
    }
    
    public StoryNode getToNode() {
        return toNode;
    }
    
    public void setToNode(StoryNode toNode) {
        this.toNode = toNode;
    }
    
    public String getChoiceText() {
        return choiceText;
    }
    
    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }
    
    public String getChoiceDescription() {
        return choiceDescription;
    }
    
    public void setChoiceDescription(String choiceDescription) {
        this.choiceDescription = choiceDescription;
    }
    
    public Integer getScoreImpact() {
        return scoreImpact;
    }
    
    public void setScoreImpact(Integer scoreImpact) {
        this.scoreImpact = scoreImpact;
    }
    
    public Boolean getIsRandomEvent() {
        return isRandomEvent;
    }
    
    public void setIsRandomEvent(Boolean isRandomEvent) {
        this.isRandomEvent = isRandomEvent;
    }
    
    public Double getRandomEventProbability() {
        return randomEventProbability;
    }
    
    public void setRandomEventProbability(Double randomEventProbability) {
        this.randomEventProbability = randomEventProbability;
    }
}

