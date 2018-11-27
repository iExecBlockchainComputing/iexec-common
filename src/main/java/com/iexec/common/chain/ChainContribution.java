package com.iexec.common.chain;

import com.iexec.common.utils.BytesUtils;
import lombok.*;
import org.web3j.tuples.generated.Tuple4;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class ChainContribution {

    private ChainContributionStatus status;
    private String resultHash;
    private String resultSeal;
    private String enclaveChallenge;

    public ChainContribution(BigInteger status, byte[] resultHash, byte[] resultSeal, String enclaveChallenge) {
        this.setStatus(status);
        this.setResultHash(resultHash);
        this.setResultSeal(resultSeal);
        this.setEnclaveChallenge(enclaveChallenge);
    }

    public static ChainContribution tuple2Contribution(Tuple4<BigInteger, byte[], byte[], String> contribution) {
        if (contribution != null) {
            return new ChainContribution(contribution.getValue1(),
                    contribution.getValue2(),
                    contribution.getValue3(),
                    contribution.getValue4());
        }
        return null;
    }

    public void setStatus(BigInteger status) {
        this.status = ChainContributionStatus.getValue(status);
    }

    public void setResultHash(byte[] resultHash) {
        this.resultHash = BytesUtils.bytesToString(resultHash);
    }

    public void setResultSeal(byte[] resultSeal) {
        this.resultSeal = BytesUtils.bytesToString(resultSeal);
    }

    public void setStatus(ChainContributionStatus status) {
        this.status = status;
    }

    public void setResultHash(String resultHash) {
        this.resultHash = resultHash;
    }

    public void setResultSeal(String resultSeal) {
        this.resultSeal = resultSeal;
    }

    public void setEnclaveChallenge(String enclaveChallenge) {
        this.enclaveChallenge = enclaveChallenge;
    }

}
