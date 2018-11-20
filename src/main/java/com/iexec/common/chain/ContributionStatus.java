package com.iexec.common.chain;

import java.math.BigInteger;

public enum ContributionStatus {
    UNSET,
    CONTRIBUTED,
    REVEALED,
    REJECTED;

    public static ContributionStatus getValue(int i) {
        return ContributionStatus.values()[i];
    }

    public static ContributionStatus getValue(BigInteger i) {
        return ContributionStatus.values()[i.intValue()];
    }

}
