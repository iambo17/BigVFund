package sdb.dao.impl;

import org.codehaus.jackson.map.ObjectMapper;
import sdb.dao.UserDao;
import sdb.data.BigVFundInfo;
import sdb.util.SDBClose;
import sdb.util.SDBMySqlConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserCollectDao implements UserDao {
    @Override
    public void userDescribe() {
        System.out.println("Dao for user's collection");
    }

    public Boolean userCollect(String username, String FCode) {
        int flag = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = SDBMySqlConnection.getConnection();
            ps = conn.prepareStatement("insert into User_Collect values (?, ?)");
            ps.setString(1, username);
            ps.setString(2, FCode);
            flag = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag == 1;
    }

    public String userCheck(String username) throws SQLException, IOException {
        String sql = "select collect_fund_code from User_Collect where id = ?";
        Connection conn = SDBMySqlConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        String fund = "select * from big_V_info where FCode = ";
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        List<BigVFundInfo> list = new ArrayList<>();
        while (rs.next()) {
            String FCode = rs.getString(1);
            Statement stmt = conn.createStatement();
            ResultSet set = stmt.executeQuery(fund + FCode);
            while(set.next()) {
                list.add(new BigVFundInfo(set.getString(1), set.getString(2),
                        set.getString(3), set.getInt(4),
                        set.getInt(5), set.getString(6),
                        set.getDouble(7), set.getInt(8),
                        set.getDouble(9)));
            }
            set.close();
            stmt.close();
        }
        SDBClose.close(conn, ps, rs);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(list);
    }
}
