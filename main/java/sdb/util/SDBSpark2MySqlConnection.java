package sdb.util;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Properties;

public class SDBSpark2MySqlConnection {
    private static final String url="jdbc:mysql://120.24.52.13/big_V_Fund_test";
    private static final SparkSession spark;
    private static final Properties dbProperties =new Properties();
    static {
        spark = SparkSession.builder().appName("SDBSpark2MySqlConnection").master("local").getOrCreate();
        dbProperties.setProperty("user", "sdbadmin");
        dbProperties.setProperty("password", "123456");
    }

    public static SparkSession getSpark(){
        return spark;
    }

    /** select all data in a formal like "database.table"
     *
     * @param where example: database.table(select * from database.table)
     */
    public static Dataset<Row> select(String where){
        return spark.read().jdbc(url, where, dbProperties);
    }

    /** filter a dataset with a sql like example
     *
     * @param sql example:"(select * from sparktest.mysql2spark where is_male = 1) as subset"
     */
    public static Dataset<Row> filter(String sql){
        return spark.read().jdbc(url, sql, dbProperties);
    }

    /**
     *
     * @param DF data set will be written in mysql
     * @param where database.table in mysql(cs.cl)
     */
    public static void write(Dataset<Row> DF,String where){
        DF.write().mode("error").jdbc(url, where, dbProperties);
    }
}
