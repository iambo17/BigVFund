package sdb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sdb.data.FundsList;
import sdb.service.FundService;
import sdb.util.ApplicationContext;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Controller("fundController")
@RequestMapping("/fund")
public class FundController {
    //获取所有大V组合的基本数据（组合名 组合编号 粉丝数等）
    @RequestMapping("/info")
    @ResponseBody
    public String fundInfo() throws SQLException, IOException {
        FundService fundService = (FundService) ApplicationContext.getApp().getBean("fundService");
        return fundService.getFund();
    }

    //获取某个大V组合在指定时间区间内的NAV
    @RequestMapping("/nav")
    @ResponseBody
    public String fundNAV(String FCode, String BeginTime, String EndTime) throws SQLException, IOException {
        FundService fundService = (FundService) ApplicationContext.getApp().getBean("fundService");
        return fundService.getFundNAV(FCode, BeginTime, EndTime);
    }

    //获取指定一系列大V组合的 共同 时间区间
    @RequestMapping("/date")
    @ResponseBody
    public String fundDate(@RequestBody FundsList fund) throws SQLException, IOException {
        FundService fundService = (FundService) ApplicationContext.getApp().getBean("fundService");
        System.out.println(fund);
        List<String> funds = fund.getFunds();
        String[] Fund=funds.toArray(new String[funds.size()]);
        return fundService.getFundDate(Fund);
    }
/*    public String fundDate(@RequestParam("funds[]") String[] funds) throws SQLException, IOException {
        FundService fundService = (FundService) ApplicationContext.getApp().getBean("fundService");
        return fundService.getFundDate(funds);
    }*/

    //获取大V组合在某个时间区间的涨幅、年化收益率、最大回撤、夏普比、年化波动率
    @RequestMapping("/data")
    @ResponseBody
    public String fundData(String FCode, String BeginTime, String EndTime) throws SQLException, IOException {
        FundService fundService = (FundService) ApplicationContext.getApp().getBean("fundService");
        return fundService.getFundData(FCode, BeginTime, EndTime);
    }
}
