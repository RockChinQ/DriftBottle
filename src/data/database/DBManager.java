package data.database;

import data.models.Bottle;
import data.models.Comment;
import data.models.DBConfig;

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
        this.statement.execute(
                "CREATE TABLE IF NOT EXISTS `bottles`(" +
                        "`id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                        "`title` VARCHAR(255) NOT NULL," +
                        "`message` TEXT NOT NULL," +
                        "`created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"
        );
        // 评论
        this.statement.execute(
                "CREATE TABLE IF NOT EXISTS `comments`(" +
                        "`id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                        "`bottle_id` INT UNSIGNED NOT NULL," +
                        "`comment` TEXT NOT NULL," +
                        "`created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"
        );
    }

    public void dropBottle(String title, String message) throws SQLException {
        this.statement.execute(
                "INSERT INTO `bottles`(`title`, `message`) VALUES ('"+title+"','"+message+"' )"
        );
    }

    public Bottle randomPick() throws SQLException {
        ResultSet resultSet = this.statement.executeQuery(
                "SELECT * FROM `bottles` ORDER BY RAND() LIMIT 1"
        );
        if (resultSet.next()){
            Bottle bottle = new Bottle();
            bottle.id = resultSet.getInt("id");
            bottle.title = resultSet.getString("title");
            bottle.message = resultSet.getString("message");
            bottle.created_at = resultSet.getTimestamp("created_at").getTime();
            return bottle;
        }else {
            return null;
        }
    }

    public Comment[] getCommentsOfBottle(Bottle bottle)throws SQLException{
        ResultSet resultSet = this.statement.executeQuery(
                "SELECT * FROM `comments` WHERE `bottle_id`="+bottle.id+" ORDER BY `created_at` ASC "
        );
        ArrayList<Comment> comments = new ArrayList<>();
        while (resultSet.next()){
            Comment comment = new Comment();
            comment.id = resultSet.getInt("id");
            comment.bottle_id = resultSet.getInt("bottle_id");
            comment.message = resultSet.getString("comment");
            comment.created_at = resultSet.getTimestamp("created_at").getTime();
            comments.add(comment);
        }
        return comments.toArray(new Comment[0]);
    }

    public void addCommentForBottle(Bottle bottle, String comment) throws SQLException {
        this.statement.execute(
                "INSERT INTO `comments`(`bottle_id`, `comment`) VALUES ("+bottle.id+",'"+comment+"')"
        );
    }
}
