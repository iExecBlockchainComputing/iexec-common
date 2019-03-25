package com.iexec.common.replicate;

import com.iexec.common.chain.ChainReceipt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplicateDetails {

    String resultLink;
    ChainReceipt chainReceipt;
}