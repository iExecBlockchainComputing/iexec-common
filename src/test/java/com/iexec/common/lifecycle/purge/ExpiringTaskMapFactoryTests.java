/*
 * Copyright 2022-2025 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.lifecycle.purge;

import net.jodah.expiringmap.ExpiringMap;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpiringTaskMapFactoryTests {
    private static final String CHAIN_TASK_ID = "chainTaskId";

    // region getExpiringTaskMap
    @Test
    void shouldGetExpiringTaskMap() {
        final Integer object = 1;
        final Map<String, Integer> map = ExpiringTaskMapFactory.getExpiringTaskMap();
        map.put(CHAIN_TASK_ID, object);

        assertEquals(1, map.size());
        assertEquals(object, map.get(CHAIN_TASK_ID));

        assertEquals(100 * 60 * 60 * 1000,
                ((ExpiringMap<String, Integer>) map).getExpiration());
    }
    // endregion
}