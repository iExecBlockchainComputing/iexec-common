package com.iexec.common.security;

import com.iexec.common.utils.BytesUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.util.Arrays;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Signature {

    private String walletAddress;
    private String signature;

    public Signature(String walletAddress, byte[] sign) {
        this.walletAddress = walletAddress;
        this.signature = BytesUtils.bytesToString(sign);
    }

    public Signature(String walletAddress, byte[] r, byte[] s, byte v) {
        this(walletAddress, Arrays.concatenate(r, s, new byte[]{v}));
    }

    public byte[] getR(){
        return Arrays.copyOfRange(BytesUtils.stringToBytes(signature), 0, 32);
    }

    public byte[] getS(){
        return Arrays.copyOfRange(BytesUtils.stringToBytes(signature), 32, 64);
    }

    public byte getV(){
        return BytesUtils.stringToBytes(signature)[64];
    }
}
