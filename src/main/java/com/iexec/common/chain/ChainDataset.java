package com.iexec.common.chain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ChainDataset {

    private String chainDatasetId;
    private String owner;
    private String name;
    private String uri;
    private String checksum;

}
