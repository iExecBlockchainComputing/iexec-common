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

package com.iexec.common.tee;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.iexec.common.utils.BytesUtils.toByte32HexString;

class TeeUtilsTests {

    @Test
    void areValidFields() {
        Assertions.assertEquals(0b0011, TeeUtils.TEE_SCONE_BITS);
        Assertions.assertEquals(0b0101, TeeUtils.TEE_GRAMINE_BITS);
        Assertions.assertEquals("0x0000000000000000000000000000000000000000000000000000000000000003",
                TeeUtils.TEE_SCONE_ONLY_TAG);
        Assertions.assertEquals("0x0000000000000000000000000000000000000000000000000000000000000005",
                TeeUtils.TEE_GRAMINE_ONLY_TAG);
    }

    @ParameterizedTest
    @ValueSource(ints = {
            0b0011, // 0x3: Scone
            0b0101, // : 0x5: Gramine
            0b11110011, // 0xf3: any (above TEE runtime framework mask) + Scone
    })
    void isTeeTag(int tag) {
        Assertions.assertTrue(TeeUtils.isTeeTag(toByte32HexString(tag)));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            0b0000, // 0x0: empty
            0b0001, // 0x1: missing TEE runtime framework
            0b1001, // 0x9: no third TEE runtime framework existing for now ({Scone, Gramine, ?})
    })
    void isNotTeeTag(int tag) {
        Assertions.assertFalse(TeeUtils.isTeeTag(toByte32HexString(tag)));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            0b0011, // 0x3: Scone
            0b11110011, // 0xf3: any (above TEE runtime framework mask) + Scone
    })
    void hasTeeSconeInTag(int tag) {
        Assertions.assertTrue(TeeUtils.hasTeeSconeInTag(toByte32HexString(tag)));
    }

    @Test
    void hasNotTeeSconeInTag() {
        Assertions.assertFalse(TeeUtils.hasTeeSconeInTag(toByte32HexString(0b0101))); // 0x5: Gramine
    }

    @ParameterizedTest
    @ValueSource(ints = {
            0b0101, // 0x5: Gramine
            0b11110101, // 0xf5: any (above TEE runtime framework mask) + Gramine
    })
    void hasTeeGramineInTag(int tag) {
        Assertions.assertTrue(TeeUtils.hasTeeGramineInTag(toByte32HexString(tag)));
    }

    @Test
    void hasNotTeeGramineInTag() {
        Assertions.assertFalse(TeeUtils.hasTeeGramineInTag(toByte32HexString(0b0011))); // 0x3: Scone
    }

    // ensures some bits are present within TEE runtime framework mask
    static Stream<Arguments> validData() {
        return Stream.of(
                Arguments.of(0b0001, 0b0001),       // exact match
                Arguments.of(0b0001, 0b11110001),   // contains
                // ...
                Arguments.of(0b1111, 0b1111),       // exact match
                Arguments.of(0b1111, 0b11111111)    // contains
        );
    }

    @ParameterizedTest
    @MethodSource("validData")
    void hasTeeRuntimeFrameworkBitsInTag(int expectedBits, int tag) {
        Assertions.assertTrue(TeeUtils.hasTeeRuntimeFrameworkBitsInTag(expectedBits,
                toByte32HexString(tag)));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "abc", // missing prefix
            "0x1", // no match
    })
    void hasNotTeeRuntimeFrameworkBitsInTag(String tag) {
        int anyBits = 0b1010;
        Assertions.assertFalse(TeeUtils.hasTeeRuntimeFrameworkBitsInTag(anyBits,
                tag));
    }
}