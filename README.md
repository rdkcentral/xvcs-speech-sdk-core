# vrex-speech-sdk

This is a multiplatform Speech SDK which supports the following targets:
1. JVM
1. Native (i.e Linux X64, MacOS X64, etc)
1. NodeJs

#### More about native targets:
1. More native targets can be added as requested.
1. Please see this documentation for available targets: https://kotlinlang.org/docs/mpp-supported-platforms.html
1. A dynamic or a static library can be generated which can then be used by C/C++ programs.

## Demos
https://github.com/rdkcentral/xvcs-speech-sdk-demo

## Usage
Please see the above demo and follow comments under Demo.kt file. The same sequence of steps apply regardless of the platform.

## Build instructions

1. Clone the repository `git clone git@github.com:rdkcentral/xvcs-speech-sdk-core.git`
1. Follow instructions from the appropriate platform below

### JVM
1. In order to publish the library to the local maven repository run `gradle publishToMavenLocal` task
1. The dependency now can be included in your build system
    1. Gradle - `implementation("com.comcast.vrex.sdk:vrex-speech-sdk-jvm:1.0.0")`
       (ensure that `mavenLocal()` is under `repositories` block)
1. See the JVM demo in the `Demos` section above for a complete usage example

### Native Targets

For native targets, a dynamic library and a header file will be created so that you can use the SDK from C/C++ projects.
We will use `macosX64` target used in a C++ project as an example. But same instructions apply to other targets as well.

1. Run the Gradle task `gradle linkReleaseSharedMacosX64` in order generate the above 2 files.
    1. These files can be found in `/build/bin/releaseShared`
    1. The dynamic libary is named `libspeech_sdk.dylib`
    1. The header file is named `libspeech_sdk_api.h`
1. Copy/include both files to your C++ project
1. Include the header file in the project `#include "libspeech_sdk_api.h"`
1. Library can be accessed with following code `libspeech_sdk_ExportedSymbols* lib = libspeech_sdk_symbols();`

