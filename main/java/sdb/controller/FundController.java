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
    //��ȡ���д�V��ϵĻ������ݣ������ ��ϱ�� ��˿���ȣ�
    @RequestMapping("/info")
    @ResponseBody
    public String fundInfo() throws SQLException, IOException {
        FundService fundService = (FundService) ApplicationContext.getApp().getBean("fundService");
        return fundService.getFund();
    }

    //��ȡĳ����V�����ָ��ʱ�������ڵ�NAV
    @RequestMapping("/nav")
    @ResponseBody
    public String fundNAV(String FCode, String BeginTime, String EndTime) throws SQLException, IOException {
        FundService fundService = (FundService) ApplicationContext.getApp().getBean("fundService");
        return fundService.getFundNAV(FCode, BeginTime, EndTime);
    }

    //��ȡָ��һϵ�д�V��ϵ� ��ͬ ʱ������
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

    //��ȡ��V�����ĳ��ʱ��������Ƿ����껯�����ʡ����س������ձȡ��껯������
    @RequestMapping("/data")
    @ResponseBody
    public String fundData(String FCode, String BeginTime, String EndTime) throws SQLException, IOException {
        FundService fundService = (FundService) ApplicationContext.getApp().getBean("fundService");
        return fundService.getFundData(FCode, BeginTime, EndTime);
    }
}
