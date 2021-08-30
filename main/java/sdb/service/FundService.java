package sdb.service;

import com.alibaba.fastjson.JSONArray;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;
import sdb.dao.impl.FundOwnDao;
import sdb.data.BigVFundInvestInfo;
import sdb.factory.FundBeanFactory;
import sdb.util.FundInvestUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("fundService")
public class FundService {
    /**
     * @return all the information of funds
     */
    public String getFund() throws SQLException, IOException {
        return FundBeanFactory.getFundInfoDao().fundInfo();
    }

    /**
     * @param FCode     fcode of a fund
     * @param BeginTime begin time of a fund
     * @param EndTime   end time of a fund
     * @return NAV list of a fund in range of BeginTime from EndTime
     */
    public String getFundNAV(String FCode, String BeginTime, String EndTime) throws SQLException, IOException {
        return FundBeanFactory.getFundOwnDao().fundNAV(FCode, BeginTime, EndTime);
    }

    /**
     * @return 涨幅、年化收益率、最大回撤、夏普比、年化波动率
     */
    public String getFundData(String FCode, String BeginTime, String EndTime) throws SQLException, IOException {
        String NAVs = this.getFundNAV(FCode, BeginTime, EndTime);
        List<Double> list = JSONArray.parseArray(NAVs, Double.class);
        BigVFundInvestInfo fundInvestInfo = new BigVFundInvestInfo(FundInvestUtils.totalGain(list), FundInvestUtils.annualYield(list),
                FundInvestUtils.maxRetracement(list), FundInvestUtils.sharpeRatio(list), FundInvestUtils.annualVolatility(list));
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(fundInvestInfo);
    }

    /**
     * @param funds the fcode of some fund
     * @return the range of the date of a fund
     */
    public String getFundDate(String[] funds) throws SQLException, IOException {

        FundOwnDao dao = FundBeanFactory.getFundOwnDao();
        long beginTime = 0;
        long endTime = new Date().getTime();
        for (String s : funds) {
            List<Long> lists = dao.fundDate(s);
            if (lists.get(0) >= beginTime) {
                beginTime = lists.get(0);
            }
            if (lists.get(1) <= endTime) {
                endTime = lists.get(1);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Long> map = new HashMap<>();
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return mapper.writeValueAsString(map);
    }
}
