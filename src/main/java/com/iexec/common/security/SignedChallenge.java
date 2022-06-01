/*
 * Copyright 2022 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.security;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignedChallenge {
    String challengeHash;
    String challengeSignature;
    String walletAddress;

    public static SignedChallenge createFromString(String signedChallengeString) {
        if (signedChallengeString == null || signedChallengeString.split("_").length != 3) {
            return null;
        }

        String[] parts = signedChallengeString.split("_");
        return SignedChallenge.builder()
                .challengeHash(parts[0])
                .challengeSignature(parts[1])
                .walletAddress(parts[2])
                .build();
    }

}
