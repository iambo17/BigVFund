package sdb.factory;

import sdb.dao.impl.UserCollectDao;
import sdb.dao.impl.UserCustomCombineDao;
import sdb.dao.impl.UserInfoDao;
import sdb.util.ApplicationContext;

public class UserBeanFactory {
    private static final UserInfoDao userInfoDao = new UserInfoDao();
    private static final UserCollectDao userCollectionDao = new UserCollectDao();
    private static final UserCustomCombineDao userCustomCombineDao =(UserCustomCombineDao) ApplicationContext.getApp().getBean("userCustomCombineDao");

    public static UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }

    public static UserCollectDao getUserCollectionDao() {
        return userCollectionDao;
    }

    public static UserCustomCombineDao getUserCustomCombineDao(){
        return userCustomCombineDao;
    }
}
