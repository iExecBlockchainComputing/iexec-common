package com.iexec.common.chain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Slf4j
public class ChainDeal {

    String chainDealId;
    // deal_pt1
    ChainApp chainApp;
    String dappOwner;
    BigInteger dappPrice;
    ChainDataset chainDataset;
    String dataPointer;
    String dataOwner;
    BigInteger dataPrice;
    String poolPointer;
    String poolOwner;
    BigInteger poolPrice;

    // deal_pt2
    BigInteger trust;
    String tag;
    String requester;
    String beneficiary;
    String callback;
    DealParams params;

    // config
    ChainCategory chainCategory;
    BigInteger startTime;
    BigInteger botFirst;
    BigInteger botSize;
    BigInteger workerStake;
    BigInteger schedulerRewardRatio;

    public static DealParams stringToDealParams(String params) {
        try {
            DealParams dealParams = new ObjectMapper().readValue(params, DealParams.class);
            if(dealParams.getIexec_input_files() == null) {
                dealParams.setIexec_input_files(new ArrayList<>());
            }
            return dealParams;
        } catch (IOException e) {
            log.warn("Params string is not a JSON, considering the string is one full param");
            //the requester want to execute one task with the whole string
            return DealParams.builder()
                    .iexec_args(params)
                    .iexec_input_files(new ArrayList<>())
                    .build();
        }
    }
}
