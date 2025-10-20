-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS fatefork CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE fatefork;

-- 创建用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建游戏记录表
CREATE TABLE IF NOT EXISTS game_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    game_score INT DEFAULT 0,
    ending_type VARCHAR(50),
    ending_description TEXT,
    choices_made TEXT,
    completed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    play_time_minutes INT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 创建剧情节点表
CREATE TABLE IF NOT EXISTS story_nodes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    node_id VARCHAR(50) NOT NULL UNIQUE,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    background_image VARCHAR(200),
    is_start_node BOOLEAN DEFAULT FALSE,
    is_ending_node BOOLEAN DEFAULT FALSE,
    ending_type VARCHAR(50)
);

-- 创建剧情选择表
CREATE TABLE IF NOT EXISTS story_choices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    from_node_id BIGINT NOT NULL,
    to_node_id BIGINT,
    choice_text VARCHAR(200) NOT NULL,
    choice_description VARCHAR(500),
    score_impact INT DEFAULT 0,
    is_random_event BOOLEAN DEFAULT FALSE,
    random_event_probability DOUBLE DEFAULT 0.0,
    FOREIGN KEY (from_node_id) REFERENCES story_nodes(id) ON DELETE CASCADE,
    FOREIGN KEY (to_node_id) REFERENCES story_nodes(id) ON DELETE SET NULL
);

-- 插入初始数据
INSERT INTO story_nodes (node_id, title, content, background_image, is_start_node, is_ending_node, ending_type) VALUES
('start', '开学第一天', '欢迎来到大学校园！今天是你的开学第一天，你站在宿舍楼下，看着熙熙攘攘的人群。\n\n你注意到有几个不同的方向：\n1. 去图书馆学习\n2. 去食堂吃饭\n3. 去社团招新现场\n4. 回宿舍休息', '/images/campus-entrance.jpg', TRUE, FALSE, NULL),
('library', '图书馆的邂逅', '你来到图书馆，发现这里安静而充满学术氛围。你看到：\n\n1. 一个空座位，旁边坐着一位认真学习的同学\n2. 一个小组讨论区，几个同学在讨论问题\n3. 一个角落的座位，看起来很安静', '/images/library.jpg', FALSE, FALSE, NULL),
('canteen', '食堂的选择', '你来到食堂，看到各种美食。你注意到：\n\n1. 健康餐窗口，价格便宜但选择有限\n2. 特色菜窗口，价格稍贵但很诱人\n3. 快餐窗口，速度快但可能不够健康', '/images/canteen.jpg', FALSE, FALSE, NULL),
('club', '社团招新现场', '你来到社团招新现场，看到各种有趣的社团：\n\n1. 学生会 - 锻炼领导能力\n2. 摄影社 - 培养艺术兴趣\n3. 编程社 - 学习技术技能\n4. 篮球社 - 强身健体', '/images/club-fair.jpg', FALSE, FALSE, NULL),
('dorm', '宿舍生活', '你回到宿舍，室友们正在聊天。你发现：\n\n1. 室友们在讨论明天的课程\n2. 有人提议一起玩游戏\n3. 有人建议一起出去逛逛', '/images/dormitory.jpg', FALSE, FALSE, NULL),
('success', '成功毕业', '经过四年的努力，你以优异的成绩毕业了！\n\n你不仅学到了专业知识，还培养了各种技能，结交了真挚的朋友。\n你的大学生活充实而精彩，为未来的人生道路奠定了坚实的基础。\n\n恭喜你！这是一个完美的结局！', '/images/graduation.jpg', FALSE, TRUE, '成功毕业'),
('entrepreneur', '创业成功', '你在大学期间就开始创业，毕业后公司发展迅速！\n\n你的创新思维和执行力让你在商界崭露头角。\n虽然过程充满挑战，但你的坚持和努力最终获得了回报。\n\n恭喜你！这是一个令人羡慕的结局！', '/images/entrepreneur.jpg', FALSE, TRUE, '创业成功'),
('dropout', '被退学', '由于种种原因，你最终被学校退学了。\n\n这可能是因为成绩不达标，或者违反了学校规定。\n虽然这是一个不太理想的结局，但人生还有无数可能。\n\n不要灰心，重新开始吧！', '/images/dropout.jpg', FALSE, TRUE, '被退学');

-- 插入选择数据
INSERT INTO story_choices (from_node_id, to_node_id, choice_text, choice_description, score_impact) VALUES
(1, 2, '去图书馆学习', '选择去图书馆，开始你的学术之旅', 5),
(1, 3, '去食堂吃饭', '选择去食堂，体验校园美食', 3),
(1, 4, '去社团招新现场', '选择去社团招新，寻找志同道合的朋友', 4),
(1, 5, '回宿舍休息', '选择回宿舍，先熟悉环境', 2),
(2, 6, '坐在认真学习的学生旁边', '向优秀同学学习', 10),
(2, 6, '加入小组讨论', '积极参与学术讨论', 8),
(2, 8, '选择安静的角落', '独自学习，但可能错过交流机会', -5),
(3, 6, '选择健康餐', '注重身体健康', 8),
(3, 6, '选择特色菜', '享受美食，但可能花费较多', 5),
(3, 8, '选择快餐', '追求速度，但可能不够健康', -3),
(4, 6, '加入学生会', '锻炼领导能力', 10),
(4, 6, '加入摄影社', '培养艺术兴趣', 7),
(4, 7, '加入编程社', '学习技术技能，为创业做准备', 12),
(4, 6, '加入篮球社', '强身健体，结交朋友', 6),
(5, 6, '参与课程讨论', '认真学习，为未来做准备', 8),
(5, 8, '一起玩游戏', '娱乐过度，影响学习', -10),
(5, 6, '一起出去逛逛', '放松心情，但也要注意学习', 3);
