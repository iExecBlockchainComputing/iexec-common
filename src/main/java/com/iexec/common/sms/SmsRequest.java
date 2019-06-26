package com.iexec.common.sms;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsRequest {

    @JsonProperty("auth") private SmsRequestData smsSecretRequestData;
}