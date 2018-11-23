package com.iexec.common.replicate;

import com.iexec.common.chain.ChainContributionStatus;

public enum ReplicateStatus {

    CREATED, RUNNING, COMPUTED, CONTRIBUTED, REVEALED, UPLOAD_RESULT_REQUEST_FAILED, UPLOADING_RESULT,
    RESULT_UPLOADED, COMPLETED, WORKER_LOST, ERROR;

    public static boolean isBlockchainStatus(ReplicateStatus status) {
        return status.equals(ReplicateStatus.CONTRIBUTED)
                || status.equals(ReplicateStatus.REVEALED);
    }

    public static ChainContributionStatus getChainStatus(ReplicateStatus replicateStatus) {
        switch (replicateStatus){
            case CONTRIBUTED:
                return ChainContributionStatus.CONTRIBUTED;
            case REVEALED:
                return ChainContributionStatus.REVEALED;
            default:
                return null;
        }
    }
}
