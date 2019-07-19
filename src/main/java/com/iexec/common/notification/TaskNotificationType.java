package com.iexec.common.notification;

public enum TaskNotificationType {

    PLEASE_CONTINUE,
    PLEASE_WAIT,                        // subscribe & do nothing (wait)
    PLEASE_START,
    PLEASE_DOWNLOAD_APP,
    PLEASE_DOWNLOAD_DATA,
    PLEASE_COMPUTE,
    PLEASE_CONTRIBUTE,                  // subscribe & contribute if result found, else compute
    PLEASE_REVEAL,                      // subscribe & reveal
    PLEASE_UPLOAD,                      // subscribe & upload result
    PLEASE_ABORT_CONSENSUS_REACHED,     // abort + unsubscribe
    PLEASE_ABORT_CONTRIBUTION_TIMEOUT,  // abort + unsubscribe
    PLEASE_COMPLETE,                           // complete + unsubscribe
    PLEASE_ABORT

}
