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
@EqualsAndHashCode(callSuper=false)
public class SmsSessionResponse extends SmsResponseData {

    private String sessionId;
    private String outputFspf;
    private String beneficiaryKey;
}