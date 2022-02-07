package com.iexec.common.utils;

import feign.Logger;
import feign.RequestLine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * The purpose of those tests is to check the {@code JacksonEncoder#encode}
 * and {@code JacksonDecoder#decode} methods are properly overridden.
 */
public class FeignBuilderTest {

    @Container
    private static final MockServerContainer mockServer = new MockServerContainer(DockerImageName.parse("mockserver/mockserver"))
            .withExposedPorts(1080);

    private FeignTestClient feignTestClient;

    @BeforeEach
    void preflight() {
        mockServer.start();
        feignTestClient = FeignBuilder.createBuilder(Logger.Level.FULL)
                .target(FeignTestClient.class,
                        "http://" + mockServer.getHost() + ":" + mockServer.getServerPort());
    }

    @Test
    void testStringDecoder() {
        new MockServerClient(mockServer.getHost(), mockServer.getServerPort())
                .when(request()
                        .withMethod("GET")
                        .withPath("/string"))
                .respond(response()
                        .withBody("stringValue"));
        String value = feignTestClient.getStringValue();
        Assertions.assertEquals("stringValue", value);
    }

    @Test
    void testStringEncoder() {
        new MockServerClient(mockServer.getHost(), mockServer.getServerPort())
                .when(request()
                        .withMethod("POST")
                        .withPath("/string"))
                .respond(response()
                        .withBody("")
                        .withStatusCode(200));
        Assertions.assertEquals("", feignTestClient.setStringValue("stringValue"));
    }

    @Test
    void testJsonDecoder() {
        new MockServerClient(mockServer.getHost(), mockServer.getServerPort())
                .when(request()
                        .withMethod("GET")
                        .withPath("/object"))
                .respond(response()
                        .withBody("{\"objectField\": \"objectValue\"}"));
        TestObject testObject = feignTestClient.getTestObject();
        Assertions.assertEquals("objectValue", testObject.getObjectField());
    }

    @Test
    void testJsonEncoder() {
        new MockServerClient(mockServer.getHost(), mockServer.getServerPort())
                .when(request()
                        .withMethod("POST")
                        .withPath("/object"))
                .respond(response()
                        .withBody("")
                        .withStatusCode(200));
        TestObject testObject = new TestObject();
        testObject.setObjectField("objectValue");
        Assertions.assertEquals("", feignTestClient.setTestObject(testObject));
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
    }

}
