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
    DATA_DOWNLOADING,
    DATA_DOWNLOADED,
    DATA_DOWNLOAD_FAILED,
    COMPUTING,
    COMPUTED,
    COMPUTE_FAILED,
    CAN_CONTRIBUTE,
    CANT_CONTRIBUTE_SINCE_STAKE_TOO_LOW,
    CANT_CONTRIBUTE_SINCE_TASK_NOT_ACTIVE,
    CANT_CONTRIBUTE_SINCE_AFTER_DEADLINE,
    CANT_CONTRIBUTE_SINCE_CONTRIBUTION_ALREADY_SET,
    CONTRIBUTING,
    CONTRIBUTE_FAILED,
    CONTRIBUTED,
    CANT_REVEAL,
    REVEALING,
    REVEALED,
    REVEAL_FAILED,
    RESULT_UPLOAD_REQUEST_FAILED,
    RESULT_UPLOADING,
    RESULT_UPLOADED,
    RESULT_UPLOAD_FAILED,
    COMPLETED,
    REVEAL_TIMEOUT,
    WORKER_LOST,
    ABORTED_ON_CONSENSUS_REACHED,
    ABORTED_ON_CONTRIBUTION_TIMEOUT,
    ERROR,
    OUT_OF_GAS;

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
                DATA_DOWNLOADING,
                DATA_DOWNLOADED,
                COMPUTING,
                COMPUTED,
                CAN_CONTRIBUTE,
                CONTRIBUTING,
                CONTRIBUTED,
                REVEALING,
                REVEALED,
                COMPLETED
        );
    }

    public static List<ReplicateStatus> getStatusesBeforeContributed() {
        return Arrays.asList(
                CREATED,
                RUNNING,
                APP_DOWNLOADING,
                APP_DOWNLOADED,
                APP_DOWNLOAD_FAILED,
                DATA_DOWNLOADING,
                DATA_DOWNLOADED,
                DATA_DOWNLOAD_FAILED,
                COMPUTING,
                COMPUTED,
                COMPUTE_FAILED,
                CAN_CONTRIBUTE,
                CANT_CONTRIBUTE_SINCE_STAKE_TOO_LOW,
                CANT_CONTRIBUTE_SINCE_TASK_NOT_ACTIVE,
                CANT_CONTRIBUTE_SINCE_AFTER_DEADLINE,
                CANT_CONTRIBUTE_SINCE_CONTRIBUTION_ALREADY_SET,
                CONTRIBUTING,
                CONTRIBUTE_FAILED
        );
    }

    public static List<ReplicateStatus> getMissingStatuses(ReplicateStatus from, ReplicateStatus to) {
        List<ReplicateStatus> statuses = getSuccessStatuses();
        if (!statuses.contains(from) || !statuses.contains(to)) {
            return new ArrayList<>();
        }

        if (statuses.indexOf(from) >= statuses.indexOf(to)) {
            return new ArrayList<>();
        }

        return statuses.subList(statuses.indexOf(from) + 1, statuses.indexOf(to) + 1);
    }

    public static List<ReplicateStatus> getSuccessStatusesBeforeComputed() {
        return Arrays.asList(
                CREATED,
                RUNNING,
                APP_DOWNLOADING,
                APP_DOWNLOADED,
                DATA_DOWNLOADING,
                DATA_DOWNLOADED,
                COMPUTING);
    }
}
