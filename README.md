# 命运分岔口 - 校园文字冒险游戏

## 项目简介

《命运分岔口》是一款基于SpringBoot + Thymeleaf开发的网页文字冒险游戏。玩家以学生身份体验校园生活，通过不同的选择影响剧情发展，最终达成不同的结局。

## 技术栈

- **后端**: Spring Boot 3.5.6, Spring Security, Spring Data JPA
- **前端**: Thymeleaf模板引擎, Bootstrap 5, Font Awesome
- **数据库**: H2内存数据库（开发环境）
- **构建工具**: Maven
- **Java版本**: 17

## 主要功能

### 1. 用户系统
- 用户注册/登录
- 个人信息管理
- 密码加密存储

### 2. 游戏系统
- 多分支剧情设计
- 选择影响分数和结局
- 随机事件系统
- 多结局设计（成功毕业、创业成功、被退学等）

### 3. 数据统计
- 游戏记录保存
- 排行榜功能
- 个人历史记录
- 结局分享功能

## 项目结构

```
FateFork/
├── src/main/java/org/example/fatefork/
│   ├── entity/          # 实体类
│   │   ├── User.java
│   │   ├── GameRecord.java
│   │   ├── StoryNode.java
│   │   └── StoryChoice.java
│   ├── repository/       # 数据访问层
│   │   ├── UserRepository.java
│   │   ├── GameRecordRepository.java
│   │   ├── StoryNodeRepository.java
│   │   └── StoryChoiceRepository.java
│   ├── service/          # 业务逻辑层
│   │   ├── UserService.java
│   │   └── GameService.java
│   ├── controller/       # 控制器层
│   │   ├── HomeController.java
│   │   ├── GameController.java
│   │   └── AuthController.java
│   ├── config/          # 配置类
│   │   ├── SecurityConfig.java
│   │   └── DataInitializer.java
│   └── FateForkApplication.java
├── src/main/resources/
│   ├── templates/        # Thymeleaf模板
│   │   ├── index.html
│   │   ├── login.html
│   │   ├── register.html
│   │   ├── game.html
│   │   ├── ending.html
│   │   ├── leaderboard.html
│   │   ├── history.html
│   │   └── error.html
│   ├── static/          # 静态资源
│   │   ├── css/
│   │   ├── js/
│   │   └── images/
│   └── application.properties
└── pom.xml
```

## 快速开始

### 1. 环境要求
- Java 17+
- Maven 3.6+
- MySQL 8.0+ (推荐)

### 2. 数据库配置

#### 使用MySQL数据库（推荐）
1. 确保MySQL服务已启动
2. 创建数据库：
   ```sql
   -- 在MySQL中执行
   CREATE DATABASE fatefork CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```
   或者直接执行项目中的 `setup-mysql.sql` 脚本

3. 运行项目：
   ```bash
   # Windows用户
   start-mysql.bat
   
   # Linux/Mac用户
   chmod +x start-mysql.sh
   ./start-mysql.sh
   ```

#### 使用H2内存数据库（开发测试）
```bash
# 修改 application.properties 中的数据库配置
# 然后运行
mvn spring-boot:run
```

### 3. 运行项目
```bash
# 进入项目目录
cd FateFork

# 编译项目
mvn clean compile

# 运行项目
mvn spring-boot:run
```

### 4. 访问应用
- 应用地址: http://localhost:8080
- MySQL数据库: fatefork (用户名: root, 密码: 123456)
- H2数据库控制台: http://localhost:8080/h2-console (仅H2模式)

## 游戏玩法

### 1. 注册登录
- 访问首页，点击"注册"创建账号
- 填写用户名、密码和昵称
- 登录后可以开始游戏

### 2. 开始游戏
- 点击"开始游戏"进入游戏界面
- 阅读剧情内容，做出选择
- 每个选择都会影响分数和后续剧情

### 3. 游戏特色
- **多分支剧情**: 不同选择导向不同剧情
- **随机事件**: 增加游戏趣味性
- **多结局系统**: 根据选择获得不同结局
- **分数系统**: 选择影响最终分数
- **分享功能**: 可以分享游戏结果

### 4. 结局类型
- **成功毕业**: 以优异成绩毕业
- **创业成功**: 在校期间创业成功
- **被退学**: 因各种原因被退学

## 评分点覆盖

### 创新点
- ✅ 多分支多结局设计
- ✅ 随机事件系统
- ✅ 结局分享功能
- ✅ 用户自定义昵称

### 前端技术
- ✅ Thymeleaf多页面切换
- ✅ Bootstrap响应式设计
- ✅ 自定义CSS美化
- ✅ 图片资源支持
- ✅ JavaScript交互功能

### 后端技术
- ✅ SpringBoot框架
- ✅ 用户登录注册系统
- ✅ 数据库持久化
- ✅ DAO-Service-Controller三层架构
- ✅ Spring Security安全框架

### 部署
- ✅ Maven打包为独立JAR
- ✅ 内嵌Tomcat服务器
- ✅ 配置文件管理

## 扩展功能

### 已实现
- 用户系统完整流程
- 游戏剧情系统
- 数据统计和排行榜
- 响应式前端界面

### 可扩展
- 更多剧情分支
- 更多结局类型
- 用户投稿剧情
- 社交功能
- 成就系统

## 开发说明

### 数据库设计
- **users**: 用户表
- **game_records**: 游戏记录表
- **story_nodes**: 剧情节点表
- **story_choices**: 剧情选择表

### 安全配置
- 密码BCrypt加密
- CSRF保护
- 会话管理
- 权限控制

### 前端设计
- 响应式布局
- 校园主题色彩
- 动画效果
- 用户体验优化

## 部署说明

### 开发环境
```bash
mvn spring-boot:run
```

### 生产环境
```bash
# 打包
mvn clean package

# 运行JAR包
java -jar target/FateFork-0.0.1-SNAPSHOT.jar
```

## 注意事项

1. **数据库选择**:
   - 开发测试: 可使用H2内存数据库（重启后数据丢失）
   - 生产环境: 推荐使用MySQL（数据持久化）
2. 图片资源需要自行添加
3. 可根据需要调整剧情内容
4. MySQL配置请参考 `MySQL-SETUP.md` 文件

## MySQL配置快速指南

### 1. 创建数据库
```sql
CREATE DATABASE fatefork CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. 测试连接
```bash
# Windows
test-mysql-connection.bat

# Linux/Mac
chmod +x test-mysql-connection.sh
./test-mysql-connection.sh
```

### 3. 启动应用
```bash
# Windows
start-mysql.bat

# Linux/Mac
./start-mysql.sh
```

## 联系方式

如有问题或建议，请联系开发团队。

---

**祝你在校园生活中做出明智的选择，创造属于自己的精彩人生！** 🎓✨
