package com.iexec.common.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class SmsSessionResponse extends SmsResponse {

    private SmsSession data;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class SmsSession {

        private String sessionId;
        private String outputFspf;
        private String beneficiaryKey;    
    }
}