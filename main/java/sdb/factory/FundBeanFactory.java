package sdb.factory;

import sdb.dao.impl.FundOwnDao;
import sdb.dao.impl.FundInfoDao;

public class FundBeanFactory {
    private static final FundOwnDao fundOwnDao = new FundOwnDao();
    private static final FundInfoDao fundInfoDao = new FundInfoDao();

    public static FundOwnDao getFundOwnDao(){
        return fundOwnDao;
    }

    public static FundInfoDao getFundInfoDao(){
        return fundInfoDao;
    }
}
