package com.iexec.common.security;

import com.iexec.common.utils.BytesUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.util.Arrays;
import org.web3j.crypto.Sign;

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

    public Signature(String walletAddress, Sign.SignatureData sign) {
        this(walletAddress, sign.getR(), sign.getS(), sign.getV());
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
