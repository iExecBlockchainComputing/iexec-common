package com.iexec.common.disconnection;


public enum RecoveryAction {

    NONE,           // subscribe & do nothing (wait)
    CONTRIBUTE,     // subscribe & contribute if result found, else compute
    REVEAL,         // subscribe & reveal
    UPLOAD_RESULT;  // subscribe & upload result
}