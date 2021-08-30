package sdb.main;

import org.apache.spark.sql.SparkSession;
import sdb.util.SDBSpark2MySqlConnection;
import sdb.util.SDBSparkConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SparkStoreData {
    public void createBigVInfo() throws SQLException {
        Connection conn = SDBSparkConnection.getConnection();
        Statement stmt = conn.createStatement();
        String big_V_info = "CREATE TABLE big_V_info ( FCode string, Big_V_name string,Fund_name string, Fans_num int, Evaluation int,FTypeName string,ZHBIntervalRate double, AccountExistTime int, AssetVol double ) USING com.sequoiadb.spark OPTIONS ( host 'iambo:11810', collectionspace 'big_V_Fund_test', collection 'big_V_info', username 'sdbadmin', password 'sdbadmin')";
        boolean execute = stmt.execute(big_V_info);
        System.out.println(execute);
        stmt.close();
        conn.close();
    }

    public void createBigVFundInfo() throws SQLException {
        Connection conn = SDBSparkConnection.getConnection();
        Statement stmt = conn.createStatement();
        String big_V_fund_info = "CREATE TABLE big_V_fund_info ( FCode string, FDate string,NAV double,Yield double,Amount double ) USING com.sequoiadb.spark OPTIONS ( host 'iambo:11810', collectionspace 'big_V_Fund_test', collection 'big_V_fund_info', username 'sdbadmin', password 'sdbadmin')";
        boolean execute = stmt.execute(big_V_fund_info);
        ResultSet rs = stmt.executeQuery("select * from big_V_fund_info");
        while (rs.next()) {
            System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " +
                    rs.getString(4) + " " + rs.getString(5));
        }
        System.out.println(execute);
        stmt.close();
        conn.close();
    }

    public static void main(String[] args) throws SQLException {
        SparkSession spark = SDBSpark2MySqlConnection.getSpark();
    }
}
