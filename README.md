# iexec-common

## Steps to genreate Java Wrappers for Smart Contacts

```
git clone https://github.com/iExecBlockchainComputing/PoCo-dev/
cd PoCo-dev
git checkout testdocker
npm i
./node_modules/.bin/truffle compile
# Get https://github.com/web3j/web3j/releases binary
~/Downloads/web3j-4.3.0/bin/web3j truffle generate ~/iexecdev/PoCo-dev/build/contracts/IexecClerkABILegacy.json -o ~/iexecdev/iexec-common/src/main/java/ -p com.iexec.common.contract.generated
~/Downloads/web3j-4.3.0/bin/web3j truffle generate ~/iexecdev/PoCo-dev/build/contracts/IexecHubABILegacy.json -o ~/iexecdev/iexec-common/src/main/java/ -p com.iexec.common.contract.generated
~/Downloads/web3j-4.3.0/bin/web3j truffle generate ~/iexecdev/PoCo-dev/build/contracts/App.json -o ~/iexecdev/iexec-common/src/main/java/ -p com.iexec.common.contract.generated
~/Downloads/web3j-4.3.0/bin/web3j truffle generate ~/iexecdev/PoCo-dev/build/contracts/Dataset.json -o ~/iexecdev/iexec-common/src/main/java/ -p com.iexec.common.contract.generated
~/Downloads/web3j-4.3.0/bin/web3j truffle generate ~/iexecdev/PoCo-dev/build/contracts/Ownable.json -o ~/iexecdev/iexec-common/src/main/java/ -p com.iexec.common.contract.generated
```
