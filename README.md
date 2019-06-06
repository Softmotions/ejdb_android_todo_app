# EJDB TODO APP

EJDB2 Android demo apllication

## Setup

1. Init ejdb2 submodule, run git command
    ```bash
    git clone https://github.com/Softmotions/ejdb_android_todo_app.git
    cd ./ejdb_android_todo_app
    git submodule update --init
    ```
1. Open Android SDK Manager and install LLDB, CMake (version >= 3.10), NDK.
1. Install [Ninja build system](https://ninja-build.org)
    ```bash
    apt-get install ninja-build
    ```

## Create local.properties
Set local android SDK/NDK path and target `arch` in `local.properties`

```properties
# Path to Android SDK dir
sdk.dir=/Android-sdk

# Path to Android NDK dir
ndk.dir=/Android-sdk/ndk-bundle

# Target abi name: armeabi-v7a, arm64-v8a, x86, x86_64
abi.name=arm64-v8a
```

## Assemble apk
```bash
./gradlew assembleDebug
or
./gradlew assembleRelease
```

## Install to connected device
```bash
./gradlew installDebug
or
./gradlew installRelease
```