plugins {
    kotlin("multiplatform") version "2.0.0"
}

group = "com.example"
version = "0.0.1"

repositories {
    mavenCentral()
    jcenter()
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

                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.7.2")
                implementation("org.jetbrains.kotlinx:kotlinx-nodejs:0.0.7")
            }
        }
    }
}

tasks.named("clean") {
    finalizedBy("cleanDist")
}

tasks.register<Delete>("cleanDist") {
    delete(layout.projectDirectory.dir("dist"))
    delete(layout.projectDirectory.dir("node_modules"))
}
