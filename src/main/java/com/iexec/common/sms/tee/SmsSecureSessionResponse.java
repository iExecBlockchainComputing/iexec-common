package com.iexec.common.sms.tee;

import com.iexec.common.sms.SmsAbstractResponse;

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
public class SmsSecureSessionResponse extends SmsAbstractResponse {

    private SmsSecureSession data;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class SmsSecureSession {

        private String sessionId;
        private String outputFspf;
        private String beneficiaryKey;    
    }
}