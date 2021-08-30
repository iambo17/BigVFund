package sdb.dao.impl;

import sdb.dao.UserDao;
import sdb.util.SDBClose;
import sdb.util.SDBMySqlConnection;

import java.sql.*;

public class UserInfoDao implements UserDao {
    @Override
    public void userDescribe() {
        System.out.println("Dao for user's information");
    }

    public boolean userRegister(String username, String password, String nickname) throws SQLException {
        if (this.checkUser(username)) {
            return false;
        } else {
            Connection conn = SDBMySqlConnection.getConnection();
            String insert = "insert into User values(?,?,?)";
            PreparedStatement ps = conn.prepareStatement(insert);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, nickname);
            PreparedStatement ps2 = conn.prepareStatement("insert into User_Invest values(?,'0',0,0)");
            ps2.setString(1,username);
            ps.executeUpdate();
            ps2.executeUpdate();
            ps.close();
            ps2.close();
            conn.close();
            return true;
        }
    }

    private boolean checkUser(String username) throws SQLException {
        Connection conn = SDBMySqlConnection.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "select id from User";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            if (username.equals(rs.getString(1))) {
                SDBClose.close(conn, stmt, rs);
                return true;
            }
        }
        SDBClose.close(conn, stmt, rs);
        return false;
    }

    public boolean userLogin(String username, String password) throws SQLException {
        Connection conn = SDBMySqlConnection.getConnection();
        String sql = "select password from User where id = ? ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            if (password.equals(rs.getString(1))) {
                SDBClose.close(conn, ps, rs);
                return true;
            }
        }
        SDBClose.close(conn, ps, rs);
        return false;
    }
}
