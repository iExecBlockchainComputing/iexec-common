# iexec-common

## Overview

The iexec-common library holds classes related to:
* iExec tasks and replicates
* Results of iExec tasks
* Compute model
* Blockchain-related things like
    * web3 clients
    * iExec Proof-of-Contribution
* Docker client
* TEE configurations
* Utils tooling

## Build from sources

```
./gradlew build --refresh-dependencies
```

## Steps to generate Java Wrappers for Smart Contracts

```
git clone https://github.com/iExecBlockchainComputing/PoCo-dev/
cd PoCo-dev
git checkout x.y.z
npm i
./node_modules/.bin/truffle compile
# Get web3j-cli (deprecated)
curl -L https://get.web3j.io | sh
# Import latest web3j in gradle: https://github.com/web3j/web3j/releases
web3j truffle generate ~/iexecdev/PoCo-dev/build/contracts/App.json -o ~/iexecdev/iexec-common/src/main/java/ -p com.iexec.common.contract.generated
web3j truffle generate ~/iexecdev/PoCo-dev/build/contracts/Dataset.json -o ~/iexecdev/iexec-common/src/main/java/ -p com.iexec.common.contract.generated
web3j truffle generate ~/iexecdev/PoCo-dev/build/contracts/Ownable.json -o ~/iexecdev/iexec-common/src/main/java/ -p com.iexec.common.contract.generated

#comment tuple json parts
web3j truffle generate ~/iexecdev/PoCo-dev/build/contracts/IexecInterfaceTokenABILegacy.json -o ~/iexecdev/iexec-common/src/main/java/ -p com.iexec.common.contract.generated
# Rename IexecInterfaceTokenABILegacy.java to IexecHubContract.java

# Get epirus-cli (recommended)
# Clean IexecLibOrders_v5.OrderOperationEnum references from IexecLibOrders_v5.json, then
epirus generate solidity generate --abiFile=$HOME/iexecdev/PoCo-dev/build/abi/IexecLibOrders_v5.json -o ~/iexecdev/iexec-common/src/main/java/ -p com.iexec.common.contract.generated

```

## License

iExec common code is released under the [Apache License 2.0](LICENSE).
