package org.example.fatefork.config;

import org.example.fatefork.entity.*;
import org.example.fatefork.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据初始化类
 *
 * 改进点：
 * - 添加日志记录（替代 System.out.println）
 * - 添加 @Transactional 确保事务完整性
 * - 改进错误处理
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private StoryNodeRepository storyNodeRepository;

    @Autowired
    private StoryChoiceRepository storyChoiceRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        try {
            // 检查是否已有数据
            if (storyNodeRepository.count() > 0) {
                logger.info("数据库已有数据，跳过初始化");
                return;
            }

            logger.info("开始初始化游戏数据...");

            // 创建剧情节点
            StoryNode startNode = new StoryNode("start", "开学第一天",
                    "欢迎来到大学校园！今天是你的开学第一天，你站在宿舍楼下，看着熙熙攘攘的人群。\n\n" +
                            "你注意到有几个不同的方向：\n" +
                            "1. 去图书馆学习\n" +
                            "2. 去食堂吃饭\n" +
                            "3. 去社团招新现场\n" +
                            "4. 回宿舍休息");
            startNode.setIsStartNode(true);
            startNode.setBackgroundImage("/images/campus-entrance.jpg");
            storyNodeRepository.save(startNode);
            logger.debug("已创建起始节点: start");

            // 图书馆节点
            StoryNode libraryNode = new StoryNode("library", "图书馆的邂逅",
                    "你来到图书馆，发现这里安静而充满学术氛围。你看到：\n\n" +
                            "1. 一个空座位，旁边坐着一位认真学习的同学\n" +
                            "2. 一个小组讨论区，几个同学在讨论问题\n" +
                            "3. 一个角落的座位，看起来很安静");
            libraryNode.setBackgroundImage("/images/library.jpg");
            storyNodeRepository.save(libraryNode);
            logger.debug("已创建节点: library");

            // 食堂节点
            StoryNode canteenNode = new StoryNode("canteen", "食堂的选择",
                    "你来到食堂，看到各种美食。你注意到：\n\n" +
                            "1. 健康餐窗口，价格便宜但选择有限\n" +
                            "2. 特色菜窗口，价格稍贵但很诱人\n" +
                            "3. 快餐窗口，速度快但可能不够健康");
            canteenNode.setBackgroundImage("/images/canteen.jpg");
            storyNodeRepository.save(canteenNode);
            logger.debug("已创建节点: canteen");

            // 社团招新节点
            StoryNode clubNode = new StoryNode("club", "社团招新现场",
                    "你来到社团招新现场，看到各种有趣的社团：\n\n" +
                            "1. 学生会 - 锻炼领导能力\n" +
                            "2. 摄影社 - 培养艺术兴趣\n" +
                            "3. 编程社 - 学习技术技能\n" +
                            "4. 篮球社 - 强身健体");
            clubNode.setBackgroundImage("/images/club-fair.jpg");
            storyNodeRepository.save(clubNode);
            logger.debug("已创建节点: club");

            // 宿舍节点
            StoryNode dormNode = new StoryNode("dorm", "宿舍生活",
                    "你回到宿舍，室友们正在聊天。你发现：\n\n" +
                            "1. 室友们在讨论明天的课程\n" +
                            "2. 有人提议一起玩游戏\n" +
                            "3. 有人建议一起出去逛逛");
            dormNode.setBackgroundImage("/images/dormitory.jpg");
            storyNodeRepository.save(dormNode);
            logger.debug("已创建节点: dorm");

            // 结局节点
            StoryNode successEnding = new StoryNode("success", "成功毕业",
                    "经过四年的努力，你以优异的成绩毕业了！\n\n" +
                            "你不仅学到了专业知识，还培养了各种技能，结交了真挚的朋友。\n" +
                            "你的大学生活充实而精彩，为未来的人生道路奠定了坚实的基础。\n\n" +
                            "恭喜你！这是一个完美的结局！");
            successEnding.setIsEndingNode(true);
            successEnding.setEndingType("成功毕业");
            successEnding.setBackgroundImage("/images/graduation.jpg");
            storyNodeRepository.save(successEnding);
            logger.debug("已创建结局节点: success");

            StoryNode entrepreneurEnding = new StoryNode("entrepreneur", "创业成功",
                    "你在大学期间就开始创业，毕业后公司发展迅速！\n\n" +
                            "你的创新思维和执行力让你在商界崭露头角。\n" +
                            "虽然过程充满挑战，但你的坚持和努力最终获得了回报。\n\n" +
                            "恭喜你！这是一个令人羡慕的结局！");
            entrepreneurEnding.setIsEndingNode(true);
            entrepreneurEnding.setEndingType("创业成功");
            entrepreneurEnding.setBackgroundImage("/images/entrepreneur.jpg");
            storyNodeRepository.save(entrepreneurEnding);
            logger.debug("已创建结局节点: entrepreneur");

            StoryNode dropoutEnding = new StoryNode("dropout", "被退学",
                    "由于种种原因，你最终被学校退学了。\n\n" +
                            "这可能是因为成绩不达标，或者违反了学校规定。\n" +
                            "虽然这是一个不太理想的结局，但人生还有无数可能。\n\n" +
                            "不要灰心，重新开始吧！");
            dropoutEnding.setIsEndingNode(true);
            dropoutEnding.setEndingType("被退学");
            dropoutEnding.setBackgroundImage("/images/dropout.jpg");
            storyNodeRepository.save(dropoutEnding);
            logger.debug("已创建结局节点: dropout");

            // 创建选择
            createChoices(startNode, libraryNode, canteenNode, clubNode, dormNode,
                    successEnding, entrepreneurEnding, dropoutEnding);

            logger.info("游戏数据初始化完成！");
        } catch (Exception e) {
            logger.error("数据初始化失败", e);
            throw e;
        }
    }

    @Transactional
    protected void createChoices(StoryNode startNode, StoryNode libraryNode, StoryNode canteenNode,
                                 StoryNode clubNode, StoryNode dormNode, StoryNode successEnding,
                                 StoryNode entrepreneurEnding, StoryNode dropoutEnding) {

        logger.debug("开始创建选择关系...");

        // 从起始节点的选择
        storyChoiceRepository.save(new StoryChoice(startNode, libraryNode, "去图书馆学习"));
        storyChoiceRepository.save(new StoryChoice(startNode, canteenNode, "去食堂吃饭"));
        storyChoiceRepository.save(new StoryChoice(startNode, clubNode, "去社团招新现场"));
        storyChoiceRepository.save(new StoryChoice(startNode, dormNode, "回宿舍休息"));

        // 图书馆节点的选择
        storyChoiceRepository.save(new StoryChoice(libraryNode, successEnding, "坐在认真学习的学生旁边"));
        storyChoiceRepository.save(new StoryChoice(libraryNode, successEnding, "加入小组讨论"));
        storyChoiceRepository.save(new StoryChoice(libraryNode, dropoutEnding, "选择安静的角落"));

        // 食堂节点的选择
        storyChoiceRepository.save(new StoryChoice(canteenNode, successEnding, "选择健康餐"));
        storyChoiceRepository.save(new StoryChoice(canteenNode, successEnding, "选择特色菜"));
        storyChoiceRepository.save(new StoryChoice(canteenNode, dropoutEnding, "选择快餐"));

        // 社团招新节点的选择
        storyChoiceRepository.save(new StoryChoice(clubNode, successEnding, "加入学生会"));
        storyChoiceRepository.save(new StoryChoice(clubNode, successEnding, "加入摄影社"));
        storyChoiceRepository.save(new StoryChoice(clubNode, entrepreneurEnding, "加入编程社"));
        storyChoiceRepository.save(new StoryChoice(clubNode, successEnding, "加入篮球社"));

        // 宿舍节点的选择
        storyChoiceRepository.save(new StoryChoice(dormNode, successEnding, "参与课程讨论"));
        storyChoiceRepository.save(new StoryChoice(dormNode, dropoutEnding, "一起玩游戏"));
        storyChoiceRepository.save(new StoryChoice(dormNode, successEnding, "一起出去逛逛"));

        logger.debug("选择关系创建完成！");
    }
}