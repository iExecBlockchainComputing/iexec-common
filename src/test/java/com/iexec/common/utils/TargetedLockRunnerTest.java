package com.iexec.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

class TargetedLockRunnerTest {
    // region Check methods actually run what's expected
    @Test
    void runWithLock() {
        final TargetedLockRunner<Integer> locks = new TargetedLockRunner<>();

        Wrapper<Integer> wrappedValue = new Wrapper<>(0);
        locks.runWithLock(1, () -> wrappedValue.setValue(1));

        Assertions.assertThat(wrappedValue.getValue()).isEqualTo(1);
    }

    @Test
    void acceptWithLock() {
        final TargetedLockRunner<Integer> locks = new TargetedLockRunner<>();

        Wrapper<Integer> wrappedValue = new Wrapper<>(0);
        locks.acceptWithLock(1, wrappedValue::setValue);

        Assertions.assertThat(wrappedValue.getValue()).isEqualTo(1);
    }

    @Test
    void getWithLock() {
        final TargetedLockRunner<Integer> locks = new TargetedLockRunner<>();

        boolean hasRun = locks.getWithLock(1, () -> true);

        Assertions.assertThat(hasRun).isTrue();
    }

    @Test
    void applyWithLock() {
        final TargetedLockRunner<Integer> locks = new TargetedLockRunner<>();

        Wrapper<Integer> wrappedValue = new Wrapper<>(0);
        boolean hasRun = locks.applyWithLock(1, wrappedValue::setValueAndAcknowledge);

        Assertions.assertThat(hasRun).isTrue();
        Assertions.assertThat(wrappedValue.getValue()).isEqualTo(1);
    }

    @Data
    @AllArgsConstructor
    private static class Wrapper<T> {
        private T value;

        public boolean setValueAndAcknowledge(T value) {
            this.value = value;
            return true;
        }
    }
    // endregion

    // region Check synchronization works
    /**
     * Run an action {@code numberOfRunsPerGroup * numberOfActions} times.
     * <br>
     * There are {@code numberOfActions} groups of actions, every action in a group having the same input.
     * Every group is run {@code numberOfRunsPerGroup} times.
     *
     * @param <K> Type of the lock key.
     */
    private <K> void runWithLock(int numberOfRunsPerGroup,
                                 int numberOfActions,
                                 Function<Integer, K> keyProvider) {
        final TargetedLockRunner<K> locks = new TargetedLockRunner<>();

        // Represents the order the actions will have been run.
        final ConcurrentLinkedQueue<Integer> actionsOrder = new ConcurrentLinkedQueue<>();

        // Just a simple consumer that runs an action a bunch of times
        // and logs each time in `actionsOrder` list.
        // If this consumer is run a few times at the same time,
        // it should mix the values when there's no sync mechanism.
        final Consumer<Integer> runActionAndGetOrder = (i) -> {
            for (int j = 0; j < numberOfRunsPerGroup; j++) {
                actionsOrder.add(i);
            }
        };

        // Run the action a bunch of times with different inputs
        // and potentially different keys each time.
        IntStream.range(0, numberOfActions)
                .parallel()
                .forEach(i -> locks.runWithLock(keyProvider.apply(i), () -> runActionAndGetOrder.accept(i)));

        assertOrderIsCorrect(actionsOrder, numberOfRunsPerGroup, keyProvider);
    }

