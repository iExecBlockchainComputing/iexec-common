package com.iexec.common.disconnection;


public enum RecoveryAction {

    WAIT,                           // subscribe & do nothing (wait)
    CONTRIBUTE,                     // subscribe & contribute if result found, else compute
    ABORT_CONSENSUS_REACHED,        // abort + unsubscribe
    ABORT_CONTRIBUTION_TIMEOUT,     // abort + unsubscribe
    REVEAL,                         // subscribe & reveal
    UPLOAD_RESULT,                  // subscribe & upload result
    COMPLETE,                       // complete + unsubscribe
}