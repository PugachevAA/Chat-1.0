package server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseAuthService implements AuthService{
    Map<String, User> users;
    @Override
    public void start() {
        //users = new HashMap<>();

    }

    @Override
    public String getNickByLoginPass(String login, String password) {
        DB.getConnection();
        ResultSet sqlUsers = DB.read("select * from `users` where `login` = '" + login + "'");
        while (true) {
            try {
                if (!sqlUsers.next() && sqlUsers.getString(0) != null) break;
        //        users.put(sqlUsers.getString("login"), new User(sqlUsers.getString("login"),sqlUsers.getString("pass"),sqlUsers.getString("nickname")));
                if (sqlUsers.getString("pass").equals(password)) {
                    return sqlUsers.getString("nickname");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        DB.closeConnection();
        //User user = users.get(login);
        //if (user != null && user.getPassword().equals(password)) {
        //    return user.getNick();
       // }
        return null;
    }

    @Override
    public boolean rename(String newNick, String login) {
        int isok = 0;
        DB.getConnection();
        isok = DB.update("update `users` set `nickname` = '" + newNick + "' where `login` = '" + login + "'");
        System.out.println("rename ok: " + isok);
        DB.closeConnection();
        if (isok == 1) {
            return true;
        }
        return false;
    }

    @Override
    public void stop() {
        System.out.println("Сервис остановился");
    }
}
