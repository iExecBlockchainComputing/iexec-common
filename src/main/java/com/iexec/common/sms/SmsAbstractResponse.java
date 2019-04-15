package com.iexec.common.sms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class SmsAbstractResponse {

    private boolean ok;
    private String errorMessage;
}