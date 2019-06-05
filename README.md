# EJDB TODO APP

## Build and start

### Setup

Init ejdb2 submodule, run git command
```
git submodule init
git submodule update
```
1. Open SDK Manager and install LLDB, CMake (version >= 3.10), NDK.
2. Provide cmake dir, add /path/to/Sdk/cmake/version/bin to system property PATH
3. Set ANDROID_NDK_HOME="/path/to/Sdk/ndk-bundle" system property

### Create local.properties
```properties
ndk.dir=/path/to/Sdk/ndk-bundle
sdk.dir=/path/to/Sdk

# target abi name
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