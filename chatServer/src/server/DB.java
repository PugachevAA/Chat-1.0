package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DB {
    private static final Logger LOGGER = LogManager.getLogger(DB.class.getName());
    private Connection sqlConnection;
    private Statement statement;

    public void getConnection() {
        try {
            sqlConnection = DriverManager.getConnection(Config.SQL_URL, Config.SQL_USER, Config.SQL_PASS);
            statement = sqlConnection.createStatement();
            LOGGER.info("Подключение к БД открыто");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            LOGGER.error("Ошибка подключения к БД");
        }
    }
    public void closeConnection() {
        try {
            sqlConnection.close();
            statement.close();
            LOGGER.info("Подключение к БД закрыто");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            LOGGER.error("Ошибка закрытия подключения к БД");
        }
    }

    public ResultSet read(String query) {
        ResultSet queryResult = null;
        try {
            queryResult = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return queryResult;
    }

    public int update(String query) {
        int queryResult = 0;
        try {
            queryResult = statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return queryResult;
    }
}
