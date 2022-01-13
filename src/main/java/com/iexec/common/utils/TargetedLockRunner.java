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

package com.iexec.common.utils;

import net.jodah.expiringmap.ExpiringMap;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Provides a way to avoid parallel runs of an action.
 * Locks are based on a key, so that two actions with the same key can't be run at the same time
 * but two actions with different keys can be run at the same time.
 * <br>
 * To avoid memory-leaks, entries expire after a given time.
 * Default is 1 hour, but it can be set at {@link TargetedLockRunner} object creation.
 *
 * @param <K> Type of the key.
 */
public class TargetedLockRunner<K> {
    private final ConcurrentMap<K, Object> locks;

    public TargetedLockRunner() {
        this(1, TimeUnit.HOURS);
    }

    public TargetedLockRunner(long expirationDuration, TimeUnit unit) {
        this.locks = ExpiringMap.builder()
                .expiration(expirationDuration, unit)
                .build();
    }

    public void runWithLock(K key, Runnable action) {
        synchronized (getLock(key)) {
            action.run();
        }
    }

    public void acceptWithLock(K key, Consumer<K> action) {
        synchronized (getLock(key)) {
            action.accept(key);
        }
    }

    public <R> R getWithLock(K key, Supplier<R> action) {
        synchronized (getLock(key)) {
            return action.get();
        }
    }

    public <R> R applyWithLock(K key, Function<K, R> action) {
        synchronized (getLock(key)) {
            return action.apply(key);
        }
    }

    private Object getLock(K key) {
        // `ConcurrentHashMap::computeIfAbsent` is atomic,
        // so there can't be any race condition there
        // if this call is wrapped in a `synchronized` block.
        return locks.computeIfAbsent(key, id -> new Object());
    }
}
