# AppToDoList

## Build and start

Project uses ejdb2, clone lib from https://github.com/Softmotions/ejdb.git

### Setup

1. Open SDK Manager and install LLDB, CMake (version >= 3.10),NDK.
2. Provide ninja, add /path/to/Sdk/cmake/version/bin to PATH

### Create local.properties
```properties
ndk.dir=/path/to/Sdk/ndk-bundle
sdk.dir=/path/to/Sdk

# target abi name
abi.name=arm64-v8a
ejdb.path=path/to/ejdb/CMakeLists.txt
```

### Assemble apk
```bash
./gradlew assembleDebug
or
./gradlew assembleRelease
```
### Install to connected device
```bash
./gradlew installDebug
or
./gradlew installRelease
```