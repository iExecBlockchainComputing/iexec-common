/*
* Copyright 2020 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.docker.client;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class EnabledOnlyOnWednesdayNightCondition implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        boolean isWednesdayNight = LocalDateTime.now().getDayOfWeek().equals(DayOfWeek.WEDNESDAY) // wednesday == 3
                && LocalDateTime.now().getHour() >= 18; // 6pm
        if (!isWednesdayNight) {
            return ConditionEvaluationResult.disabled("Runs only on wednesday in the night");
        }
        return ConditionEvaluationResult.enabled("It's wednesday!");
    }
}
