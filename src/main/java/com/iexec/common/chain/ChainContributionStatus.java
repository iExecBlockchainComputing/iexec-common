package com.iexec.common.chain;

import java.math.BigInteger;

public enum ChainContributionStatus {
    UNSET,
    CONTRIBUTED,
    REVEALED,
    REJECTED;

    public static ChainContributionStatus getValue(int i) {
        return ChainContributionStatus.values()[i];
    }

    public static ChainContributionStatus getValue(BigInteger i) {
        return ChainContributionStatus.values()[i.intValue()];
    }

}
