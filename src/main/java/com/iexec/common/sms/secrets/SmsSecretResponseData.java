package com.iexec.common.sms.secrets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSecretResponseData {

    private Object params;
    private TaskSecrets secrets;
}