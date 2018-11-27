package com.iexec.common.chain;

import com.iexec.common.contract.generated.App;
import com.iexec.common.contract.generated.IexecClerkABILegacy;
import com.iexec.common.contract.generated.IexecHubABILegacy;
import lombok.extern.slf4j.Slf4j;
import org.web3j.crypto.Credentials;
import org.web3j.ens.EnsResolutionException;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigInteger;

@Slf4j
public class ChainUtils {

    private ChainUtils() {
        throw new UnsupportedOperationException();
    }

    public static Web3j getWeb3j(String chainNodeAddress) {
        Web3j web3j = Web3j.build(new HttpService(chainNodeAddress));
        ExceptionInInitializerError exceptionInInitializerError = new ExceptionInInitializerError("Failed to connect to ethereum node " + chainNodeAddress);
        try {
            log.info("Connected to Ethereum node [node:{}]", web3j.web3ClientVersion().send().getWeb3ClientVersion());
            if (web3j.web3ClientVersion().send().getWeb3ClientVersion() == null) {
                throw exceptionInInitializerError;
            }
        } catch (IOException e) {
            throw exceptionInInitializerError;
        }
        return web3j;
    }

    public static IexecHubABILegacy loadHubContract(Credentials credentials, Web3j web3j, String iexecHubAddress) {
        ExceptionInInitializerError exceptionInInitializerError = new ExceptionInInitializerError("Failed to load IexecHub contract from address " + iexecHubAddress);

        if (iexecHubAddress != null && !iexecHubAddress.isEmpty()) {
            try {
                IexecHubABILegacy iexecHubABILegacy = IexecHubABILegacy.load(
                        iexecHubAddress, web3j, credentials, new ContractGasProvider() {
                            @Override
                            public BigInteger getGasPrice(String s) {
                                return BigInteger.valueOf(22000000000L);
                            }

                            @Override
                            public BigInteger getGasPrice() {
                                return BigInteger.valueOf(22000000000L);
                            }

                            @Override
                            public BigInteger getGasLimit(String s) {
                                return BigInteger.valueOf(8000000L);
                            }

                            @Override
                            public BigInteger getGasLimit() {
                                return BigInteger.valueOf(8000000L);
                            }
                        });

                log.info("Loaded contract IexecHub [address:{}] ", iexecHubAddress);
                return iexecHubABILegacy;
            } catch (EnsResolutionException e) {
                throw exceptionInInitializerError;
            }
        } else {
            throw exceptionInInitializerError;
        }
    }

    public static IexecClerkABILegacy loadClerkContract(Credentials credentials, Web3j web3j, String iexecHubAddress) {
        IexecHubABILegacy iexecHubABILegacy = loadHubContract(credentials, web3j, iexecHubAddress);
        ExceptionInInitializerError exceptionInInitializerError = new ExceptionInInitializerError("Failed to load IexecClerk contract from Hub address " + iexecHubAddress);
        try {
            String addressClerk = iexecHubABILegacy.iexecclerk().send();
            if (addressClerk == null || addressClerk.isEmpty()) {
                throw exceptionInInitializerError;
            }

            IexecClerkABILegacy iexecClerkABILegacy = IexecClerkABILegacy.load(addressClerk, web3j, credentials, new DefaultGasProvider());
            log.info("Loaded contract IexecClerkLegacy [address:{}] ", addressClerk);
            return iexecClerkABILegacy;
        } catch (Exception e) {
            throw exceptionInInitializerError;
        }
    }

    public static App loadDappContract(Credentials credentials, Web3j web3j, String appAddress) {
        ExceptionInInitializerError exceptionInInitializerError = new ExceptionInInitializerError("Failed to load Dapp contract address " + appAddress);
        try {
            if (appAddress == null || appAddress.isEmpty()) {
                throw exceptionInInitializerError;
            }

            App app = App.load(appAddress, web3j, credentials, new DefaultGasProvider());
            log.info("Loaded contract app [address:{}] ", appAddress);
            return app;
        } catch (Exception e) {
            throw exceptionInInitializerError;
        }
    }
}
