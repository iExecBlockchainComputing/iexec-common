package com.iexec.common.sdk.order.payload;

import com.iexec.common.chain.DealParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RequestOrderTest {

    @Test
    void shouldSerializeEmptyMapWhenNoParameters() {
        DealParams params = DealParams.builder().build();
        Assertions.assertEquals("{}", params.toJsonString());
    }

    @Test
    void shouldSerializeEmptyMapWhenBooleanParametersAreFalse() {
        DealParams params = DealParams.builder()
                .iexecResultEncryption(false)
                .iexecDeveloperLoggerEnabled(false)
                .build();
        Assertions.assertEquals("{}", params.toJsonString());
    }

}
