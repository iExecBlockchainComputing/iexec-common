package com.iexec.common.chain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ChainDeal {

    // deal_pt1
    String dappPointer;
    String dappOwner;
    BigInteger dappPrice;
    String dataPointer;
    String dataOwner;
    BigInteger dataPrice;
    String poolPointer;
    String poolOwner;
    BigInteger poolPrice;

    // deal_pt2
    BigInteger trust;
    BigInteger tag;
    String requester;
    String beneficiary;
    String callback;
    List<String> params;

    // config
    BigInteger category;
    BigInteger startTime;
    BigInteger botFirst;
    BigInteger botSize;
    BigInteger workerStake;
    BigInteger schedulerRewardRatio;

    static List<String> stringParamsToList(String params){
        List<String> listParams = new ArrayList<>();
        try {
            LinkedHashMap tasksParamsMap = new ObjectMapper().readValue(params, LinkedHashMap.class);
            listParams = new ArrayList<String>(tasksParamsMap.values());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listParams;
    }


}
