package com.iexec.common.result.eip712;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Domain {

    private String name;
    private String version;
    private long chainId;

}
