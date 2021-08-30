package sdb.main;

import sdb.util.FundInvestUtils;
import sdb.util.SDBMySqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLStoreData {
    public void storeAllValue() throws SQLException {
        Connection conn = SDBMySqlConnection.getConnection();
        Statement stmt = conn.createStatement();
        PreparedStatement ps = conn.prepareStatement("select NAV from big_V_fund_info where FCode= ?");
        PreparedStatement ps2 = conn.prepareStatement("insert into value values(?,?,?,?,?,?)");
        String sql = "select FCode from big_V_info";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String FCode = rs.getString(1);
            ps.setString(1, FCode);
            ResultSet navs = ps.executeQuery();
            List<Double> nav = new ArrayList<>();
            while (navs.next()) {
                nav.add(navs.getDouble(1));
            }
            double totalGain = FundInvestUtils.totalGain(nav);
            double maxRetracement = FundInvestUtils.maxRetracement(nav);
            double annualYield = FundInvestUtils.annualYield(nav);
            double annualVolatility = FundInvestUtils.annualVolatility(nav);
            double sharpeRatio = FundInvestUtils.sharpeRatio(nav);
            ps2.setString(1, FCode);
            ps2.setDouble(2, totalGain);
            ps2.setDouble(3, annualYield);
            ps2.setDouble(4, maxRetracement);
            ps2.setDouble(5, sharpeRatio);
            ps2.setDouble(6, annualVolatility);
            int i = ps2.executeUpdate();
            System.out.println(i);
        }
    }

    public static void main(String[] args) {

    }
}
