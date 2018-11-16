package com.iexec.common.replicate;

public enum ReplicateStatus {

    CREATED, RUNNING, COMPUTED, CONTRIBUTED, REVEALED, UPLOAD_RESULT_REQUEST_FAILED, UPLOADING_RESULT, RESULT_UPLOADED, WORKER_LOST, ERROR;

    public static boolean isBlockchainStatus(ReplicateStatus status) {
        return status.equals(ReplicateStatus.CONTRIBUTED)
                || status.equals(ReplicateStatus.REVEALED);
    }
}
