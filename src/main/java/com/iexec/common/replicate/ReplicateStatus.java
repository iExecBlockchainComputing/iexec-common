package com.iexec.common.replicate;

import com.iexec.common.chain.ChainContributionStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ReplicateStatus {

    CREATED,
    RUNNING,//TODO rename to STARTING
    STARTED,
    START_FAILED,
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
    CANT_CONTRIBUTE,
    CONTRIBUTING,
    CONTRIBUTE_FAILED,
    CONTRIBUTED,
    CANT_REVEAL,
    REVEALING,
    REVEALED,
    REVEAL_FAILED,
    RESULT_UPLOAD_REQUESTED,
    RESULT_UPLOAD_REQUEST_FAILED,
    RESULT_UPLOADING,
    RESULT_UPLOADED,
    RESULT_UPLOAD_FAILED,
    COMPLETING,
    COMPLETED,
    COMPLETE_FAILED,
    REVEAL_TIMEOUT,
    WORKER_LOST,
    ABORTED_ON_CONSENSUS_REACHED,
    ABORTED_ON_CONTRIBUTION_TIMEOUT,
    FAILED,
    OUT_OF_GAS,
    RECOVERING;

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
                STARTED,
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
                COMPLETING,
                COMPLETED,
                RECOVERING);
    }

    public static List<ReplicateStatus> getStatusesBeforeContributed() {
        return Arrays.asList(
                CREATED,
                RUNNING,
                STARTED,
                START_FAILED,
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
                CANT_CONTRIBUTE,
                CONTRIBUTING,
                CONTRIBUTE_FAILED,
                RECOVERING);
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
                STARTED,
                APP_DOWNLOADING,
                APP_DOWNLOADED,
                DATA_DOWNLOADING,
                DATA_DOWNLOADED,
                COMPUTING,
                RECOVERING);
    }

    public static boolean isRecoverableStatus(ReplicateStatus status) {
        return Arrays.asList(
                CREATED,
                RUNNING,
                STARTED,
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
                RESULT_UPLOAD_REQUESTED,
                RESULT_UPLOADING,
                RESULT_UPLOADED,
                RECOVERING)
            .contains(status);
    }

    public static List<ReplicateStatus> getCompletableStatuses() {
        return Arrays.asList(
                CREATED,
                RUNNING,
                STARTED,
                START_FAILED,
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
                CONTRIBUTING,
                CONTRIBUTED,
                REVEALING,
                REVEALED,
                RESULT_UPLOAD_REQUESTED,
                RESULT_UPLOAD_REQUEST_FAILED,   //could complete later
                RESULT_UPLOADING,
                RESULT_UPLOADED,
                RESULT_UPLOAD_FAILED            //could complete later
        );
    }

    public static List<ReplicateStatus> getUncompletableStatuses() {
        return Arrays.asList(
                CANT_CONTRIBUTE,
                CONTRIBUTE_FAILED,
                CANT_REVEAL,
                REVEAL_FAILED,
                //RESULT_UPLOAD_REQUEST_FAILED, // still good if don't upload
                //RESULT_UPLOAD_FAILED,         //still good if don't upload
                ABORTED_ON_CONSENSUS_REACHED,
                ABORTED_ON_CONTRIBUTION_TIMEOUT,
                REVEAL_TIMEOUT,
                OUT_OF_GAS
        );
    }

    public static List<ReplicateStatus> getCantContributeStatus() {
        return Arrays.asList(
                CANT_CONTRIBUTE);
    }
}
