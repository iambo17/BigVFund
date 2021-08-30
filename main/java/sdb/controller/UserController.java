package sdb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sdb.data.CollectionInfo;
import sdb.data.UserCollectionInfo;
import sdb.service.UserService;
import sdb.util.ApplicationContext;
import java.util.List;

import java.io.IOException;
import java.sql.SQLException;

@Controller("userController")
@RequestMapping("/user")
public class UserController {
    /**
     * @param username new username
     * @param password new user's password
     * @param nickname new user's name
     * @return register information 0:fail   1:success
     */
    @RequestMapping("/register")
    @ResponseBody
    public String userRegister(String username, String password, String nickname) throws SQLException, JsonProcessingException {
        UserService userService = (UserService) ApplicationContext.getApp().getBean("userService");
        if (userService.userRegister(username, password, nickname)) {
            return "{\"register\":1}";
        } else {
            return "{\"register\":0}";
        }
    }

    @RequestMapping("/login")
    @ResponseBody
    public String userLogin(String username, String password) throws SQLException {
        UserService userService = (UserService) ApplicationContext.getApp().getBean("userService");
        if (userService.userLogin(username, password)) {
            return "{\"login\":1}";
        } else {
            return "{\"login\":0}";
        }
    }

    @RequestMapping("/collect")
    @ResponseBody
    public String userCollection(String username, String FCode) {
        UserService userService = (UserService) ApplicationContext.getApp().getBean("userService");
        if (userService.userCollect(username, FCode)) {
            return "{\"collect\":1}";
        } else {
            return "{\"collect\":0}";
        }
    }

    @RequestMapping("/check")
    @ResponseBody
    public String userCheck(String username) throws SQLException, IOException {
        UserService userService = (UserService) ApplicationContext.getApp().getBean("userService");
        return userService.userCheck(username);
    }

    @RequestMapping("/customCombine")
    @ResponseBody
    public String userCustomCombine(@RequestBody UserCollectionInfo userCollectionInfo) throws SQLException, IOException {
        UserService userService = (UserService) ApplicationContext.getApp().getBean("userService");
        System.out.println(userCollectionInfo.getUsername());
        System.out.println(userCollectionInfo.getCollectionInfo());
        System.out.println(userCollectionInfo);
        return userService.userCustomCombine(userCollectionInfo);
    }

    @RequestMapping("/customCheck")
    @ResponseBody
    public String userCustomCheck(String username) throws SQLException, IOException {
        UserService userService = (UserService) ApplicationContext.getApp().getBean("userService");
        String s = userService.userCustomCombineCheck(username);
        System.out.println(s);
        return s;
    }
}
