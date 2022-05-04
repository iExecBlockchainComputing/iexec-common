/*
 * Copyright 2021 IEXEC BLOCKCHAIN TECH
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseBody<D, E> {
    private D data;
    private E errors;

    /**
     * Return whether this response contains error(s).
     * Checks whether the object is null.
     * In case of a collection of errors, also checks whether it is empty.
     *
     * @return {@literal false} if {@code errors} is null or empty,
     * {@literal true} otherwise.
     */
    @JsonIgnore
    public boolean isError() {
        boolean isError = errors == null;

        if (errors instanceof Collection) {
            isError = !((Collection<?>) errors).isEmpty();
        }

        return isError;
    }
}