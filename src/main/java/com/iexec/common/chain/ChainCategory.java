package com.iexec.common.chain;

import lombok.*;

import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ChainCategory {

    private long id;
    private String name;
    private String description;
    private long maxExecutionTime;

    public static ChainCategory tuple2ChainCategory(long id, String name, String description, BigInteger maxTime) {
        return ChainCategory.builder()
                .id(id)
                .name(name)
                .description(description)
                .maxExecutionTime(maxTime.longValue() * 1000)
                .build();
    }
}
