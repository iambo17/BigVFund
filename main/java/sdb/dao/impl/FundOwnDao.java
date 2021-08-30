package sdb.dao.impl;

import org.codehaus.jackson.map.ObjectMapper;
import sdb.dao.FundDao;
import sdb.util.SDBClose;
import sdb.util.SDBMySqlConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FundOwnDao implements FundDao {
    @Override
    public void fundDescribe() {
        System.out.println("Get the information of some days of a fund.");
    }

    public String fundNAV(String FCode, String BeginTime, String EndTime) throws SQLException, IOException {
        Connection conn = SDBMySqlConnection.getConnection();
        String Begin = "/Date(" + BeginTime + ")/";
        String End = "/Date(" + EndTime + ")/";
        String sql = "select NAV from big_V_fund_info where FCode = ? and (FDate between ? and ? )";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, FCode);
        ps.setString(2, Begin);
        ps.setString(3, End);
        ResultSet rs = ps.executeQuery();
        List<String> list = new ArrayList<>();
        while (rs.next()) {
            list.add(rs.getString(1));
        }
        SDBClose.close(conn, ps, rs);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(list);
    }

    public List<Long> fundDate(String FCode) throws SQLException, IOException {
        Connection conn = SDBMySqlConnection.getConnection();
        String sql = "select FDate from big_V_fund_info where FCode = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, FCode);
        ResultSet rs = ps.executeQuery();
        List<String> list = new ArrayList<>();
        while (rs.next()) {
            list.add(rs.getString(1));
        }
        List<Long> map = new ArrayList<>();
        map.add(Long.parseLong(list.get(0).substring(6, 19)));
        map.add(Long.parseLong(list.get(list.size() - 1).substring(6, 19)));
        SDBClose.close(conn, ps, rs);
        return map;
    }
}
