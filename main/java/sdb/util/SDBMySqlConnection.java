package sdb.util;

import java.sql.*;
import java.util.ResourceBundle;

public class SDBMySqlConnection {
    private static final String url;
    private static final String user;
    private static final String password;

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("SDBMySql");
        String driver = resourceBundle.getString("driver");
        url = resourceBundle.getString("url");
        user = resourceBundle.getString("user");
        password = resourceBundle.getString("password");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private SDBMySqlConnection() {
    }
}
