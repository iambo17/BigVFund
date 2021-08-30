package sdb.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BigVFundInfo {
    private String FCode;
    private String Big_V_name;
    private String Fund_name;
    private int Fans_num;
    private int Evaluation;
    private String FTypeName;
    private double ZHBIntervalRate;
    private int AccountExistTime;
    private double AssetVol;
}
