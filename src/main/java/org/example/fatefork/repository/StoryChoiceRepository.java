package org.example.fatefork.repository;

import org.example.fatefork.entity.StoryChoice;
import org.example.fatefork.entity.StoryNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 剧情选择数据访问接口
 */
@Repository
public interface StoryChoiceRepository extends JpaRepository<StoryChoice, Long> {
    
    /**
     * 根据起始节点查找所有选择
     */
    List<StoryChoice> findByFromNode(StoryNode fromNode);
    
    /**
     * 查找随机事件选择
     */
    List<StoryChoice> findByIsRandomEventTrue();
}

