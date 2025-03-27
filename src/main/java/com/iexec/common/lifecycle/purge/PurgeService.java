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

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class PurgeService {
    private final List<Purgeable> services;

    public PurgeService(List<Purgeable> services) {
        this.services = services;
        log.info("The following services are registered to be purged on task completion: {}",
                services.stream().map(Purgeable::getClass).toList());
    }

    /**
     * On each registered service, calls {@link Purgeable#purgeTask(String)} with given chain task ID.
     *
     * @param chainTaskId Task ID whose data should be purged from registered services.
     * @return {@literal true} if there's no more reference to task data in all registered services,
     * {@literal false} otherwise.
     */
    public boolean purgeAllServices(String chainTaskId) {
        return services
                .stream()
                .allMatch(service -> service.purgeTask(chainTaskId));
    }
}
