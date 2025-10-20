package org.example.fatefork.entity;

import jakarta.persistence.*;
import java.util.List;

/**
 * 剧情节点实体类
 */
@Entity
@Table(name = "story_nodes")
public class StoryNode {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "node_id", unique = true, nullable = false)
    private String nodeId;
    
    @Column(name = "title", length = 100, nullable = false)
    private String title;
    
    @Column(name = "content", length = 2000, nullable = false)
    private String content;
    
    @Column(name = "background_image", length = 200)
    private String backgroundImage;
    
    @Column(name = "is_start_node")
    private Boolean isStartNode = false;
    
    @Column(name = "is_ending_node")
    private Boolean isEndingNode = false;
    
    @Column(name = "ending_type", length = 50)
    private String endingType;
    
    @OneToMany(mappedBy = "fromNode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StoryChoice> choices;
    
    // 构造函数
    public StoryNode() {}
    
    public StoryNode(String nodeId, String title, String content) {
        this.nodeId = nodeId;
        this.title = title;
        this.content = content;
    }
    
    // Getter和Setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNodeId() {
        return nodeId;
    }
    
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getBackgroundImage() {
        return backgroundImage;
    }
    
    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
    
    public Boolean getIsStartNode() {
        return isStartNode;
    }
    
    public void setIsStartNode(Boolean isStartNode) {
        this.isStartNode = isStartNode;
    }
    
    public Boolean getIsEndingNode() {
        return isEndingNode;
    }
    
    public void setIsEndingNode(Boolean isEndingNode) {
        this.isEndingNode = isEndingNode;
    }
    
    public String getEndingType() {
        return endingType;
    }
    
    public void setEndingType(String endingType) {
        this.endingType = endingType;
    }
    
    public List<StoryChoice> getChoices() {
        return choices;
    }
    
    public void setChoices(List<StoryChoice> choices) {
        this.choices = choices;
    }
}
