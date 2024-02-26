/*
 * Copyright 2022-2024 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.utils;

import feign.Logger;
import feign.RequestLine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.socket.PortFactory;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * The purpose of those tests is to check the {@code JacksonEncoder#encode}
 * and {@code JacksonDecoder#decode} methods are properly overridden.
 */
class FeignBuilderTest {

    private ClientAndServer mockServer;

    private FeignTestClient feignTestClient;

    @BeforeEach
    void preflight() {
        mockServer = ClientAndServer.startClientAndServer(PortFactory.findFreePort());
        feignTestClient = FeignBuilder.createBuilder(Logger.Level.FULL)
                .target(FeignTestClient.class, "http://localhost:" + mockServer.getPort());
    }

    @AfterEach
    void stopServer() {
        mockServer.stop();
    }

    @Test
    void testBuilderCreation() {
        assertNotNull(FeignBuilder.createBuilder(Logger.Level.FULL)
                .target(FeignTestClient.class, "http://localhost:" + mockServer.getPort()));
        assertNotNull(FeignBuilder.createBuilderWithBasicAuth(Logger.Level.FULL, "username", "password")
                .target(FeignTestClient.class, "http://localhost:" + mockServer.getPort()));
    }

    @Test
    void testStringDecoder() {
        mockServer
                .when(request()
                        .withMethod("GET")
                        .withPath("/string"))
                .respond(response()
                        .withBody("stringValue"));
        String value = feignTestClient.getStringValue();
        assertEquals("stringValue", value);
    }

    @Test
    void testStringEncoder() {
        mockServer
                .when(request()
                        .withMethod("POST")
                        .withPath("/string"))
                .respond(response()
                        .withBody("")
                        .withStatusCode(200));
        assertEquals("", feignTestClient.setStringValue("stringValue"));
    }

    @Test
    void testJsonDecoder() {
        mockServer
                .when(request()
                        .withMethod("GET")
                        .withPath("/object"))
                .respond(response()
                        .withBody("{\"objectField\": \"objectValue\",\"durationField\":5.000000000}"));
        TestObject testObject = feignTestClient.getTestObject();
        assertEquals("objectValue", testObject.getObjectField());
        assertEquals(Duration.ofSeconds(5L), testObject.getDurationField());
    }

    @Test
    void testJsonEncoder() {
        mockServer
                .when(request()
                        .withMethod("POST")
                        .withPath("/object"))
                .respond(response()
                        .withBody("")
                        .withStatusCode(200));
        TestObject testObject = new TestObject();
        testObject.setObjectField("objectValue");
        testObject.setDurationField(Duration.ofSeconds(5));
        assertEquals("", feignTestClient.setTestObject(testObject));
    }

    interface FeignTestClient {

        @RequestLine("GET /string")
        String getStringValue();

        @RequestLine("POST /string")
        String setStringValue(String value);

        @RequestLine("GET /object")
        TestObject getTestObject();

        @RequestLine("POST /object")
        String setTestObject(TestObject testObject);

    }

    @Getter
    @Setter
    @NoArgsConstructor
    static class TestObject {
        private String objectField;
        private Duration durationField;
    }

}
