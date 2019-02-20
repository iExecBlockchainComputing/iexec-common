package com.iexec.common.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Signature {

    private String walletAddress;

    private byte signV;
    private byte[] signR;
    private byte[] signS;
}
