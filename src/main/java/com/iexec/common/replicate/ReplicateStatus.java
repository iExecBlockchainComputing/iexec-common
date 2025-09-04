/*
 * Copyright 2020-2025 IEXEC BLOCKCHAIN TECH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iexec.common.replicate;

import com.iexec.commons.poco.chain.ChainContributionStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ReplicateStatus {

    CREATED,
    STARTING,
    START_FAILED,
    STARTED,
    APP_DOWNLOADING,
    APP_DOWNLOAD_FAILED,
    APP_DOWNLOADED,
    DATA_DOWNLOADING,
    DATA_DOWNLOAD_FAILED,
    DATA_DOWNLOADED,
    COMPUTING,
    COMPUTE_FAILED,
    COMPUTED,
    CONTRIBUTING,
    CONTRIBUTE_FAILED,
    CONTRIBUTED,
    REVEALING,
    REVEAL_FAILED,
    REVEALED,
    RESULT_UPLOAD_REQUESTED,
    RESULT_UPLOADING,
    RESULT_UPLOAD_FAILED,
    RESULT_UPLOADED,
    CONTRIBUTE_AND_FINALIZE_ONGOING,
    CONTRIBUTE_AND_FINALIZE_FAILED,
    CONTRIBUTE_AND_FINALIZE_DONE,
    COMPLETING,
    COMPLETE_FAILED,
    COMPLETED,
    FAILED,
    ABORTED,

    RECOVERING,
    WORKER_LOST;

    public static boolean isSuccess(ReplicateStatus status) {
        return getSuccessStatuses().contains(status);
    }

    /**
     * @deprecated not used in other projects
     */
    @Deprecated(forRemoval = true)
    public static boolean isFailure(ReplicateStatus status) {
        return getFailureStatuses().contains(status);
    }

    /**
     * @deprecated Use non-static method on Enum member instead
     */
    @Deprecated(forRemoval = true)
    public static boolean isFailedBeforeComputed(ReplicateStatus status) {
        return status.ordinal() < COMPUTED.ordinal() && getFailureStatuses().contains(status);
    }

    /**
     * Covers START_FAILED, APP_DOWNLOAD_FAILED, DATA_DOWNLOAD_FAILED and COMPUTE_FAILED
     *
     * @return {@literal true} if the status is in the list, {@literal false} otherwise
     */
    public boolean isFailedBeforeComputed() {
        return this.ordinal() < COMPUTED.ordinal() && getFailureStatuses().contains(this);
    }

    /**
     * @deprecated not used in other projects
     */
    @Deprecated(forRemoval = true)
    public static boolean isCompletable(ReplicateStatus status) {
        return getCompletableStatuses().contains(status);
    }

    /**
     * @deprecated not used in other projects
     */
    @Deprecated(forRemoval = true)
    public static boolean isFailable(ReplicateStatus status) {
        return getFailableStatuses().contains(status);
    }

    /**
     * @deprecated not used in other projects
     */
    @Deprecated(forRemoval = true)
    public static boolean isAbortable(ReplicateStatus status) {
        return getAbortableStatuses().contains(status);
    }

    /**
     * @deprecated not used in other projects
     */
    @Deprecated(forRemoval = true)
    public static boolean isRecoverable(ReplicateStatus status) {
        return true;  // just temporary
    }

    public static List<ReplicateStatus> getSuccessStatuses() {
        return List.of(
                CREATED,
                STARTING,
                STARTED,
                APP_DOWNLOADING,
                APP_DOWNLOADED,
                DATA_DOWNLOADING,
                DATA_DOWNLOADED,
                COMPUTING,
                COMPUTED,
                CONTRIBUTING,
                CONTRIBUTED,
                REVEALING,
                REVEALED,
                CONTRIBUTE_AND_FINALIZE_ONGOING,
                CONTRIBUTE_AND_FINALIZE_DONE,
                COMPLETING,
                COMPLETED);
    }

    public static List<ReplicateStatus> getFailureStatuses() {
        return List.of(
                START_FAILED,
                APP_DOWNLOAD_FAILED,
                DATA_DOWNLOAD_FAILED,
                COMPUTE_FAILED,
                CONTRIBUTE_FAILED,
                REVEAL_FAILED,
                RESULT_UPLOAD_FAILED,
                CONTRIBUTE_AND_FINALIZE_FAILED,
                COMPLETE_FAILED,
                FAILED); // FAILED can be set from ReplicateListener in scheduler
    }

    /**
     * Statuses that should be updated to COMPLETED at the end of a task.
     */
    public static List<ReplicateStatus> getCompletableStatuses() {
        return List.of(
                REVEALED,
                RESULT_UPLOAD_REQUESTED,
                RESULT_UPLOADING,
                RESULT_UPLOAD_FAILED,
                RESULT_UPLOADED,
                CONTRIBUTE_AND_FINALIZE_DONE,
                COMPLETING,
                COMPLETE_FAILED);
    }

    /**
     * Statuses that should be updated to FAILED at the end of a task.
     */
    public static List<ReplicateStatus> getFailableStatuses() {
        return List.of(
                CREATED,
                STARTING,
                START_FAILED,
                STARTED,
                APP_DOWNLOADING,
                APP_DOWNLOAD_FAILED,
                APP_DOWNLOADED,
                DATA_DOWNLOADING,
                DATA_DOWNLOAD_FAILED,
                DATA_DOWNLOADED,
                COMPUTING,
                COMPUTE_FAILED,
                COMPUTED,
                CONTRIBUTING,
                CONTRIBUTE_FAILED,
                CONTRIBUTED,
                REVEALING,
                REVEAL_FAILED,
                CONTRIBUTE_AND_FINALIZE_ONGOING,
                CONTRIBUTE_AND_FINALIZE_FAILED,
                ABORTED);
    }

    /**
     * Statuses that can be updated to ABORTED by the worker.
     */
    public static List<ReplicateStatus> getAbortableStatuses() {
        final List<ReplicateStatus> nonFinal = new ArrayList<>(getNonFinalWorkflowStatuses());
        nonFinal.add(WORKER_LOST);
        return List.copyOf(nonFinal);
    }

    /**
     * Statuses that can be recovered by the worker
     */
    public static List<ReplicateStatus> getRecoverableStatuses() {
        return List.of(
                CREATED,
                STARTING,
                STARTED,
                APP_DOWNLOADING,
                APP_DOWNLOAD_FAILED,            // can contribute
                APP_DOWNLOADED,
                DATA_DOWNLOADING,
                DATA_DOWNLOAD_FAILED,           // can contribute
                DATA_DOWNLOADED,
                COMPUTING,
                COMPUTED,
                CONTRIBUTING,
                CONTRIBUTED,
                REVEALING,
                REVEALED,
                RESULT_UPLOAD_REQUESTED,
                RESULT_UPLOADING,
                RESULT_UPLOAD_FAILED,           // can complete later
                RESULT_UPLOADED,
                CONTRIBUTE_AND_FINALIZE_ONGOING,
                CONTRIBUTE_AND_FINALIZE_DONE,
                CONTRIBUTE_AND_FINALIZE_FAILED,
                COMPLETING,
                RECOVERING);
    }

    /*
     * Statuses of the default workflow.
     * CREATED -> COMPLETED/FAILED
     */
    public static List<ReplicateStatus> getWorkflowStatuses() {
        final List<ReplicateStatus> nonFinal = new ArrayList<>(getNonFinalWorkflowStatuses());
        nonFinal.add(COMPLETED);
        nonFinal.add(FAILED);
        return List.copyOf(nonFinal);
    }

    public static List<ReplicateStatus> getFinalStatuses() {
        return List.of(
                COMPLETED,
                FAILED);
    }

    /*
     * Non final statuses of the default workflow.
     * CREATED -> COMPLETING/COMPLETE_FAILED
     */
    public static List<ReplicateStatus> getNonFinalWorkflowStatuses() {
        return List.of(
                CREATED,
                STARTING,
                START_FAILED,
                STARTED,
                APP_DOWNLOADING,
                APP_DOWNLOAD_FAILED,
                APP_DOWNLOADED,
                DATA_DOWNLOADING,
                DATA_DOWNLOAD_FAILED,
                DATA_DOWNLOADED,
                COMPUTING,
                COMPUTE_FAILED,
                COMPUTED,
                CONTRIBUTING,
                CONTRIBUTE_FAILED,
                CONTRIBUTED,
                REVEALING,
                REVEAL_FAILED,
                REVEALED,
                RESULT_UPLOAD_REQUESTED,
                RESULT_UPLOADING,
                RESULT_UPLOAD_FAILED,
                RESULT_UPLOADED,
                CONTRIBUTE_AND_FINALIZE_ONGOING,
                CONTRIBUTE_AND_FINALIZE_FAILED,
                CONTRIBUTE_AND_FINALIZE_DONE,
                COMPLETING,
                COMPLETE_FAILED);
    }

    /**
     * @deprecated unused in other projects
     */
    @Deprecated(forRemoval = true)
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

    /**
     * @deprecated unused in other projects
     */
    @Deprecated(forRemoval = true)
    public static List<ReplicateStatus> getStatusesBeforeContributed() {
        return Arrays.asList(
                CREATED,
                STARTING,
                START_FAILED,
                STARTED,
                APP_DOWNLOADING,
                APP_DOWNLOAD_FAILED,
                APP_DOWNLOADED,
                DATA_DOWNLOADING,
                DATA_DOWNLOAD_FAILED,
                DATA_DOWNLOADED,
                COMPUTING,
                COMPUTE_FAILED,
                COMPUTED,
                CONTRIBUTING,
                CONTRIBUTE_FAILED);
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

    /**
     * @deprecated called once from a scheduler method itself never used
     */
    @Deprecated(forRemoval = true)
    public static List<ReplicateStatus> getSuccessStatusesBeforeComputed() {
        return Arrays.asList(
                CREATED,
                STARTING,
                STARTED,
                APP_DOWNLOADING,
                APP_DOWNLOADED,
                DATA_DOWNLOADING,
                DATA_DOWNLOADED,
                COMPUTING);
    }

    public static List<ReplicateStatus> getUncompletableStatuses() {
        return List.of(
                CONTRIBUTE_FAILED,
                REVEAL_FAILED,
                CONTRIBUTE_AND_FINALIZE_FAILED
                //RESULT_UPLOAD_FAILED,         //still good if don't upload
        );
    }
}
