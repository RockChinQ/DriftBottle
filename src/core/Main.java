package core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.database.DBManager;
import data.models.DBConfig;
import data.models.User;
import gui.DashboardMain;
import utils.FileIO;

import java.io.File;

public class Main {
    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static DBManager dbManager;

    public static User user = new User();
    public static DashboardMain dashboardMain;
    public static void main(String[] args) throws Exception{
        // 检查是否有数据库配置文件db.json，若没有则将DBConfig的默认值写入
        // 若有则读取db.json并使用其中的配置
        if (new File("db.json").exists()){
            DBConfig dbConfig = gson.fromJson(FileIO.read("db.json"), DBConfig.class);
            dbManager = new DBManager(dbConfig);
        }else {
            DBConfig dbConfig = new DBConfig();
            FileIO.write("db.json", gson.toJson(dbConfig));
            throw new Exception("请先在db.json配置数据库");
        }

        // 检查是否有缓存的user.json，若没有则将User的默认值写入
        // 若有则读取user.json并使用其中的配置
        if (new File("user.json").exists()){
            user = gson.fromJson(FileIO.read("user.json"), User.class);
        }else {
            FileIO.write("user.json", gson.toJson(user));
        }
        user=dbManager.getUser(user.username);

        dashboardMain=new gui.DashboardMain(user);
        dashboardMain.setVisible(true);
    }
}