    /**
     * Run an action {@code numberOfRunsPerGroup * numberOfActions} times.
     * <br>
     * There are {@code numberOfActions} groups of actions, every action in a group having the same input.
     * Every group is run {@code numberOfRunsPerGroup} times.
     * <br>
     * That's the same as {@link TargetedLockRunnerTest#runWithLock(int, int, Function)}
     * but there's no lock so synchronization should fail.
     *
     * @param <K> Type of the lock key.
     */
    private <K> void runWithoutLock(int numberOfRunsPerGroup,
                                    int numberOfActions,
                                    Function<Integer, K> keyProvider) {
        // Represents the order the actions will have been run.
        final ConcurrentLinkedQueue<Integer> actionsOrder = new ConcurrentLinkedQueue<>();

        // Just a simple consumer that runs an action a bunch of times
        // and logs each time in `actionsOrder` list.
        // If this consumer is run a few times at the same time,
        // it should mix the values when there's no sync mechanism.
        final Consumer<Integer> runActionAndGetOrder = (i) -> {
            for (int j = 0; j < numberOfRunsPerGroup; j++) {
                actionsOrder.add(i);
            }
        };

        // Run the action a bunch of times with different inputs
        // and potentially different keys each time.
        IntStream.range(0, numberOfActions)
                .parallel()
                .forEach(runActionAndGetOrder::accept);

        assertOrderIsCorrect(actionsOrder, numberOfRunsPerGroup, keyProvider);
    }

    /**
     * We loop through calls order and see if all calls for a given action have finished
     * before another action with the same key starts for this task.
     * Two actions with different keys should be able to run at the same time.
     */
    private <K> void assertOrderIsCorrect(ConcurrentLinkedQueue<Integer> actionsOrder, int numberOfRunsPerGroup, Function<Integer, K> keyProvider) {
        final Map<K, Map<Integer, Integer>> callsOrder = new HashMap<>();

        for (int index : actionsOrder) {
            final Map<Integer, Integer> foundOutputsForKeyGroup = callsOrder.computeIfAbsent(keyProvider.apply(index), (key) -> new HashMap<>());
            for (int alreadyFound : foundOutputsForKeyGroup.keySet()) {
                if (!Objects.equals(alreadyFound, index) && foundOutputsForKeyGroup.get(alreadyFound) < numberOfRunsPerGroup) {
                    Assertions.fail("Synchronization has failed: %s has only %s out of %s occurrences while %s has been inserted.",
                            alreadyFound, foundOutputsForKeyGroup.get(alreadyFound), numberOfRunsPerGroup, index);
                }
            }

            foundOutputsForKeyGroup.merge(index, 1, (currentValue, defaultValue) -> currentValue + 1);
        }
    }

    /**
     * A constant value is used to lock all operations:
     * final order should be a list within which each value should not interfere with others.
     * E.g. [2, 2, 2, 1, 1, 1, 0, 0, 0, 3, 3, 3] would be an expected result,
     * however [2, <b>1</b>, 2, 2, 1, 1, 0, 0, 0, 3, 3, 3] would not be an expected result as 1 interferes with 2.
     */
    @Test
    void runWithLockOnConstantValue() {
        runWithLock(100, 100, i -> true);
        Assertions.assertThatThrownBy(() -> runWithoutLock(100, 100, i -> true))
                .hasMessageContaining("Synchronization has failed");
    }

    /**
     * Parity is used to lock all operations:
     * final order should be a list within which each value should not interfere with others of same parity.
     * E.g. [2, 1, 2, 2, 0, 1, 1, 3, 0, 0, 0, 3, 3] would be an expected result,
     * however [2, 1, 2, 2, 1, <b>3</b>, 1, 0, 0, 0, 3, 3] would not be an expected result as 3 interferes with 1.
     */
    @Test
    void runWithLockOnParity() {
        runWithLock(100, 100, i -> i % 2 == 0);
        Assertions.assertThatThrownBy(() -> runWithoutLock(100, 100, i -> i % 2 == 0))
                .hasMessageContaining("Synchronization has failed");
    }

    /**
     * Operation's value are used to lock operations:
     * any order is then acceptable as we don't care about interferences between distinct operations.
     */
    @Test
    void runWithLockPerValue() {
        runWithLock(100, 100, Function.identity());

        // As any order is acceptable, there should not be any exception there.
        Assertions.assertThatCode(() -> runWithoutLock(100, 100, Function.identity()))
                .doesNotThrowAnyException();
    }
    // endregion
}