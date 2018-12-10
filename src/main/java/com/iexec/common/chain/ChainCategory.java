package com.iexec.common.chain;

import lombok.*;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ChainCategory {

    private long id;
    private String name;
    private String description;
    private long maxTime;

    public static ChainCategory tuple2ChainCategory(long id, String name, String description, BigInteger maxTime) {
        return ChainCategory.builder()
                .id(id)
                .name(name)
                .description(description)
                .maxTime(maxTime.longValue() * 1000)
                .build();
    }
}
