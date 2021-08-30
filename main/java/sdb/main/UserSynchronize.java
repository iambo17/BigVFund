package sdb.main;

import sdb.util.SDBMySqlConnection;

import java.sql.*;

public class UserSynchronize {
    public static void main(String[] args) throws SQLException {
        Connection conn = SDBMySqlConnection.getConnection();
        Statement stmt = conn.createStatement();
        PreparedStatement ps = conn.prepareStatement("insert into User_Invest values(?,'0',0,0)");
        ResultSet userIdRs = stmt.executeQuery("select id from User");
        while (userIdRs.next()) {
            String id = userIdRs.getString(1);
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}
