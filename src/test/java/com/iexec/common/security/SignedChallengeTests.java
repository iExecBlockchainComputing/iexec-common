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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class SignedChallengeTests {

    @Test
    void shouldCreateSignedChallenge() {
        SignedChallenge signedChallenge = SignedChallenge.createFromString("challengeHash_challengeSignature_walletAddress");
        assertThat(signedChallenge.getChallengeHash()).isEqualTo("challengeHash");
        assertThat(signedChallenge.getChallengeSignature()).isEqualTo("challengeSignature");
        assertThat(signedChallenge.getWalletAddress()).isEqualTo("walletAddress");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "empty", "part1_part2", "part1_part2_part3_part4"})
    void shouldNotCreateSignedChallengeFromMalformedString(String authorization) {
        assertThat(SignedChallenge.createFromString(authorization)).isNull();
    }

}
