# Changelog

All notable changes to this project will be documented in this file.

## [[NEXT]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/vNEXT) 2023

### Bug Fixes
- Remove deprecated `OrderSigner` class and `OrderTag` enum. (#430)

## [[8.3.0]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/v8.3.0) 2023-09-26

### New Features
- Add new `POST_COMPUTE_TOO_LONG_RESULT_FILE_NAME` replicate status cause. (#422)
### Quality
- Upgrade to Gradle 8.2.1 with up-to-date plugins. (#424)
- Delete `ArgsUtils`, `SgxUtils` and `SgxDriverMode` classes moved to `iexec-commons-containers`. (#426)
### Dependency Upgrades
- Upgrade to Spring Boot 2.7.14. (#423)
- Upgrade to `jenkins-library` 2.7.3. (#425)
- Remove `maven-shared-utils` dependency. (#426)
- Add `commons-io` 2.13.0. (#426)
- Upgrade to `iexec-commons-poco` 3.1.0. (#427)

## [[8.2.1]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/v8.2.1) 2023-06-23

### Dependency Upgrades
- Upgrade to `iexec-commons-poco` 3.0.4. (#420)

## [[8.2.0]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/v8.2.0) 2023-06-05

### Bug Fixes
- Always use DatasetAddress in `IEXEC_DATASET_FILENAME` environment variable. (#416)
### Quality
- Mark classes from `com.iexec.common.sdk.order`, `com.iexec.common.sdk.order.payload` and `com.iexec.common.chain.eip712` packages deprecated. (#414 #415)
### Dependency Upgrades
- Upgrade to `iexec-commons-poco` 3.0.0. (#417)

## [[8.1.0]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/v8.1.0) 2023-05-11

### New Features
- Add `CONTRIBUTE_AND_FINALIZE` replicate statuses. (#408)
- Add `contributeAndFinalize` related values to `ReplicateStatusCause`. (#411)
### Bug Fixes
- Log `computedFilePath` instead of `computedFileDir`. (#409)
### Quality
- Clean unused `ReplicateStatus#RESULT_UPLOAD_REQUEST_FAILED`. (#410)
### Dependency Upgrades
- Upgrade to `iexec-commons-poco` 2.0.0. (#412)

## [[8.0.0]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/v8.0.0) 2023-04-12

### Quality
- Deprecate and remove custom Docker implementation in favor of `iexec-commons-containers`. (#388, #389, #390, #391, #392, #393, #394)
- Remove `com.iexec.common.sms` package. (#395)
- Immutable classes in `com.iexec.common.config` package. (#396)
- Immutable classes in `com.iexec.common.sdk.order` package. (#397)
- Clean `iexec-common` following `iexec-commons-poco` creation. (#399, #400, #401, #402, #403, #404)
### Dependency Upgrades
- Upgrade to `jenkins-library` 2.4.0. (#387)
- New `iexec-commons-poco` 1.0.2 dependency. (#398, #404)

## [[7.0.0]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/v7.0.0) 2023-03-02

### New Features
* Add classes to support Gramine framework for TEE tasks.
* Rename `TeeEnclaveProvider` to `TeeFramework`.
* Add SMS URL to related task and replicate classes.
### Bug Fixes
* Disable `notifyJobs` configuration in Jenkinsfile pipeline.
* Resolve dependencies from internal Nexus.
* Increase app creation gas limit.
* Support `provider` alias for `framework` in `TeeEnclaveConfiguration`.
* Ignore entrypoint and heap size during Gramine `TeeEnclaveConfiguration` validation.
### Quality
* Introduce changelog.
* Rework Docker implementation and tests.
* Remove failed synchronization assertions in tests of `ContextualLockRunner`.
* Remove `IexecCli` and `BrokeringEngine`.
* Remove unused references.
* Remove shadow JAR bundle.
### Dependency Upgrades
* Upgrade spring-boot-dependencies to 2.6.14.
* Upgrade feign dependencies to 11.10.
* Upgrade java-multiaddr dependency to v1.4.6.
* Upgrade to Gradle 7.6.

## [[6.2.2]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/v6.2.2) 2023-03-02

* Update iconarchive RLC PNG hash in tests.

## [[6.2.1]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/v6.2.1) 2022-12-19

* Improve Docker tests reentrancy.

## [[6.2.0]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/v6.2.0) 2022-12-16

* Update Web3j dependency to version [4.8.9](https://github.com/web3j/web3j/releases/tag/v4.8.9).
* Add `CredentialsAbstractService` constructor from `Credentials`.

## [[6.1.0]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/v6.1.0) 2022-12-07

* Display stack trace when zip of a folder fails.
* Add `TASK_DESCRIPTION_INVALID` in `ReplicateStatusCause` to represent invalid task descriptions.
* Miscellaneous code quality improvements.
* Add `.sonarcloud.properties` configuration file.
* Increments of jenkins-library up to version 2.2.3. Enable SonarCloud analyses on branches and Pull Requests.

## [[6.0.1]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/v6.0.1) 2022-07-25

* Fix test downloading resource over SSL.

## [[6.0.0]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/v6.0.0) 2022-06-27

* Add pre/app/post compute replicate execution statuses for task feedback.
* Manipulate replicates stdout and stderr through a dedicated class.
* Improve EIP-712 implementation.
* Remove deprecated EIP-712 implementation.
* Fix some serialization and deserialization problems.

## [[5.9.3]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/v5.9.3) 2022-04-12

* Add methods to fetch workerpool orders based on tag.

## [[5.9.2]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/v5.9.2) 2022-04-05

* Make Docker image pull timeout configurable.

## [[5.9.1]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/5.9.1) 2021-12-15

* Get EIP712Domain without verifying contract if not provided.

## [[5.9.0]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/5.9.0) 2021-11-30

* Fix address recovery from private key.
* Fix converter from hexadecimal string to bytes 32.
* Upgrade Jacoco/Sonarqube reporting and plugins.

## [[5.8.0]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/5.8.0) 2021-11-25

* Add dedicated public chain configuration domain entity.
* Enhance tooling to deal with hex strings.
* Upgrade artifacts publishing process.

## [[5.7.0]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/5.7.0) 2021-11-10

* Add appAddress to task description.
* Merge abort notifications into a single notification with custom abort causes.

## [[5.6.0]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/5.6.0) 2021-10-05

* Check if replicate has failed before computing.

## [[5.5.2]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/5.5.2) 2021-10-13

* Get on-chain task features a minimal field validation to ensure data consistency.
* Get task description waits for on-chain task (and deal) or retries.
* Added methods for fetching on-chain task and deal information with retry.

## [[5.5.1]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/5.5.1) 2021-10-05

* Any blockchain call is now made over a single web3-HTTP client to enable socket reuse.

## [[5.5.0]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/5.5.0) 2021-09-21

* Add tools to deploy more iExec objects (Workerpools, applications).
* Add EthAddress utility to validate Ethereum adresses.

## [[5.4.0]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/5.4.0) 2021-06-16

* Use the new MrEnclave format for TEE applications.
* Add shared configuration between the SMS and the worker.

## [[5.3.0]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/5.3.0) 2021-06-01

* TEE pre-compute configuration.
* TEE & Standard compute configuration.

## [[5.2.0]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/5.2.0) 2021-03-26

* feature/5.2.0-rc1
* feature/docker-devices
* feature/docker-tests
* feature/orders
* feature/docker
* feature/download-replicate-status-causes
* feature/retrieve-checked-onchain-objects
* feature/format
* feature/final-replicate-statuses
* feature/v5.2.0-snapshot

## [[5.1.0]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/5.1.0) 2020-12-17

## [[5.0.0]](https://github.com/iExecBlockchainComputing/iexec-common/releases/tag/5.0.0) 2020-07-15

What's merged?
* feature/symlink
* feature/stdout-length
* feature/clean-stdout-string
* feature/rename
* feature/stdout
* feature/task-description-update
* feature/reformat
* feature/poco-5.1.0
* feature/tee-env
* feature/npe
* feature/fix
* feature/iexec-out
* feature/computed-json
* feature/dropbox
* feature/unit-test
* feature/v5-smart-contracts
* feature/unzip-update
* feature/unzip
* feature/encryption
* feature/sms-auth
* feature/credentials
* feature/iexechub
* feature/palaemon-templating
* feature/requester-params
* feature/jar-with-sources
* feature/result-proxy
* feature/tee-callback
* feature/tee-ipfs
