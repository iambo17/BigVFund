package sdb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;
import sdb.dao.impl.UserCollectDao;
import sdb.dao.impl.UserInfoDao;
import sdb.data.BigVFundInvestInfo;
import sdb.data.CustomCollection;
import sdb.data.UserCollectionInfo;
import sdb.factory.UserBeanFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service("userService")
public class UserService {
    public boolean userRegister(String username, String password, String nickname) throws SQLException, JsonProcessingException {
        UserInfoDao userInfoDao = UserBeanFactory.getUserInfoDao();
        return userInfoDao.userRegister(username, password, nickname);
    }

    public boolean userLogin(String username, String password) throws SQLException {
        UserInfoDao userInfoDao = UserBeanFactory.getUserInfoDao();
        return userInfoDao.userLogin(username, password);
    }

    public boolean userCollect(String username, String FCode) {
        UserCollectDao userCollectionDao = UserBeanFactory.getUserCollectionDao();
        return userCollectionDao.userCollect(username, FCode);
    }

    public String userCheck(String username) throws SQLException, IOException {
        UserCollectDao userCollectionDao = UserBeanFactory.getUserCollectionDao();
        return userCollectionDao.userCheck(username);
    }

    public String userCustomCombine(UserCollectionInfo userCollectionInfo) throws SQLException, IOException {
        BigVFundInvestInfo funds = UserBeanFactory.getUserCustomCombineDao().userCustomCombine(userCollectionInfo);
        UserBeanFactory.getUserCustomCombineDao().userCustomCombineInsert(userCollectionInfo,funds);
        ObjectMapper mapper=new ObjectMapper();
        return mapper.writeValueAsString(funds);
    }

    public String userCustomCombineCheck(String username) throws SQLException, IOException {
        List<CustomCollection> collections = UserBeanFactory.getUserCustomCombineDao().userCustomCombineCheck(username);
        ObjectMapper mapper=new ObjectMapper();
        return mapper.writeValueAsString(collections);
    }
}
