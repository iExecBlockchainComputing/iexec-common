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

package com.iexec.common.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class ApiResponseBodyDecoder {
    private static final ObjectMapper mapper = new ObjectMapper();

    private ApiResponseBodyDecoder() {
        throw new UnsupportedOperationException();
    }

    public static <D,E> Optional<ApiResponseBody<D, E>> decodeResponse(String response, Class<D> dataClass, Class<E> errorClass) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ApiResponseBody.class, dataClass, errorClass);
        try {
            return Optional.ofNullable(mapper.readValue(response, javaType));
        } catch (JsonProcessingException e) {
            log.warn("Failed to decode response", e);
            return Optional.empty();
        }
    }

    public static <D,E> Optional<D> getDataFromResponse(String response, Class<D> dataClass, Class<E> errorClass) {
        return decodeResponse(response, dataClass, errorClass)
                .map(ApiResponseBody::getData);
    }

    public static <D,E> Optional<E> getErrorFromResponse(String response, Class<D> dataClass, Class<E> errorClass) {
        return decodeResponse(response, dataClass, errorClass)
                .map(ApiResponseBody::getError);
    }
}
