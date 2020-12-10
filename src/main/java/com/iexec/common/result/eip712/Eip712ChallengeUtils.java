/*
 * Copyright 2020 IEXEC BLOCKCHAIN TECH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iexec.common.result.eip712;

import com.iexec.common.chain.eip712.EIP712Domain;
import com.iexec.common.utils.HashUtils;
import com.iexec.common.utils.SignatureUtils;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class Eip712ChallengeUtils {

    private Eip712ChallengeUtils() {
        throw new UnsupportedOperationException();
    }

    public static String buildAuthorizationToken(Eip712Challenge eip712Challenge, String walletAddress, ECKeyPair ecKeyPair) {
        try {
            String challengeString = getEip712ChallengeString(eip712Challenge);
            String signatureString = SignatureUtils.signAsString(challengeString, ecKeyPair);
            return String.join("_", challengeString, signatureString, walletAddress);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getEip712ChallengeString(Eip712Challenge eip712Challenge) {
        String domainSeparator = getDomainSeparator(eip712Challenge);
        String messageHash = getMessageHash(eip712Challenge);
        /*
        REMINDER : Arrays.concatenate(byteArray) similar to Numeric.hexStringToByteArray("0x1901")
        abi.encode should take bytes32 (padding could be required) while abi.encodePacked takes 0x12+1a2b
        System.out.println(Numeric.toHexString(Arrays.concatenate(Numeric.hexStringToByteArray("1901"),
                Numeric.hexStringToByteArray(domainSeparator) ,
                Numeric.hexStringToByteArray(messageHash))).equals(Numeric.toHexString(Numeric.hexStringToByteArray(
                "0x1901" + Numeric.cleanHexPrefix(domainSeparator) + Numeric.cleanHexPrefix(messageHash)))));*/

        return HashUtils.concatenateAndHash("0x1901", domainSeparator, messageHash);
    }

    static String getDomainSeparator(Eip712Challenge eip712Challenge) {
        EIP712Domain domain = eip712Challenge.getDomain();

        String domainTypesParams = Types.typeParamsToString(eip712Challenge.getTypes().getDomainTypeParams());
        String domainType = "EIP712Domain(" + domainTypesParams + ")";//EIP712Domain(string name,string version,uint256 chainId)
        String domainTypeHash = Numeric.toHexString(Hash.sha3(domainType.getBytes()));
        String appNameDomainSeparator = Numeric.toHexString(Hash.sha3(domain.getName().getBytes()));
        String versionDomainSeparator = Numeric.toHexString(Hash.sha3(domain.getVersion().getBytes()));
        String chainIdDomainSeparator = Numeric.toHexString(Numeric.toBytesPadded(BigInteger.valueOf(domain.getChainId()), 32));

        return HashUtils.concatenateAndHash(domainTypeHash,
                appNameDomainSeparator,
                versionDomainSeparator,
                chainIdDomainSeparator);
    }

    static String getMessageHash(Eip712Challenge eip712Challenge) {
        Message message = eip712Challenge.getMessage();

        String messageTypesParams = Types.typeParamsToString(eip712Challenge.getTypes().getChallengeTypeParams());
        String messageType = eip712Challenge.getPrimaryType() + "(" + messageTypesParams + ")";//Challenge(string challenge)
        String messageTypeHash = Numeric.toHexString(Hash.sha3(messageType.getBytes()));
        String challengeMessage = Numeric.toHexString(Hash.sha3(message.getChallenge().getBytes()));

        return HashUtils.concatenateAndHash(messageTypeHash, challengeMessage);
    }
}
