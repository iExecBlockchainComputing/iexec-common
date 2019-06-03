package com.iexec.common.notification;

public enum TaskNotificationType {

    PLEASE_WAIT,                        // subscribe & do nothing (wait)
    PLEASE_CONTRIBUTE,                  // subscribe & contribute if result found, else compute
    PLEASE_REVEAL,                      // subscribe & reveal
    PLEASE_UPLOAD,                      // subscribe & upload result
    PLEASE_ABORT_CONSENSUS_REACHED,     // abort + unsubscribe
    PLEASE_ABORT_CONTRIBUTION_TIMEOUT,  // abort + unsubscribe
    PLEASE_COMPLETE                           // complete + unsubscribe

}
