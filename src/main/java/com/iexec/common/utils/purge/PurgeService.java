package com.iexec.common.utils.purge;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class PurgeService {
    private final List<Purgeable> services;

    public PurgeService(List<Purgeable> services) {
        this.services = services;
        log.info("The following services are registered to be purged on task completion: {}",
                services.stream().map(Purgeable::getClass).collect(Collectors.toList()));
    }

    /**
     * On each registered service, calls {@link Purgeable#purgeTask(String)} with given chain task ID.
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
