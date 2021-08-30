package sdb.dao.impl;

import org.codehaus.jackson.map.ObjectMapper;
import sdb.dao.FundDao;
import sdb.data.BigVFundInfo;
import sdb.util.SDBClose;
import sdb.util.SDBMySqlConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FundInfoDao implements FundDao {
    @Override
    public void fundDescribe() {
        System.out.println("Get all funds information.");
    }

    public String fundInfo() throws SQLException, IOException {
        Connection conn = SDBMySqlConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from big_V_info");
        ResultSet rs = ps.executeQuery();
        List<BigVFundInfo> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new BigVFundInfo(rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getInt(4),
                    rs.getInt(5), rs.getString(6),
                    rs.getDouble(7), rs.getInt(8),
                    rs.getDouble(9)));
        }
        SDBClose.close(conn, ps, rs);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(list);
    }
}
