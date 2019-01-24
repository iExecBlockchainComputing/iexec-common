package com.iexec.common.replicate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.iexec.common.chain.ChainReceipt;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplicateStatusChange {

    private Date date;
    private ReplicateStatus status;
    private ReplicateStatusModifier modifier;
    private ChainReceipt chainReceipt;

    public ReplicateStatusChange(ReplicateStatus status, ReplicateStatusModifier modifier) {
        this.date = new Date();
        this.status = status;
        this.modifier = modifier;
    }

    public ReplicateStatusChange(ReplicateStatus status, ReplicateStatusModifier modifier, ChainReceipt chainReceipt) {
        this.date = new Date();
        this.status = status;
        this.modifier = modifier;
        this.chainReceipt = chainReceipt;
    }

}
