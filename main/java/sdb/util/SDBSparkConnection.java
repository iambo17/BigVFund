package sdb.util;

import java.sql.*;
import java.util.ResourceBundle;

public class SDBSparkConnection {
    private static final String url;
    private static final String user;
    private static final String password;

    static {
        ResourceBundle rb = ResourceBundle.getBundle("SDBSpark");
        String driver = rb.getString("driver");
        url = rb.getString("url");
        user = rb.getString("user");
        password = rb.getString("password");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private SDBSparkConnection() {
    }
}
