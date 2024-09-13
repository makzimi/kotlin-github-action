plugins {
    kotlin("multiplatform") version "2.0.0"
}

group = "com.example"
version = "0.0.1"

repositories {
    mavenCentral()
}

kotlin {

    js(IR) {
        useCommonJs()
        nodejs {
            binaries.executable()
        }
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(npm("@actions/core", "1.10.1"))
            }
        }
    }
}
