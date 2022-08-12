package com.iexec.common.chain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DealParamsTest {

    private static final String ARGS = "argument for the worker";
    private static final String FILE1 = "http://test.com/image1.png";
    private static final String FILE2 = "http://test.com/image2.png";
    private static final String FILE3 = "http://test.com/image3.png";

    @Test
    void shouldReadArgsWithoutJson() {
        DealParams params = DealParams.createFromString(ARGS);
        assertEquals(ARGS, params.getIexecArgs());
        assertThat(params.getIexecInputFiles()).isNotNull();
        assertThat(params.getIexecInputFiles()).isEmpty();
        assertThat(params.getIexecSecrets()).isNotNull();
        assertThat(params.getIexecSecrets()).isEmpty();
    }

    @Test
    void shouldReadArgsInJson() {
        DealParams params = DealParams.createFromString("{\"iexec_args\":\"" + ARGS + "\"}");
        assertThat(params.getIexecArgs()).isEqualTo(ARGS);
        assertThat(params.getIexecInputFiles()).isNotNull();
        assertThat(params.getIexecInputFiles()).isEmpty();
        assertThat(params.getIexecSecrets()).isNotNull();
        assertThat(params.getIexecSecrets()).isEmpty();
    }

    @Test
    void shouldReadArgsInJsonAndEmptyInputFilesAndEmptySecrets() {
        DealParams params = DealParams.createFromString("{\"iexec_args\":\"" + ARGS + "\"," +
                "\"iexec_input_files\":[],\"iexec_secrets\":{}}");
        assertThat(params.getIexecArgs()).isEqualTo(ARGS);
        assertThat(params.getIexecInputFiles()).isNotNull();
        assertThat(params.getIexecInputFiles()).isEmpty();
        assertThat(params.getIexecSecrets()).isNotNull();
        assertThat(params.getIexecSecrets()).isEmpty();
    }

    @Test
    void shouldReadNotCorrectJsonFile() {
        String wrongJson = "{\"wrong_field1\":\"wrong arg value\"," +
                "\"iexec_input_files\":[\"http://file1\"]}";
        DealParams params = DealParams.createFromString(wrongJson);
        assertThat(params.getIexecArgs()).isEqualTo(wrongJson);
        assertThat(params.getIexecInputFiles()).isNotNull();
        assertThat(params.getIexecInputFiles()).isEmpty();
    }

    @Test
    void testSerializationWithBuilderDefaultValues() {
        DealParams params = DealParams.builder().build();
        DealParams newParams =  DealParams.createFromString(params.toJsonString());
        assertThat(newParams)
                .usingRecursiveComparison()
                .ignoringFields("iexecInputFiles", "iexecSecrets")
                .isEqualTo(params);
        assertThat(params.getIexecInputFiles()).isNull();
        assertThat(newParams.getIexecInputFiles()).isEqualTo(Collections.emptyList());
        assertThat(params.getIexecSecrets()).isNull();
        assertThat(newParams.getIexecSecrets()).isEqualTo(Collections.emptyMap());
    }

    @Test
    void testSerializationWithBooleanAsFalse() {
        DealParams params = DealParams.builder()
                .iexecDeveloperLoggerEnabled(false)
                .iexecResultEncryption(false)
                .build();
        DealParams newParams = DealParams.createFromString(params.toJsonString());
        assertThat(newParams)
                .usingRecursiveComparison()
                .ignoringFields("iexecInputFiles", "iexecSecrets")
                .isEqualTo(params);
        assertThat(params.getIexecInputFiles()).isNull();
        assertThat(newParams.getIexecInputFiles()).isEqualTo(Collections.emptyList());
        assertThat(params.getIexecSecrets()).isNull();
        assertThat(newParams.getIexecSecrets()).isEqualTo(Collections.emptyMap());
    }

    @Test
    void testSerializationWithBooleanAsTrue() {
        DealParams params = DealParams.builder()
                .iexecDeveloperLoggerEnabled(true)
                .iexecResultEncryption(true)
                .build();
        DealParams newParams = DealParams.createFromString(params.toJsonString());
        assertThat(newParams)
                .usingRecursiveComparison()
                .ignoringFields("iexecInputFiles", "iexecSecrets")
                .isEqualTo(params);
        assertThat(params.getIexecInputFiles()).isNull();
        assertThat(newParams.getIexecInputFiles()).isEqualTo(Collections.emptyList());
        assertThat(params.getIexecSecrets()).isNull();
        assertThat(newParams.getIexecSecrets()).isEqualTo(Collections.emptyMap());
    }

    @Test
    void testSerializationForInputFiles() {
        DealParams params = DealParams.builder()
                .iexecInputFiles(List.of(FILE1, FILE2))
                .build();
        DealParams newParams = DealParams.createFromString(params.toJsonString());
        assertThat(params)
                .usingRecursiveComparison()
                .ignoringFields("iexecSecrets")
                .isEqualTo(newParams);
        assertThat(params.getIexecSecrets()).isNull();
        assertThat(newParams.getIexecSecrets()).isEmpty();
    }

    @Test
    void testSerializationForSecrets(){
        DealParams params = DealParams.builder()
                .iexecSecrets(Map.of("0", "secretA", "3", "secretX"))
                .build();
        DealParams newParams = DealParams.createFromString(params.toJsonString());
        assertThat(params)
                .usingRecursiveComparison()
                .ignoringFields("iexecInputFiles")
                .isEqualTo(newParams);
        assertThat(params.getIexecInputFiles()).isNull();
        assertThat(newParams.getIexecInputFiles()).isEmpty();
    }

    @Test
    void testSerializationWithEmptyArgs() {
        DealParams params = DealParams.builder()
                .iexecArgs("")
                .iexecInputFiles(Collections.emptyList())
                .iexecSecrets(Collections.emptyMap())
                .build();
        DealParams newParams = DealParams.createFromString(params.toJsonString());
        assertThat(params)
                .usingRecursiveComparison()
                .ignoringFields("iexecArgs")
                .isEqualTo(newParams);
        assertThat(params.getIexecArgs()).isNotNull();
        assertThat(params.getIexecArgs()).isBlank();
        assertThat(newParams.getIexecArgs()).isNull();
        assertThat(params.getIexecInputFiles()).isEmpty();
        assertThat(newParams.getIexecInputFiles()).isEmpty();
        assertThat(params.getIexecSecrets()).isEmpty();
        assertThat(newParams.getIexecSecrets()).isEmpty();
    }

    @Test
    void testSerializationWithArgsAndMultipleFilesAndMultipleSecrets() {
        DealParams params = DealParams.builder()
                .iexecArgs(ARGS)
                .iexecInputFiles(List.of(FILE2, FILE3))
                .iexecSecrets(Map.of("2", "secretK", "1", "secretD"))
                .build();
        DealParams newParams = DealParams.createFromString(params.toJsonString());
        assertThat(params).isEqualTo(newParams);
    }

    @ParameterizedTest
    @ValueSource(strings = {DealParams.DROPBOX_RESULT_STORAGE_PROVIDER, DealParams.IPFS_RESULT_STORAGE_PROVIDER})
    void testSerializationWithAllParams(String storageProvider) {
        DealParams params = DealParams.builder()
                .iexecArgs(ARGS)
                .iexecInputFiles(List.of(FILE3, FILE2, FILE1))
                .iexecSecrets(Map.of("1", "secretC", "2", "secretB", "3", "secretA"))
                .iexecDeveloperLoggerEnabled(true)
                .iexecResultEncryption(true)
                .iexecResultStorageProvider(storageProvider)
                .iexecResultStorageProxy("http://result-proxy.local:13200")
                .iexecSmsUrl("http://sms.url")
                .build();
        DealParams newParams = DealParams.createFromString(params.toJsonString());
        assertThat(newParams).isEqualTo(params);
        assertThat(newParams.getIexecArgs()).isNotBlank();
        assertThat(newParams.getIexecInputFiles()).isNotEmpty();
        assertThat(newParams.getIexecSecrets()).isNotEmpty();
        assertThat(newParams.getIexecSmsUrl()).isNotEmpty();
    }

}
