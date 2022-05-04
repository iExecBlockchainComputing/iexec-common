package com.iexec.common.web;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseBodyDecoderTests {
    private enum DummyEnum {
        ENUM_MEMBER
    }

    private static Stream<Arguments> emptyResponses() {
        return Stream.of(
                Arguments.of("{}", Void.class     , Void.class     , ApiResponseBody.builder().build()),
                Arguments.of("{}", DummyEnum.class, Void.class     , ApiResponseBody.builder().build()),
                Arguments.of("{}", Void.class     , DummyEnum.class, ApiResponseBody.builder().build()),
                Arguments.of("{}", DummyEnum.class, DummyEnum.class, ApiResponseBody.builder().build())
        );
    }

    private static Stream<Arguments> responsesWithData() {
        return Stream.of(
                Arguments.of("{\"data\": \"test\"}"       , String.class   , Void.class, ApiResponseBody.builder().data("test").build()),
                Arguments.of("{\"data\": \"test\"}"       , String.class   , List.class, ApiResponseBody.builder().data("test").build()),
                Arguments.of("{\"data\": \"ENUM_MEMBER\"}", DummyEnum.class, Void.class, ApiResponseBody.builder().data(DummyEnum.ENUM_MEMBER).build()),
                Arguments.of("{\"data\": [1,2,3]}"        , List.class     , Void.class, ApiResponseBody.builder().data(List.of(1,2,3)).build())
        );
    }

    private static Stream<Arguments> responsesWithErrors() {
        return Stream.of(
                Arguments.of("{\"errors\": \"error\"}"      , Void.class  , String.class   , ApiResponseBody.builder().errors("error").build()),
                Arguments.of("{\"errors\": \"error\"}"      , String.class, String.class   , ApiResponseBody.builder().errors("error").build()),
                Arguments.of("{\"errors\": \"ENUM_MEMBER\"}", Void.class  , DummyEnum.class, ApiResponseBody.builder().errors(DummyEnum.ENUM_MEMBER).build()),
                Arguments.of(
                        "{\"errors\": [\"VALIDATION_ERROR_1\", \"VALIDATION_ERROR_2\"]}",
                        Void.class, List.class, ApiResponseBody.builder().errors(List.of("VALIDATION_ERROR_1", "VALIDATION_ERROR_2")).build()
                )
        );
    }

    private static Stream<Arguments> responsesWithDataAndErrors() {
        return Stream.of(
                Arguments.of("{\"data\": \"test\", \"errors\": \"error\"}"      , String.class, String.class   , ApiResponseBody.builder().data("test").errors("error").build()),
                Arguments.of("{\"data\": \"test\", \"errors\": \"ENUM_MEMBER\"}", String.class, DummyEnum.class, ApiResponseBody.builder().data("test").errors(DummyEnum.ENUM_MEMBER).build())
        );
    }

    private static Stream<Arguments> malformedResponses() {
        return Stream.of(
                Arguments.of(""                              , Void.class   , Void.class),
                Arguments.of("[]"                            , Void.class   , Void.class),
                Arguments.of("{\"data\": \"Not an integer\"}", Integer.class, Void.class),
                Arguments.of("{\"errors\": 1"                , Void.class   , DummyEnum.class),
                Arguments.of("{\"Unknown key\": 1"           , Void.class   , DummyEnum.class)
        );
    }

    @ParameterizedTest
    @MethodSource({"emptyResponses","responsesWithData","responsesWithErrors","responsesWithDataAndErrors"})
    <D, E> void shouldDecodeResponse(String responseBody, Class<D> dataClass, Class<E> errorsClass, ApiResponseBody<D, E> expectedResult) {
        final Optional<ApiResponseBody<D, E>> oResult = ApiResponseBodyDecoder.decodeResponse(responseBody, dataClass, errorsClass);
        assertTrue(oResult.isPresent());

        final ApiResponseBody<D, E> actualResult = oResult.get();
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @MethodSource({"responsesWithData","responsesWithDataAndErrors"})
    <D, E> void shouldDecodeResponseAndGetData(String responseBody, Class<D> dataClass, Class<E> errorsClass, ApiResponseBody<D, E> expectedResult) {
        final Optional<D> oData = ApiResponseBodyDecoder.getDataFromResponse(responseBody, dataClass, errorsClass);
        assertTrue(oData.isPresent());

        final D data = oData.get();
        assertEquals(expectedResult.getData(), data);
    }

    @ParameterizedTest
    @MethodSource({"responsesWithErrors","responsesWithDataAndErrors"})
    <D, E> void shouldDecodeResponseAndGetErrors(String responseBody, Class<D> dataClass, Class<E> errorsClass, ApiResponseBody<D, E> expectedResult) {
        final Optional<E> oErrors = ApiResponseBodyDecoder.getErrorsFromResponse(responseBody, dataClass, errorsClass);
        assertTrue(oErrors.isPresent());

        final E actualErrors = oErrors.get();
        assertEquals(expectedResult.getErrors(), actualErrors);
    }

    @ParameterizedTest
    @MethodSource("malformedResponses")
    <D, E> void shouldNotDecodeResponse(String responseBody, Class<D> dataClass, Class<E> errorsClass) {
        final Optional<ApiResponseBody<D, E>> oResult = ApiResponseBodyDecoder.decodeResponse(responseBody, dataClass, errorsClass);
        assertTrue(oResult.isEmpty());
    }

    @ParameterizedTest
    @MethodSource({"emptyResponses", "responsesWithErrors", "malformedResponses"})
    <D, E> void shouldNotDecodeResponseAndNotGetData(String responseBody, Class<D> dataClass, Class<E> errorsClass) {
        final Optional<D> oResult = ApiResponseBodyDecoder.getDataFromResponse(responseBody, dataClass, errorsClass);
        assertTrue(oResult.isEmpty());
    }

    @ParameterizedTest
    @MethodSource({"emptyResponses", "responsesWithData", "malformedResponses"})
    <D, E> void shouldNotDecodeResponseAndNotGetErrors(String responseBody, Class<D> dataClass, Class<E> errorsClass) {
        final Optional<E> oResult = ApiResponseBodyDecoder.getErrorsFromResponse(responseBody, dataClass, errorsClass);
        assertTrue(oResult.isEmpty());
    }
}