package com.iexec.common.replicate;

import com.iexec.common.chain.ChainContributionStatus;

public enum ReplicateStatus {

    CREATED,
    RUNNING,
    APP_DOWNLOADING,
    APP_DOWNLOADED,
    APP_DOWNLOAD_FAILED,
    COMPUTING,
    COMPUTED,
    COMPUTE_FAILED,
    CONTRIBUTING,
    CONTRIBUTED,
    CONTRIBUTE_FAILED,
    REVEALING,
    REVEALED,
    REVEAL_FAILED,
    UPLOAD_RESULT_REQUEST_FAILED,
    RESULT_UPLOADING,
    RESULT_UPLOADED,
    RESULT_UPLOAD_FAILED,
    COMPLETED,
    WORKER_LOST,
    ERROR;


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
