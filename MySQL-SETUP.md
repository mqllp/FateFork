# MySQL数据库配置指南

## 1. 环境准备

### 确保MySQL已安装并运行
- MySQL 8.0+ 版本
- 服务已启动
- 用户名: root
- 密码: 123456

## 2. 创建数据库

### 方法一：使用SQL脚本（推荐）
```bash
# 在MySQL命令行中执行
mysql -u root -p123456 < setup-mysql.sql
```

### 方法二：手动创建
```sql
-- 在MySQL中执行
CREATE DATABASE fatefork CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

## 3. 验证数据库创建
```sql
-- 检查数据库是否创建成功
SHOW DATABASES;
USE fatefork;
SHOW TABLES;
```

## 4. 运行应用

### Windows用户
```bash
# 双击运行
start-mysql.bat

# 或命令行运行
cd FateFork
start-mysql.bat
```

### Linux/Mac用户
```bash
# 给脚本执行权限
chmod +x start-mysql.sh

# 运行脚本
./start-mysql.sh
```

### 手动运行
```bash
cd FateFork
mvn spring-boot:run
```

## 5. 访问应用

- **应用地址**: http://localhost:8080
- **数据库**: MySQL (fatefork)
- **用户名**: root
- **密码**: 123456

## 6. 数据库表结构

应用启动后会自动创建以下表：
- `users` - 用户表
- `game_records` - 游戏记录表
- `story_nodes` - 剧情节点表
- `story_choices` - 剧情选择表

## 7. 故障排除

### 常见问题

1. **连接失败**
   - 检查MySQL服务是否启动
   - 确认用户名密码正确
   - 检查端口3306是否开放

2. **数据库不存在**
   - 执行 `setup-mysql.sql` 脚本
   - 或手动创建数据库

3. **字符编码问题**
   - 确保数据库使用utf8mb4编码
   - 检查连接URL中的字符编码设置

4. **权限问题**
   - 确保root用户有创建数据库的权限
   - 检查用户权限设置

### 测试连接
```sql
-- 测试数据库连接
SELECT 'MySQL连接成功！' AS message;
```

## 8. 数据备份

### 备份数据库
```bash
mysqldump -u root -p123456 fatefork > fatefork_backup.sql
```

### 恢复数据库
```bash
mysql -u root -p123456 fatefork < fatefork_backup.sql
```

## 9. 性能优化

### MySQL配置建议
```ini
# 在my.cnf中添加
[mysqld]
max_connections = 200
innodb_buffer_pool_size = 256M
innodb_log_file_size = 64M
```

## 10. 监控和维护

### 查看数据库状态
```sql
-- 查看数据库大小
SELECT 
    table_schema AS 'Database',
    ROUND(SUM(data_length + index_length) / 1024 / 1024, 2) AS 'Size (MB)'
FROM information_schema.tables 
WHERE table_schema = 'fatefork'
GROUP BY table_schema;
```

### 查看表记录数
```sql
-- 查看各表记录数
SELECT 
    'users' AS table_name, COUNT(*) AS record_count FROM users
UNION ALL
SELECT 
    'game_records' AS table_name, COUNT(*) AS record_count FROM game_records
UNION ALL
SELECT 
    'story_nodes' AS table_name, COUNT(*) AS record_count FROM story_nodes
UNION ALL
SELECT 
    'story_choices' AS table_name, COUNT(*) AS record_count FROM story_choices;
```

---

**注意**: 请确保MySQL服务正常运行，并且有足够的权限创建数据库和表。
