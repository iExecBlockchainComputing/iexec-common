package com.iexec.common.worker.result;

import com.iexec.common.result.ComputedFile;
import org.junit.Assert;
import org.junit.Test;

public class ResultUtilsTests {

    public static final String CHAIN_TASK_ID = "chainTaskId";

    @Test
    public void shouldComputeWeb3ResultDigest() {
        ComputedFile computedFile = ComputedFile.builder()
                .taskId(CHAIN_TASK_ID)
                .callbackData("0x0000000000000000000000000000000000000000000000000000000000000001")
                .build();

        String deterministHash = ResultUtils.computeWeb3ResultDigest(computedFile);
        Assert.assertEquals("0xb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf6", deterministHash);
    }

    @Test
    public void shouldComputeWeb2ResultDigest() {
        ComputedFile computedFile = ComputedFile.builder()
                .taskId(CHAIN_TASK_ID)
                .deterministicOutput("/iexec_out")
                .build();

        String deterministHash = ResultUtils.computeWeb2ResultDigest(computedFile,
                "src/test/resources/utils/file-helper/file-hash/output");

        Assert.assertEquals("0xcc77508549295dd5de5876a2f4f00d4c3c27a547c6403450e43ab4de191bf1bc", deterministHash);
    }

}