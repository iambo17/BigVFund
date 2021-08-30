package sdb.util;

import java.util.List;

public class FundInvestUtils {
    /*
         "totalGain":涨幅（百分比）
         "annualYield":年化收益率（百分比）
         "maxRetracement":最大回撤（小数）
         "sharpeRatio":夏普比（小数）
         "annualVolatility":年化波动率（百分比）
     */
    public static double totalGain(List<Double> list) {
        return (list.get(list.size() - 1) - list.get(0)) / list.get(0);
    }

    public static double annualYield(List<Double> list) {
        int dates = list.size();
        double avgYield = ((list.get(list.size() - 1) - list.get(0)) / list.get(0)) / dates;
        return avgYield * 365;
    }

    public static double maxRetracement(List<Double> list) {
        int length = list.size();
        double maxRetracement = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            double minNav = 100.0;
            for (int j = i + 1; j < list.size(); j++) {
                if (minNav > list.get(j)) {
                    minNav = list.get(j);
                }
            }
            double temp = (list.get(i) - minNav) / list.get(i);
            if (temp > maxRetracement) {
                maxRetracement = temp;
            }
        }
        return maxRetracement;
    }

    public static double sharpeRatio(List<Double> list) {
        double noRisk = 0.03045;
        return (annualYield(list) - noRisk) / annualVolatility(list);
    }

    public static double annualVolatility(List<Double> list) {
        int dates = list.size();
        double sum = 0;
        double avg = (list.get(dates - 1) - list.get(0)) / dates;
        for (int i = 0; i < dates - 1; i++) {
            sum += Math.pow(((list.get(i + 1) - list.get(i)) / (list.get(i)) - avg), 2);
        }
        return (Math.sqrt(sum / dates) * Math.sqrt(250));
    }
}
