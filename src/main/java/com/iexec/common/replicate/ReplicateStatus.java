package com.iexec.common.replicate;

import com.iexec.common.chain.ChainContributionStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    RESULT_UPLOAD_REQUEST_FAILED,
    RESULT_UPLOADING,
    RESULT_UPLOADED,
    RESULT_UPLOAD_FAILED,
    COMPLETED,
    CONTRIBUTION_TIMEOUT,
    REVEAL_TIMEOUT,
    WORKER_LOST,
    ABORT_CONSENSUS_REACHED,
    ABORT_CONTRIBUTION_TIMEOUT,
    ERROR;

    public static ChainContributionStatus getChainStatus(ReplicateStatus replicateStatus) {
        switch (replicateStatus) {
            case CONTRIBUTED:
                return ChainContributionStatus.CONTRIBUTED;
            case REVEALED:
                return ChainContributionStatus.REVEALED;
            default:
                return null;
        }
    }

    public static List<ReplicateStatus> getSuccessStatuses() {
        return Arrays.asList(
                CREATED,
                RUNNING,
                APP_DOWNLOADING,
                APP_DOWNLOADED,
                COMPUTING,
                COMPUTED,
                CONTRIBUTING,
                CONTRIBUTED,
                REVEALING,
                REVEALED,
                COMPLETED
        );
    }

    public static List<ReplicateStatus> getMissingStatuses(ReplicateStatus from, ReplicateStatus to) {
        boolean shouldAddStatus = false;
        List<ReplicateStatus> missingStatuses = new ArrayList<>();

        if (!getSuccessStatuses().contains(from) || !getSuccessStatuses().contains(to)){
            return new ArrayList<>();
        }

        if (getSuccessStatuses().indexOf(from) >= getSuccessStatuses().indexOf(to)){
            return new ArrayList<>();
        }

        for (ReplicateStatus status: ReplicateStatus.getSuccessStatuses()){
            if (shouldAddStatus){
                missingStatuses.add(status);
            }
            if (status.equals(from)){
                shouldAddStatus = true;
            }
            if (status.equals(to)){
                shouldAddStatus = false;
            }
        }

        return missingStatuses;
    }

}
