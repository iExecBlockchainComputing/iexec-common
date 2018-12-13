package com.iexec.common.chain;

import com.iexec.common.utils.BytesUtils;
import lombok.*;
import org.web3j.tuples.generated.Tuple12;

import java.math.BigInteger;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class ChainTask {

    private ChainTaskStatus status;
    private String dealid;
    private int idx;
    private long timeRef;
    private long contributionDeadline;
    private long revealDeadline;
    private long finalDeadline;
    private String consensusValue;
    private int revealCounter;
    private int winnerCounter;
    private List<String> contributors;
    private String results;


    public ChainTask(BigInteger status,
                     byte[] dealid,
                     BigInteger idx,
                     BigInteger timeRef,
                     BigInteger contributionDeadline,
                     BigInteger revealDeadline,
                     BigInteger finalDeadline,
                     byte[] consensusValue,
                     BigInteger revealCounter,
                     BigInteger winnerCounter,
                     List<String> contributors,
                     byte[] results) {
        this.setStatus(status);
        this.setDealid(dealid);
        this.setIdx(idx);
        this.setTimeRef(timeRef);
        this.setContributionDeadline(contributionDeadline);
        this.setRevealDeadline(revealDeadline);
        this.setFinalDeadline(finalDeadline);
        this.setConsensusValue(consensusValue);
        this.setRevealCounter(revealCounter);
        this.setWinnerCounter(winnerCounter);
        this.setContributors(contributors);
        this.setResults(results);

    }

    public static ChainTask tuple2ChainTask(Tuple12<BigInteger, byte[], BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, byte[], BigInteger, BigInteger, List<String>, byte[]> chainTask) {
        if (chainTask != null) {
            return new ChainTask(chainTask.getValue1(),
                    chainTask.getValue2(),
                    chainTask.getValue3(),
                    chainTask.getValue4(),
                    chainTask.getValue5(),
                    chainTask.getValue6(),
                    chainTask.getValue7(),
                    chainTask.getValue8(),
                    chainTask.getValue9(),
                    chainTask.getValue10(),
                    chainTask.getValue11(),
                    chainTask.getValue12());
        }
        return null;
    }

    private void setStatus(BigInteger status) {
        this.status = ChainTaskStatus.getValue(status);
    }

    private void setDealid(byte[] dealid) {
        this.dealid = BytesUtils.bytesToString(dealid);
    }

    private void setIdx(BigInteger idx) {
        this.idx = idx.intValue();
    }

    private void setTimeRef(BigInteger timeRef) {
        this.contributionDeadline = timeRef.longValue() * 1000L;
    }

    private void setContributionDeadline(BigInteger contributionDeadline) {
        this.contributionDeadline = contributionDeadline.longValue() * 1000L;
    }

    private void setRevealDeadline(BigInteger revealDeadline) {
        this.revealDeadline = revealDeadline.longValue() * 1000L;
    }

    private void setFinalDeadline(BigInteger finalDeadline) {
        this.finalDeadline = finalDeadline.longValue() * 1000L;
    }

    private void setConsensusValue(byte[] consensusValue) {
        this.consensusValue = BytesUtils.bytesToString(consensusValue);
    }

    private void setRevealCounter(BigInteger revealCounter) {
        this.revealCounter = revealCounter.intValue();
    }

    private void setWinnerCounter(BigInteger winnerCounter) {
        this.winnerCounter = winnerCounter.intValue();
    }

    public void setContributors(List<String> contributors) {
        this.contributors = contributors;
    }

    private void setResults(byte[] results) {
        this.results = BytesUtils.bytesToString(results);
    }


}
