package org.example.fatefork.repository;

import org.example.fatefork.entity.GameRecord;
import org.example.fatefork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 游戏记录数据访问接口
 */
@Repository
public interface GameRecordRepository extends JpaRepository<GameRecord, Long> {
    
    /**
     * 根据用户查找游戏记录
     */
    List<GameRecord> findByUserOrderByCompletedAtDesc(User user);
    
    /**
     * 查找排行榜（按分数排序）
     */
    @Query("SELECT gr FROM GameRecord gr ORDER BY gr.gameScore DESC, gr.completedAt DESC")
    List<GameRecord> findTopScores();
    
    /**
     * 根据结局类型查找记录
     */
    List<GameRecord> findByEndingTypeOrderByCompletedAtDesc(String endingType);
    
    /**
     * 统计用户游戏次数
     */
    long countByUser(User user);
    
    /**
     * 查找用户最高分
     */
    @Query("SELECT MAX(gr.gameScore) FROM GameRecord gr WHERE gr.user = :user")
    Integer findMaxScoreByUser(@Param("user") User user);
}

