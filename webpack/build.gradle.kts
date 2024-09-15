import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsExec

plugins {
    kotlin("multiplatform") version "2.0.0"
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
                implementation(project(":objectwrapper"))
                implementation(npm("webpack", "5.94.0"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
                implementation("org.jetbrains.kotlinx:kotlinx-nodejs:0.0.7")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-js:1.0.0-pre.785")
            }
        }
    }
}

repositories {
    mavenCentral()
    jcenter()
}

tasks.named<NodeJsExec>("jsNodeProductionRun") {
    val name = rootProject.name
    val buildDir = "${rootProject.layout.buildDirectory.get()}"
    val outputPath = "${rootProject.layout.projectDirectory}/dist/"
    args(name, buildDir, outputPath)
}
