package com.iexec.common.chain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.web3j.tuples.generated.Tuple2;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChainAccount {

    private long deposit;
    private long locked;

    public ChainAccount(BigInteger deposit, BigInteger locked) {
        this.setDeposit(deposit.longValue());
        this.setLocked(locked.longValue());
    }

    public static ChainAccount tuple2Account(Tuple2<BigInteger, BigInteger> account) {
        if (account != null) {
            return new ChainAccount(account.getValue1(), account.getValue2());
        }
        return null;
    }

}
