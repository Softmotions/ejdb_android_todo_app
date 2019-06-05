# EJDB TODO APP

## Build and start

### Setup

1. Init ejdb2 submodule, run git command `git submodule update --init`
2. Open SDK Manager and install LLDB, CMake (version >= 3.10), NDK.
3. Install ninja build tool

### Create local.properties
```properties
# Path to Android SDK dir
ndk.dir=/path/to/Sdk/ndk-bundle

# Path to Android NDK dir
sdk.dir=/path/to/Sdk

# Target abi name: armeabi-v7a, arm64-v8a, x86, x86_64
abi.name=arm64-v8a
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