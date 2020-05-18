package com.iexec.common.utils;

import com.iexec.common.result.ComputedFile;
import org.junit.Assert;
import org.junit.Test;

public class IexecFileHelperTest {

    private static final String CHAIN_TASK_ID = "CHAIN_TASK_ID";

    @Test
    public void shouldReadComputedFile() {
        String computedFileDirPath = "src/test/resources/result/valid/";

        ComputedFile computedFile = IexecFileHelper.readComputedFile(CHAIN_TASK_ID, computedFileDirPath);
        Assert.assertEquals("/iexec_out/computing-trace.txt", computedFile.getDeterministicOutputPath());
    }

    @Test
    public void shouldReadComputedFileWithoutTrailingSlash() {
        String computedFileDirPath = "src/test/resources/result/valid";

        ComputedFile computedFile = IexecFileHelper.readComputedFile(CHAIN_TASK_ID, computedFileDirPath);
        Assert.assertEquals("/iexec_out/computing-trace.txt", computedFile.getDeterministicOutputPath());
    }

    @Test
    public void shouldNotReadComputedFileEmptyPath() {
        String computedFileDirPath = "";

        ComputedFile computedFile = IexecFileHelper.readComputedFile(CHAIN_TASK_ID, computedFileDirPath);
        Assert.assertNull(computedFile);
    }

    @Test
    public void shouldNotReadComputedFileSinceInvalidPath() {
        String computedFileDirPath = "/nowhere/nowhere/";

        ComputedFile computedFile = IexecFileHelper.readComputedFile(CHAIN_TASK_ID, computedFileDirPath);
        Assert.assertNull(computedFile);
    }

}