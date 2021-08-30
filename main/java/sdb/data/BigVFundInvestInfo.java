package sdb.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BigVFundInvestInfo {
    private double totalGain;
    private double annualYield;
    private double maxRetracement;
    private double sharpeRatio;
    private double annualVolatility;
}

