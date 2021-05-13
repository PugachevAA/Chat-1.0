package server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class BaseAuthService implements AuthService{
    DB db = new DB();
    @Override
    public void start() {
        System.out.println("Сервис запущен");
    }

    @Override
    public String getNickByLoginPass(String login, String password) {
        db.getConnection();
        ResultSet sqlUsers = db.read("select * from `users` where `login` = '" + login + "'");
        while (true) {
            try {
                if (!sqlUsers.next()) break;
                //if (sqlUsers.getString(1) != null) break;
                if (sqlUsers.getString("pass").equals(password)) {
                    return sqlUsers.getString("nickname");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        db.closeConnection();
        return null;
    }

    @Override
    public boolean rename(String newNick, String login) {
        int isok = 0;
        db.getConnection();
        isok = db.update("update `users` set `nickname` = '" + newNick + "' where `login` = '" + login + "'");
        System.out.println("rename ok: " + isok);
        db.closeConnection();
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
