## AppToDoList

## Properties

Project uses ejdb2, clone lib from https://github.com/Softmotions/ejdb.git

Create local.properties
```properties
ndk.dir=/path/to/Sdk/ndk-bundle
sdk.dir=/path/to/Sdk

# target abi name
abi.name=arm64-v8a
ejdb.path=path/to/ejdb/CMakeLists.txt
```

## Build and start

Assemble apk
```bash
./gradlew assembleDebug
or
./gradlew assembleRelease
```
Install to connected device
```bash
./gradlew installDebug
or
./gradlew installRelease
```