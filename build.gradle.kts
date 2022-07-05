/*
 * Copyright 2021 Comcast Cable Communications Management, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */

plugins {
    kotlin("multiplatform") version "1.6.21"
    kotlin("plugin.serialization") version "1.6.21"
    id("maven-publish")
}

group = "com.comcast.vrex.sdk"
version = "1.0.0"

repositories {
    jcenter()
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(BOTH) {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    macosX64 {
        binaries {
            sharedLib {
                baseName = "speech_sdk"
            }
            staticLib {
                baseName = "speech_sdk"
            }
        }
    }
    linuxX64 {
        binaries {
            sharedLib {
                baseName = "speech_sdk"
            }
            staticLib {
                baseName = "speech_sdk"
            }
        }
    }
//    linuxArm32Hfp {}
//    linuxMips32 {}
//    linuxMipsel32 {}
    mingwX64 {
        binaries {
            sharedLib {
                baseName = "speech_sdk"
            }
            staticLib {
                baseName = "speech_sdk"
            }
        }
    }
//    mingwX86 {}

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
                implementation("com.benasher44:uuid:0.4.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
        val linuxX64Main by getting
        val linuxX64Test by getting
        val macosX64Main by getting
        val macosX64Test by getting
//        val linuxArm32HfpMain by getting
//        val linuxArm32HfpTest by getting
//        val linuxMips32Main by getting
//        val linuxMips32Test by getting
//        val linuxMipsel32Main by getting
//        val linuxMipsel32Test by getting
        val mingwX64Main by getting
        val mingwX64Test by getting
//        val mingwX86Main by getting
//        val mingwX86Test by getting
    }
}
