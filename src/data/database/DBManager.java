package data.database;

import core.Main;
import data.models.Bottle;
import data.models.Comment;
import data.models.DBConfig;
import data.models.User;

import java.sql.*;
import java.util.ArrayList;

public class DBManager {
    // 数据库连接
    Connection connection;
    Statement statement;
    public DBManager(DBConfig dbConfig) throws ClassNotFoundException, SQLException {
        // 连接数据库
        Class.forName("com.mysql.jdbc.Driver");
        this.connection= DriverManager.getConnection(
                "jdbc:mysql://"+dbConfig.address+"/"+dbConfig.database+"?useUnicode=true&characterEncoding=utf-8&useSSL=false",
                dbConfig.username,
                dbConfig.password
        );
        this.statement = connection.createStatement();
        createTables();
    }

    public void createTables() throws SQLException {
        // 用户
        this.statement.execute("" +
                "CREATE TABLE IF NOT EXISTS `users`(" +
                "`id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                "`username` VARCHAR(255) NOT NULL)"
        );
        // 瓶子
        this.statement.execute(
                "CREATE TABLE IF NOT EXISTS `bottles`(" +
                        "`id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                        "`user_id` INT UNSIGNED NOT NULL," +
                        "`title` VARCHAR(255) NOT NULL," +
                        "`message` TEXT NOT NULL," +
                        "`created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"
        );
        // 评论
        this.statement.execute(
                "CREATE TABLE IF NOT EXISTS `comments`(" +
                        "`id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                        "`user_id` INT UNSIGNED NOT NULL," +
                        "`bottle_id` INT UNSIGNED NOT NULL," +
                        "`comment` TEXT NOT NULL," +
                        "`created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"
        );
    }

    public User getUser(String username) throws SQLException {
        User user = new User();
        user.id=-1;
        do {
            ResultSet resultSet = this.statement.executeQuery(
                    "SELECT * FROM `users` WHERE `username`='"+username+"' LIMIT 1"
            );
            if (resultSet.next()){
                user.id = resultSet.getInt("id");
                user.username = resultSet.getString("username");
            }else {
                this.statement.execute(
                        "INSERT INTO `users`(`username`) VALUES ('"+username+"')"
                );
            }
        }while (user.id==-1);
        return user;
    }

    public void dropBottle(String title, String message) throws SQLException {
        this.statement.execute(
                "INSERT INTO `bottles`(`user_id`, `title`, `message`) VALUES ("+ Main.user.id +",'"+title+"','"+message+"' )"
        );
    }

    public Bottle randomPick() throws SQLException {
        // 随机一个bottle，同时获取其作者用户的信息
        ResultSet resultSet = this.statement.executeQuery(
                "SELECT * FROM `bottles` AS `b` JOIN `users` AS `u` ON `b`.`user_id`=`u`.`id` ORDER BY RAND() LIMIT 1"
        );
        if (resultSet.next()){
            Bottle bottle = new Bottle();
            bottle.id = resultSet.getInt("id");
            bottle.user_id = resultSet.getInt("user_id");
            bottle.title = resultSet.getString("title");
            bottle.message = resultSet.getString("message");
            bottle.created_at = resultSet.getTimestamp("created_at").getTime();
            bottle.user = new User();
            bottle.user.id = resultSet.getInt("user_id");
            bottle.user.username = resultSet.getString("username");
            return bottle;
        }else {
            return null;
        }
    }

    public Comment[] getCommentsOfBottle(Bottle bottle)throws SQLException{
        ResultSet resultSet = this.statement.executeQuery(
                "SELECT * FROM `comments` AS `c` JOIN `users` AS `u` ON `c`.`user_id`=`u`.`id` WHERE `bottle_id`="+bottle.id+" ORDER BY `created_at` DESC"
        );
        ArrayList<Comment> comments = new ArrayList<>();
        while (resultSet.next()){
            Comment comment = new Comment();
            comment.id = resultSet.getInt("id");
            comment.user_id = resultSet.getInt("user_id");
            comment.bottle_id = resultSet.getInt("bottle_id");
            comment.message = resultSet.getString("comment");
            comment.created_at = resultSet.getTimestamp("created_at").getTime();
            comment.user = new User();
            comment.user.id = resultSet.getInt("user_id");
            comment.user.username = resultSet.getString("username");
            comments.add(comment);
        }
        return comments.toArray(new Comment[0]);
    }

    public void addCommentForBottle(Bottle bottle, String comment) throws SQLException {
        this.statement.execute(
                "INSERT INTO `comments`(`user_id`, `bottle_id`, `comment`) VALUES ("+Main.user.id+","+bottle.id+",'"+comment+"')"
        );
    }
}
