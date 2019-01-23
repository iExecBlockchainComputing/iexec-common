package com.iexec.common.result.eip712;

import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Data
@Getter
public class Eip712Challenge {

    private static final String DOMAIN_NAME = "iExec Result Repository";
    private static final String DOMAIN_VERSION = "1";
    private static final String PRIMARY_TYPE = "Challenge";

    private Types types;
    private Domain domain;
    private String primaryType;
    private Message message;

    public Eip712Challenge(String challenge, long chainId) {
        this(challenge, chainId, DOMAIN_NAME, DOMAIN_VERSION, PRIMARY_TYPE);
    }

    public Eip712Challenge(String challenge, long chainId, String domainName) {
        this(challenge, chainId, domainName, DOMAIN_VERSION, PRIMARY_TYPE);
    }

    public Eip712Challenge(String challenge, long chainId, String domainName, String domainVersion, String primaryType) {
        List<TypeParam> domainTypeParams = Arrays.asList(
                new TypeParam("name", "string"),
                new TypeParam("version", "string"),
                new TypeParam("chainId", "uint256")
        );

        List<TypeParam> messageTypeParams = Arrays.asList(
                new TypeParam("challenge", "string")
        );

        Types types = new Types(domainTypeParams, messageTypeParams);

        Domain domain = Domain.builder()
                .name(domainName)
                .version(domainVersion)
                .chainId(chainId)
                .build();

        Message message = Message.builder()
                .challenge(challenge)
                .build();

        this.types = types;
        this.domain = domain;
        this.message = message;
        this.primaryType = primaryType;
    }

}


