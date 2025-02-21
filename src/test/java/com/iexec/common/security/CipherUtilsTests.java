/*
 * Copyright 2021-2025 IEXEC BLOCKCHAIN TECH
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

import com.iexec.common.utils.FileHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CipherUtilsTests {

    private static final String DIR = "src/test/resources/security/";
    // AES
    private static final String AES = DIR + "aes/";
    private static final String AES_KEY_FILE = AES + "key.txt";
    private static final String AES_PLAIN_DATA_FILE = AES + "plain-data.txt";
    private static final String AES_ENC_DATA_FILE = AES + "encrypted-data.bin";
    // RSA
    private static final String RSA = DIR + "rsa/";
    private static final String RSA_PRIV_KEY_FILE = RSA + "key";
    private static final String RSA_PUB_KEY_FILE = RSA + "key.pub";
    private static final String RSA_PLAIN_DATA_FILE = RSA + "plain-data.txt";
    private static final String RSA_ENC_DATA_FILE = RSA + "encrypted-data.bin";

    // ###############
    // #     AES     #
    // ###############

    @Test
    void shouldGenerate256AesKey() {
        final byte[] aesKey = CipherUtils.generateAesKey();
        assertThat(aesKey).hasSize(32);
    }

    @Test
    void shouldGenerateDifferent256AesKey() {
        final byte[] aesKey1 = CipherUtils.generateAesKey();
        final byte[] aesKey2 = CipherUtils.generateAesKey();
        assertThat(aesKey1).isNotEqualTo(aesKey2);
    }

    @Test
    void shouldGenerateAesKeyWithGivenSize() {
        final byte[] aesKey = CipherUtils.generateAesKey(128);
        assertThat(aesKey).hasSize(16);
    }

    @Test
    void shouldGenerateIvWithSize16bytes() {
        final byte[] iv = CipherUtils.generateIv();
        assertThat(iv).hasSize(16);
    }

    @Test
    void shouldGenerateDifferentIvs() {
        final byte[] iv1 = CipherUtils.generateIv();
        final byte[] iv2 = CipherUtils.generateIv();
        assertThat(iv1).isNotEqualTo(iv2);
    }

    @Test
    void shouldGet16BytesIvFromEncryptedData() {
        final byte[] encryptedData = FileHelper.readAllBytes(AES_ENC_DATA_FILE);
        assertThat(CipherUtils.getIvFromEncryptedData(encryptedData)).hasSize(16);
    }

    @Test
    void shouldStripIvFromEncryptedData() {
        final byte[] encryptedData = FileHelper.readAllBytes(AES_ENC_DATA_FILE);
        final byte[] strippedData = CipherUtils.stripIvFromEncryptedData(encryptedData);
        assertThat(encryptedData).hasSize(strippedData.length + 16);
    }

    @Test
    void shouldGenerateIvWithGivenSize() {
        final byte[] iv = CipherUtils.generateIv(32);
        assertThat(iv).hasSize(32);
    }

    // encryption

    @Test
    void shouldEncryptAndDecryptDataWithAesKey() throws Exception {
        final byte[] plainData = FileHelper.readAllBytes(AES_PLAIN_DATA_FILE);
        final byte[] base64Key = FileHelper.readAllBytes(AES_KEY_FILE);
        final byte[] decodeKey = Base64.getDecoder().decode(base64Key);
        final byte[] encryptedData = CipherUtils.aesEncrypt(plainData, decodeKey);
        final byte[] decryptedData = CipherUtils.aesDecrypt(encryptedData, decodeKey);
        assertThat(decryptedData).isEqualTo(plainData);
    }

    @Test
    void shouldEncryptDataWithDifferentIvs() throws Exception {
        final byte[] plainData = FileHelper.readAllBytes(AES_PLAIN_DATA_FILE);
        final byte[] base64Key = FileHelper.readAllBytes(AES_KEY_FILE);
        final byte[] decodeKey = Base64.getDecoder().decode(base64Key);
        final byte[] encryptedData1 = CipherUtils.aesEncrypt(plainData, decodeKey);
        final byte[] iv1 = CipherUtils.getIvFromEncryptedData(encryptedData1);
        final byte[] encryptedData2 = CipherUtils.aesEncrypt(plainData, decodeKey);
        final byte[] iv2 = CipherUtils.getIvFromEncryptedData(encryptedData2);
        assertThat(encryptedData1).isNotEqualTo(encryptedData2);
        assertThat(iv1).isNotEqualTo(iv2);
    }

    // decryption

    @Test
    void shouldDecryptDataEncryptedWithSdk() throws Exception {
        final byte[] plainData = FileHelper.readAllBytes(AES_PLAIN_DATA_FILE);
        final byte[] base64Key = FileHelper.readAllBytes(AES_KEY_FILE);
        final byte[] decodeKey = Base64.getDecoder().decode(base64Key);
        final byte[] sdkEncryptedData = FileHelper.readAllBytes(AES_ENC_DATA_FILE);
        final byte[] decryptedData = CipherUtils.aesDecrypt(sdkEncryptedData, decodeKey);
        assertThat(decryptedData).isEqualTo(plainData);
    }

    @Test
    void shouldNotDecryptDataWithBadKey() {
        final byte[] badKey = Base64.getEncoder().encode(CipherUtils.generateAesKey());
        final byte[] encryptedData = FileHelper.readAllBytes(AES_ENC_DATA_FILE);
        Assertions.assertThrows(
                GeneralSecurityException.class,
                () -> CipherUtils.aesDecrypt(encryptedData, badKey));
    }

    @Test
    void shouldNotDecryptDataWithBadIv() throws Exception {
        final byte[] plainData = FileHelper.readAllBytes(AES_PLAIN_DATA_FILE);
        final byte[] base64Key = FileHelper.readAllBytes(AES_KEY_FILE);
        final byte[] decodeKey = Base64.getDecoder().decode(base64Key);
        final byte[] encryptedData = FileHelper.readAllBytes(AES_ENC_DATA_FILE);
        final byte[] strippedData = CipherUtils.stripIvFromEncryptedData(encryptedData);
        final byte[] badIv = CipherUtils.generateIv();
        final byte[] encryptedDataWithBadIv =
                CipherUtils.prependIvToEncryptedData(badIv, strippedData);
        final byte[] badDecryptedData =
                CipherUtils.aesDecrypt(encryptedDataWithBadIv, decodeKey);
        assertThat(badDecryptedData).isNotEqualTo(plainData);
    }

    // ###############
    // #     RSA     #
    // ###############

    @Test
    void shouldGenerateRsaKeyPair() {
        assertThat(CipherUtils.generateRsaKeyPair()).isNotEmpty();
    }

    @Test
    void shouldEncryptDataWithRsa() throws Exception {
        final byte[] plainData = FileHelper.readAllBytes(RSA_PLAIN_DATA_FILE);
        final Optional<KeyPair> keyPair =
                CipherUtils.readRsaKeyPair(RSA_PUB_KEY_FILE, RSA_PRIV_KEY_FILE);
        final byte[] encryptedData =
                CipherUtils.rsaEncrypt(plainData, keyPair.get().getPublic());
        final byte[] decryptedData =
                CipherUtils.rsaDecrypt(encryptedData, keyPair.get().getPrivate());
        assertThat(decryptedData).isEqualTo(plainData);
    }

    @Test
    void shouldDecryptDataWithRsa() throws Exception {
        final byte[] plainData = FileHelper.readAllBytes(RSA_PLAIN_DATA_FILE);
        final byte[] encryptedData = FileHelper.readAllBytes(RSA_ENC_DATA_FILE);
        final Optional<KeyPair> keyPair =
                CipherUtils.readRsaKeyPair(RSA_PUB_KEY_FILE, RSA_PRIV_KEY_FILE);
        final byte[] decryptedData =
                CipherUtils.rsaDecrypt(encryptedData, keyPair.get().getPrivate());
        assertThat(decryptedData).isEqualTo(plainData);
    }

    @Test
    void shouldDecodeBase64RsaPublicKeyToRsaPublicKey() {
        final String base64RsaPub = FileHelper.readFile(RSA_PUB_KEY_FILE);
        final Optional<PublicKey> publicKey = CipherUtils.base64ToRsaPublicKey(base64RsaPub);
        byte[] expectedKey = Base64.getDecoder().decode(
                "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAvrUmRuLWu/2o7r/1Iohq" +
                        "zFBSPOwOLXVRhf8AQ8CrfgedZpMKwxnYI8PbZwOhXJH36KfMTrxQV4whXejVj67C" +
                        "1Z2ALf0Op/8uyJcrnNXTaxaVf4sV4Epty0hq3KHknSBtq0R8CSWR1xR8DciGXIhh" +
                        "NIeVFZk6NKous6vNKzqfBl2V1Z+G2yxHB/3bTM2Z521x19FiYIdROuMYpDTgUyej" +
                        "YNYxVNBfURZaGphOKQja8XZEnITuoKhYZYrW55XnUc94t8L8+o83Vf49OhsRJA+e" +
                        "OH8ARdhd7utsYp9PsrJ4lQ7wsypXs5ciCD7OW8cs/lQDbOGDyOeYLoJNyJVCYHQk" +
                        "ITxA9nia4iOb67ZECuRJBVM5hV+xPsRDEvRDFvJEp4es0n8ID7/9n+xFE6VIHZrn" +
                        "gQE+ap4VmwBO1kwx+dadcoJSHuHrSaWPjEEFtGDM6dNO21MgM2Vsx3qHWiwcdlUs" +
                        "27boG0hrNZxwh6R7GZbR410qcuit9ML5GVRCD0hSiwiE42roOZFEurOJclt+yFW/" +
                        "AopWqmbI/blcguDvNiOmKE7B4Y2qOlH/fkHYmsuh008UQOVTqzXmAmi9j73bz9fy" +
                        "n7ToKqZmA+a7dKJXQ7e5s6oEGx77ZW436xJ1x1862BUUX14gK9j2O5sSDlO0Zu09" +
                        "GbEAHfQToq290HPCEy0ruc0CAwEAAQ==");
        assertThat(publicKey.get().getEncoded()).isEqualTo(expectedKey);
    }

    @Test
    void shouldDecodeBase64RsaPrivateKeyToRsaPrivateKey() {
        final String base64RsaPriv = FileHelper.readFile(RSA_PRIV_KEY_FILE);
        final Optional<PrivateKey> privateKey =
                CipherUtils.base64ToRsaPrivateKey(base64RsaPriv);
        final byte[] expectedKey = Base64.getDecoder().decode(
                "MIIJRQIBADANBgkqhkiG9w0BAQEFAASCCS8wggkrAgEAAoICAQC+tSZG4ta7/aju" +
                        "v/UiiGrMUFI87A4tdVGF/wBDwKt+B51mkwrDGdgjw9tnA6Fckffop8xOvFBXjCFd" +
                        "6NWPrsLVnYAt/Q6n/y7Ilyuc1dNrFpV/ixXgSm3LSGrcoeSdIG2rRHwJJZHXFHwN" +
                        "yIZciGE0h5UVmTo0qi6zq80rOp8GXZXVn4bbLEcH/dtMzZnnbXHX0WJgh1E64xik" +
                        "NOBTJ6Ng1jFU0F9RFloamE4pCNrxdkSchO6gqFhlitbnledRz3i3wvz6jzdV/j06" +
                        "GxEkD544fwBF2F3u62xin0+ysniVDvCzKlezlyIIPs5bxyz+VANs4YPI55gugk3I" +
                        "lUJgdCQhPED2eJriI5vrtkQK5EkFUzmFX7E+xEMS9EMW8kSnh6zSfwgPv/2f7EUT" +
                        "pUgdmueBAT5qnhWbAE7WTDH51p1yglIe4etJpY+MQQW0YMzp007bUyAzZWzHeoda" +
                        "LBx2VSzbtugbSGs1nHCHpHsZltHjXSpy6K30wvkZVEIPSFKLCITjaug5kUS6s4ly" +
                        "W37IVb8CilaqZsj9uVyC4O82I6YoTsHhjao6Uf9+Qdiay6HTTxRA5VOrNeYCaL2P" +
                        "vdvP1/KftOgqpmYD5rt0oldDt7mzqgQbHvtlbjfrEnXHXzrYFRRfXiAr2PY7mxIO" +
                        "U7Rm7T0ZsQAd9BOirb3Qc8ITLSu5zQIDAQABAoICAQCLbo6pzTgLAo784EQuF2de" +
                        "MmuuNzi2a8xLGAHth3TbFF40nNWFh+PLYmuLic/0ipSi+ewatPxYxg+vRYi/IJs6" +
                        "64jIFvkcQyrFZiFw7bVB2qU7N0mrTz+vHSdkYMlxSEBmtA+r8FgM1OFOgooaJWxM" +
                        "p8SMohc4YiT7IGVFcfrLAKmIIrbKkDju0t//62LXHeHaVTCEOutdDqT5id9pbNW6" +
                        "+1/eDuthseH3B9w9jEfnuvy3I0oFFJBszXIKqEMUQYiVcm7cEtH2gYfqb9e8cd7Q" +
                        "l6pvZp5nDKGWCbyPfoVDFkVN+Wtd1uX++UyPNbTjzjEi7k8YGJvfhU2xlc+ODdxL" +
                        "XPwRUCWJFKJMfaYjtvDoVNnqHd4lLVdmIEFv4ZC4mDVjmAMlSyfl/Tw1pofqNyQH" +
                        "ee90QVWhJn+trGtFq1c3kAmdSiXVB1HxWST9o2ynR49AFg7e+6gZ0H0UAMpBtR5Y" +
                        "B2Sd1KlKGftYgf4felPATKzWhPL7YAMpDkqe4bEuPJapnRYWGY56IrHyeXbY+wLc" +
                        "5PQNimBYX79qgwqjwcKm3qoGN2XZGuKaw5c32WGJ7l6csdeO2Jf0fO/+XiFqJ98A" +
                        "DbAMGN2fygqZNPClPPvwE+tFfLiNA3wmWnp6bRcPa7rH9ut/hS8mVh8sjyeP0CR3" +
                        "oZLapmX4RLaFOpQfjZMS0QKCAQEA53WJZx/q6aEiIeFzlO5oiGBWD8HQiudHcb4E" +
                        "EafA1AY7kHH/Dl/uhQn94l+33lO0xsr5IB2r5vDCPFqqqT0BgZvxrrpLoJMLUTjl" +
                        "6vHzag6Vpg4sjztKaJf3l92XqntgkTUGCkK6qItB97ufAyLtN4PhL8CRF7gYlUsy" +
                        "bVCFvdgx1Mi+4eRcFuVwr/fTBrIaVsyugWXyUUbtOA+lMxBH3gCgBqJWkEJVVhgk" +
                        "4MusgHL27XLZJbAXA4Yynn0Fx/5cQ+kKzPznMZYBihSIr+36LzE+L4zkANexNJa4" +
                        "1dlxXaX5JRGzZU/Tgdl9xB6tG4/RAc7PEKoMO8QhGxWvIdCCMwKCAQEA0u2ABeHW" +
                        "fw2b6SCcwPUa+iaztIEppes3f3zNcv/LG3AS6t/Z83lmPH+71w1ScaO58iNbAX7K" +
                        "mOHZcQI7X74oboePVexrAQnK7VRz+Xclk0CdsfLR40xoZsLua079yTBlTA56o927" +
                        "aSYpamC2j+eqV8JKl02ZQDHChyrEFcxm4eOaznqsKQP8TNz++v90aXcprqaWB7g2" +
                        "rot0GC/yqn96EU716QPpevBw19LdIn/3lAzJ6ZRwGV0bj2u/RFfBvgM5LgUprMeA" +
                        "z1WEMZ4qEGbkST8/dFNKlvp91TusNlNsC1ekH/LjpMnXhMsL9uPtSAbJJ38eKAfS" +
                        "3gg/2XI6yi7T/wKCAQEAkRZLWBpYzbRUyfoh0pSTcBE7QUtTpw6M6U6cDFkkdYOm" +
                        "qMfcgq09vaViwRkRD8tWwVoQScJvtCbtCpom6kk0fYM8PzPGHlSuPm61KM4bsDqO" +
                        "MfYmlm4rGV9RM6AS5ynJgZxEOgBUMzx/0IOKjJPDMQ55BM7n5H9g4Yyugnl8LGGt" +
                        "8t0XUlAsLaoLNjLZ1BOmkQxiwvgdqjcUkhS0kEq/UfkAVshCDNGX3ozp6QOjES1/" +
                        "fZ6FYat88ZeYeWKTUicuvN8DBzXs0ldehaiTefxp3FU4zuO5NJIAIZ/tIxfXiUV1" +
                        "5HexBWvBgF7OCDbWejVvZXu2rpJ5cqlhuzGfg0nJowKCAQEAuf283vL2ThB1A4vD" +
                        "TY6UHDKWUbt3Oxv/UwZ8r0QJGAeqVrvPFxdcZEqKGyZfJV+m7nHopJfmFAtKzpCW" +
                        "RGWMh3I3nR+cd0zoSIAox7gdRQw8QZaJJzHP+ZU34R3FQWvQVtGJqQczY5PH/0qK" +
                        "kLhKB5qGZYaCdQ2rp765KzOTIOqvLhUTzBL8ndZdbHxnbTwrYBr6vpHcferwEwfs" +
                        "phVEURDcXH0bm1F7X4RLhElsyXrBbJt8gZENZVkwThZH+8Ih4Iei6Pz+g1S3/Xyn" +
                        "QNFJyaDg1jU/14PIA4fb4geLhTDhw15NB5kH1suooCr5p4J8S7yWndvQQALsMlS8" +
                        "rF3AhwKCAQEAwIar6VI7vCQa3ZIL+rxCawdktu83076/jbxDlqvTw1ovlyEZPp06" +
                        "aFrs+PZOuMHKmgp95RypQZrMw1SprNGTsFQ//1X5mCDCaOyWj3QJdLFxfaajHFbN" +
                        "FX6O6hDD0Ca9sTq58uURg2xnC1eBrL5LQncP8ZayUE/MHFhQQvHJnI/wYzERBEeU" +
                        "oVYOdWP6w7EG4I0e5EAuIVQ8r1qNCng7FBicoOz+CjdeiZxRN1IQTLYqwXNeXJnQ" +
                        "tU1e/ek3TzDAG4LCCssalbK+rZP6i4Kc9LGTf5zPSMn9z7HO+wvxAq+uiK6sLs8K" +
                        "YMkAIn3ZMg2hjJ0Flq0/a9yvPlva8HZZxg==");
        assertThat(privateKey.get().getEncoded()).isEqualTo(expectedKey);
    }
}
