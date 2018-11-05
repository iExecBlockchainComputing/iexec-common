# iexec-common-new

## Steps to genreate Java Wrappers for Smart Contacts

```
git clone https://github.com/iExecBlockchainComputing/PoCo-dev/
cd PoCo-dev
git checkout ABILegacy
npm i
./node_modules/.bin/truffle compile
# Get https://github.com/web3j/web3j/releases binary
web3j-3.6.0/bin/web3j truffle generate ~iexecdev/PoCo-dev/build/contracts/IexecClerkABILegacy.json -o ./java -p com.iexec.common
web3j-3.6.0/bin/web3j truffle generate ~iexecdev/PoCo-dev/build/contracts/IexecHubABILegacy.json -o ./java -p com.iexec.common
```
