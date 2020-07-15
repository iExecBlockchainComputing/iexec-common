package com.iexec.common.chain;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ChainApp {

    private String chainAppId;
    private String name;
    private String type;
    private String uri;
    private String checksum;
    private String fingerprint;


}
