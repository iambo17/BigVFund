package sdb.dao.impl;

import com.esotericsoftware.kryo.util.ObjectMap;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;
import sdb.dao.UserDao;
import sdb.data.*;
import sdb.util.SDBMySqlConnection;
import sdb.util.SDBSpark2MySqlConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("userCustomCombineDao")
public class UserCustomCombineDao implements UserDao {
    @Override
    public void userDescribe() {
        System.out.println();
    }

    public BigVFundInvestInfo userCustomCombine1(UserCollectionInfo user) {
        List<CollectionInfo> collections = user.getCollectionInfo();
        String FCode = "0";
        double ratio;
        String sparkSelect = "(select * from big_V_Fund_test.value where FCode = '" + FCode + "') as subset";
        double totalGain = 0;
        double annualYield = 0;
        double maxRetracement = 0;
        double sharpeRatio = 0;
        double annualVolatility = 0;
        for (CollectionInfo collection : collections) {
            FCode = collection.getFCode();
            ratio = collection.getRatio();
            sparkSelect = "(select * from big_V_Fund_test.value where FCode = '" + FCode + "') as subset";
            Dataset<Row> filter = SDBSpark2MySqlConnection.filter(sparkSelect);
            List<Row> rows = filter.collectAsList();
            for (Row row : rows) {
                totalGain += row.getDouble(1) * ratio;
                annualYield += row.getDouble(2) * ratio;
                maxRetracement += row.getDouble(3) * ratio;
                sharpeRatio += row.getDouble(4) * ratio;
                annualVolatility += row.getDouble(5) * ratio;
            }
        }
        return new BigVFundInvestInfo(totalGain, annualYield, maxRetracement, sharpeRatio, annualVolatility);
    }

    public BigVFundInvestInfo userCustomCombine(UserCollectionInfo user) throws SQLException {
        List<CollectionInfo> collections = user.getCollectionInfo();
        String FCode = "0";
        double ratio;
        String sql = "select * from value where FCode = ?";
        Connection conn = SDBMySqlConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        double totalGain = 0;
        double annualYield = 0;
        double maxRetracement = 0;
        double sharpeRatio = 0;
        double annualVolatility = 0;
        for (CollectionInfo collection : collections) {
            FCode = collection.getFCode();
            ratio = collection.getRatio();
            ps.setString(1, FCode);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                totalGain += rs.getDouble(2) * ratio;
                annualYield += rs.getDouble(3) * ratio;
                maxRetracement += rs.getDouble(4) * ratio;
                sharpeRatio += rs.getDouble(5) * ratio;
                annualVolatility += rs.getDouble(6) * ratio;
            }
            rs.close();
        }
        ps.close();
        conn.close();
        return new BigVFundInvestInfo(totalGain, annualYield, maxRetracement, sharpeRatio, annualVolatility);
    }

    public void userCustomCombineInsert(UserCollectionInfo user, BigVFundInvestInfo funds) throws SQLException {
        Connection conn = SDBMySqlConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet nums = stmt.executeQuery("select max(num) from User_Invest where id = '" + user.getUsername() + "'");
        int num = 0;
        while (nums.next()) {
            num = nums.getInt(1);
        }
        String username = user.getUsername();
        String sql = "insert into User_Invest values(?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        List<CollectionInfo> collections = user.getCollectionInfo();
        for (CollectionInfo collection : collections) {
            ps.setString(1, username);
            ps.setString(2, collection.getFCode());
            ps.setInt(3, num + 1);
            ps.setDouble(4, collection.getRatio());
            ps.execute();
        }
        String sql2 = "insert into User_Invest_Value values(?,?,?,?,?,?,?)";
        PreparedStatement ps2 = conn.prepareStatement(sql2);
        ps2.setString(1, username);
        ps2.setInt(2, num + 1);
        ps2.setDouble(3, funds.getTotalGain());
        ps2.setDouble(4, funds.getAnnualYield());
        ps2.setDouble(5, funds.getMaxRetracement());
        ps2.setDouble(6, funds.getSharpeRatio());
        ps2.setDouble(7, funds.getAnnualVolatility());
        nums.close();
        ps2.execute();
        ps.close();
        ps2.close();
        conn.close();
    }

    public List<CustomCollection> userCustomCombineCheck(String username) throws SQLException {
        List<CustomCollection> lists = new ArrayList<>();
        Connection conn = SDBMySqlConnection.getConnection();
        PreparedStatement nums = conn.prepareStatement("select max(num) from User_Invest_Value where id = ?");
        nums.setString(1, username);
        ResultSet num = nums.executeQuery();
        int max = 0;
        while (num.next()) {
            max = num.getInt(1);
        }
        PreparedStatement ps1 = conn.prepareStatement("select * from User_Invest_Value where num = ? and id = ?");
        PreparedStatement ps2 = conn.prepareStatement("select * from User_Invest where num = ? and id = ?");
        PreparedStatement ps3 = conn.prepareStatement("select * from big_V_info where FCode= ?");
        for (int i = 1; i <= max; i++) {
            //成员变量
            BigVFundInvestInfo fundInfo = new BigVFundInvestInfo();
            List<BigVFundInfo> members = new ArrayList<>();
            //新组合数值 ps1
            ps1.setInt(1, i);
            ps1.setString(2, username);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                fundInfo = new BigVFundInvestInfo(rs1.getDouble(3), rs1.getDouble(4),
                        rs1.getDouble(5), rs1.getDouble(6), rs1.getDouble(7));
            }
            //新组合成员 ps2
            ps2.setInt(1, i);
            ps2.setString(2, username);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                String FCode = rs2.getString(2);
                ps3.setString(1, FCode);
                ResultSet rs3 = ps3.executeQuery();
                while (rs3.next()) {
                    members.add(new BigVFundInfo(rs3.getString(1), rs3.getString(2),
                            rs3.getString(3), rs3.getInt(4),
                            rs3.getInt(5), rs3.getString(6),
                            rs3.getDouble(7), rs3.getInt(8),
                            rs3.getDouble(9)));
                }
            }
            lists.add(new CustomCollection(i, fundInfo, members));
        }
        ObjectMapper mapper=new ObjectMapper();
        return lists;
    }
}
