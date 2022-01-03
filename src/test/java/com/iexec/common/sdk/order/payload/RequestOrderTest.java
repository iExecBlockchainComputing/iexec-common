package com.iexec.common.sdk.order.payload;

import com.iexec.common.chain.DealParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class RequestOrderTest {

    @Test
    void shouldSerializeEmptyMapWhenNoParameters() {
        DealParams params = DealParams.builder().build();
        Assertions.assertEquals("{}", RequestOrder.toStringParams(params));
    }

    @Test
    void shouldSerializeEmptyMapWhenBooleanParametersAreFalse() {
        DealParams params = DealParams.builder()
                .iexecResultEncryption(false)
                .iexecDeveloperLoggerEnabled(false)
                .build();
        Assertions.assertEquals("{}", RequestOrder.toStringParams(params));
    }

    @Test
    void shouldSerializeParametersWithEncryptedResult() {
        for (String storageProxy : List.of(DealParams.DROPBOX_RESULT_STORAGE_PROVIDER, DealParams.IPFS_RESULT_STORAGE_PROVIDER)) {
            String expectedData = "{\"iexec_result_encryption\":true";
            expectedData += ",\"iexec_result_storage_proxy\":\"" + storageProxy + "\"}";
            DealParams params = DealParams.builder()
                    .iexecResultEncryption(true)
                    .iexecResultStorageProxy(storageProxy)
                    .build();
            Assertions.assertEquals(expectedData, RequestOrder.toStringParams(params));
        }
    }

    @Test
    void shouldSerializeArgs() {
        String expectedData = "{\"iexec_args\":\"arg1 arg2 arg3\"}";
        DealParams params = DealParams.builder()
                .iexecArgs("arg1 arg2 arg3")
                .build();
        Assertions.assertEquals(expectedData, RequestOrder.toStringParams(params));
    }

    @Test
    void shouldSerializeInputFiles() {
        String expectedData = "{\"iexec_input_files\":[\"file1\",\"file2\"]}";
        DealParams params = DealParams.builder()
                .iexecInputFiles(List.of("file1", "file2"))
                .build();
        Assertions.assertEquals(expectedData, RequestOrder.toStringParams(params));
    }

    @Test
    void shouldSerializeSecrets() {
        String expectedData = "{\"iexec_secrets\":{\"0\":\"0\"}}";
        DealParams params = DealParams.builder()
                .iexecSecrets(Map.of("0", "0"))
                .build();
        Assertions.assertEquals(expectedData, RequestOrder.toStringParams(params));
    }

}
