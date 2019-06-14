package com.iexec.common.sms.scone;

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
public class SconeSecureSessionResponse extends SmsAbstractResponse {

    private SconeSecureSession data;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class SconeSecureSession {

        private String sessionId;
        private String sconeVolumeFspf;
        private String beneficiaryKey;    
    }
}