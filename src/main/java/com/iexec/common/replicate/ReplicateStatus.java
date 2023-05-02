/*
 * Copyright 2020-2023 IEXEC BLOCKCHAIN TECH
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
    RESULT_UPLOAD_REQUEST_FAILED,
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

    public static boolean isFailure(ReplicateStatus status) {
        return getFailureStatuses().contains(status);
    }

    public static boolean isFailedBeforeComputed(ReplicateStatus status) {
        return status.ordinal() < COMPUTED.ordinal() && getFailureStatuses().contains(status);
    }

    public static boolean isCompletable(ReplicateStatus status) {
        return getCompletableStatuses().contains(status);
    }

    public static boolean isFailable(ReplicateStatus status) {
        return getFailableStatuses().contains(status);
    }

    public static boolean isAbortable(ReplicateStatus status) {
        return getAbortableStatuses().contains(status);
    }

    public static boolean isRecoverable(ReplicateStatus status) {
        return true;  // just temporary
    }

    public static List<ReplicateStatus> getSuccessStatuses() {
        return new ArrayList<>(Arrays.asList(
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
                COMPLETED));
    }

    public static List<ReplicateStatus> getFailureStatuses() {
        return Arrays.asList(
                START_FAILED,
                APP_DOWNLOAD_FAILED,
                DATA_DOWNLOAD_FAILED,
                COMPUTE_FAILED,
                CONTRIBUTE_FAILED,
                REVEAL_FAILED,
                RESULT_UPLOAD_REQUEST_FAILED,
                RESULT_UPLOAD_FAILED,
                CONTRIBUTE_AND_FINALIZE_FAILED,
                COMPLETE_FAILED,
                FAILED);
    }

    /*
     * Statuses that should be updated
     * to COMPLETED at the end of a task. 
     */
    public static List<ReplicateStatus> getCompletableStatuses() {
        return Arrays.asList(
                REVEALED,
                RESULT_UPLOAD_REQUESTED,
                RESULT_UPLOAD_REQUEST_FAILED,
                RESULT_UPLOADING,
                RESULT_UPLOAD_FAILED,
                RESULT_UPLOADED,
                CONTRIBUTE_AND_FINALIZE_ONGOING,
                CONTRIBUTE_AND_FINALIZE_DONE,
                COMPLETING,
                COMPLETE_FAILED);
    }

    /*
     * Statuses that should be updated
     * to FAILED at the end of a task. 
     */
    public static List<ReplicateStatus> getFailableStatuses() {
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
                CONTRIBUTE_FAILED,
                CONTRIBUTED,
                REVEALING,
                REVEAL_FAILED,
                CONTRIBUTE_AND_FINALIZE_ONGOING,
                CONTRIBUTE_AND_FINALIZE_FAILED,
                ABORTED);
    }

    /*
     * Statuses that can be updated
     * to ABORTED by the worker. 
     */
    public static List<ReplicateStatus> getAbortableStatuses() {
        List<ReplicateStatus> nonFinal = new ArrayList<>(getNonFinalWorkflowStatuses());
        nonFinal.add(WORKER_LOST);
        return nonFinal;
    }

    /*
     * Statuses that can be recovered by the worker 
     */
    public static List<ReplicateStatus> getRecoverableStatuses() {
        return Arrays.asList(
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
                RESULT_UPLOAD_REQUEST_FAILED,   // can complete later
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
        List<ReplicateStatus> nonFinal = new ArrayList<>(getNonFinalWorkflowStatuses());
        nonFinal.add(COMPLETED);
        nonFinal.add(FAILED);
        return nonFinal;
    }

    public static List<ReplicateStatus> getFinalStatuses() {
        return Arrays.asList(
                COMPLETED,
                FAILED);
    }

    /*
     * Non final statuses of the default workflow.
     * CREATED -> COMPLETING/COMPLETE_FAILED
     */
    public static List<ReplicateStatus> getNonFinalWorkflowStatuses() {
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
                CONTRIBUTE_FAILED,
                CONTRIBUTED,
                REVEALING,
                REVEAL_FAILED,
                REVEALED,
                RESULT_UPLOAD_REQUESTED,
                RESULT_UPLOAD_REQUEST_FAILED,
                RESULT_UPLOADING,
                RESULT_UPLOAD_FAILED,
                RESULT_UPLOADED,
                CONTRIBUTE_AND_FINALIZE_ONGOING,
                CONTRIBUTE_AND_FINALIZE_FAILED,
                CONTRIBUTE_AND_FINALIZE_DONE,
                COMPLETING,
                COMPLETE_FAILED);
    }

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
        return Arrays.asList(
                CONTRIBUTE_FAILED,
                REVEAL_FAILED,
                CONTRIBUTE_AND_FINALIZE_FAILED
                //RESULT_UPLOAD_REQUEST_FAILED, // still good if don't upload
                //RESULT_UPLOAD_FAILED,         //still good if don't upload
        );
    }
}
