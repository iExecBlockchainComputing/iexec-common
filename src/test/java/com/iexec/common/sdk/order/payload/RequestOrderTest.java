package com.iexec.common.sdk.order.payload;

import com.iexec.common.chain.DealParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class RequestOrderTest {

    private static final String INPUT_FILE1 = "http://file1";
    private static final String INPUT_FILE2 = "http://file2";

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
    void shouldSerializeWhenBooleanParametersAreTrue() {
        String expectedData = "{\"iexec_developer_logger\":true,\"iexec_result_encryption\":true}";
        DealParams params = DealParams.builder()
                .iexecDeveloperLoggerEnabled(true)
                .iexecResultEncryption(true)
                .build();
        Assertions.assertEquals(expectedData, RequestOrder.toStringParams(params));
    }

    @ParameterizedTest
    @ValueSource(strings = {DealParams.DROPBOX_RESULT_STORAGE_PROVIDER, DealParams.IPFS_RESULT_STORAGE_PROVIDER})
    void shouldSerializeParametersWithEncryptedResult(String storageProvider) {
        String expectedData = "{\"iexec_result_encryption\":true";
        expectedData += ",\"iexec_result_storage_provider\":\"" + storageProvider + "\"}";
        DealParams params = DealParams.builder()
                .iexecResultEncryption(true)
                .iexecResultStorageProvider(storageProvider)
                .build();
        Assertions.assertEquals(expectedData, RequestOrder.toStringParams(params));
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
        String expectedData = "{\"iexec_input_files\":[\"" + INPUT_FILE1 + "\",\"" + INPUT_FILE2 + "\"]}";
        DealParams params = DealParams.builder()
                .iexecInputFiles(List.of(INPUT_FILE1, INPUT_FILE2))
                .build();
        Assertions.assertEquals(expectedData, RequestOrder.toStringParams(params));
    }

    @Test
    void shouldSerializeSecrets() {
        Map<String, String> secrets = Map.of("0", "secretA", "2", "secretD");
        String serializedSecrets = secrets.entrySet().stream()
                .map(entry -> "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"")
                .collect(Collectors.joining(","));
        String expectedData = "{\"iexec_secrets\":{" + serializedSecrets + "}}";
        DealParams params = DealParams.builder()
                .iexecSecrets(Map.of("0", "secretA", "2", "secretD"))
                .build();
        Assertions.assertEquals(expectedData, RequestOrder.toStringParams(params));
    }

    @Test
    void shouldSerializeAllParams() {
        Map<String, String> secrets = Map.of("1", "secretA", "0", "secretZ");
        String serializedSecrets = secrets.entrySet().stream()
                .map(entry -> "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"")
                .collect(Collectors.joining(","));
        String expectedData = "{";
        expectedData += "\"iexec_args\":\"arg1 arg2 arg3\",";
        expectedData += "\"iexec_input_files\":[\"" + INPUT_FILE2 + "\",\"" + INPUT_FILE1 + "\"],";
        expectedData += "\"iexec_developer_logger\":true,";
        expectedData += "\"iexec_result_encryption\":true,";
        expectedData += "\"iexec_result_storage_provider\":\"" + DealParams.IPFS_RESULT_STORAGE_PROVIDER + "\",";
        expectedData += "\"iexec_secrets\":{" + serializedSecrets + "}";
        expectedData += "}";
        DealParams params = DealParams.builder()
                .iexecArgs("arg1 arg2 arg3")
                .iexecInputFiles(List.of(INPUT_FILE2, INPUT_FILE1))
                .iexecSecrets(secrets)
                .iexecDeveloperLoggerEnabled(true)
                .iexecResultEncryption(true)
                .iexecResultStorageProvider(DealParams.IPFS_RESULT_STORAGE_PROVIDER)
                .build();
        Assertions.assertEquals(expectedData, RequestOrder.toStringParams(params));
    }

}
