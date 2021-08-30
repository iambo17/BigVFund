package sdb.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomCollection {
    private int num;
    private BigVFundInvestInfo fundInfo;
    private List<BigVFundInfo> members;
}
