package com.iexec.common.replicate;

public enum ReplicateStatusCause {
    
    DETERMINISM_HASH_NOT_FOUND,
    TEE_EXECUTION_NOT_VERIFIED,
    CHAIN_UNREACHABLE,
    STAKE_TOO_LOW,
    TASK_NOT_ACTIVE,
    AFTER_DEADLINE,
    CONTRIBUTION_ALREADY_SET,
    
}
