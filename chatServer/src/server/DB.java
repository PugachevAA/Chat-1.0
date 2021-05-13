package server;

import java.sql.*;

public class DB {
    private Connection sqlConnection;
    private Statement statement;

    public void getConnection() {
        try {
            sqlConnection = DriverManager.getConnection(Config.SQL_URL, Config.SQL_USER, Config.SQL_PASS);
            statement = sqlConnection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void closeConnection() {
        try {
            sqlConnection.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
