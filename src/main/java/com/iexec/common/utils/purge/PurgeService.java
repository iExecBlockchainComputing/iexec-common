package com.iexec.common.utils.purge;

import java.util.List;

public class PurgeService {
    private final List<Purgeable> services;

    public PurgeService(List<Purgeable> services) {
        this.services = services;
    }

    public boolean purgeAllServices(String chainTaskId) {
        return services
                .stream()
                .allMatch(service -> service.purgeTask(chainTaskId));
    }
}
