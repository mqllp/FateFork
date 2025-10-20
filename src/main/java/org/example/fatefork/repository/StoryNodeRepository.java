package org.example.fatefork.repository;

import org.example.fatefork.entity.StoryNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * 剧情节点数据访问接口
 */
@Repository
public interface StoryNodeRepository extends JpaRepository<StoryNode, Long> {
    
    /**
     * 根据节点ID查找剧情节点
     */
    Optional<StoryNode> findByNodeId(String nodeId);
    
    /**
     * 查找起始节点
     */
    Optional<StoryNode> findByIsStartNodeTrue();
    
    /**
     * 查找所有结局节点
     */
    java.util.List<StoryNode> findByIsEndingNodeTrue();
    
    /**
     * 根据结局类型查找节点
     */
    java.util.List<StoryNode> findByEndingType(String endingType);
}

