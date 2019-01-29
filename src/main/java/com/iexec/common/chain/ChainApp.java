package com.iexec.common.chain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iexec.common.contract.generated.App;
import lombok.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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

}
