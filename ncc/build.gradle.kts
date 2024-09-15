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
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
                implementation("org.jetbrains.kotlinx:kotlinx-nodejs:0.0.7")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-js:1.0.0-pre.785")
                implementation(npm("@vercel/ncc", "0.38.1", generateExternals = false))
            }
        }
    }
}

repositories {
    mavenCentral()
    jcenter()
}

tasks.named<NodeJsExec>("jsNodeProductionRun") {
    val inputPath = "${rootProject.layout.buildDirectory.get()}/js/packages/${rootProject.name}/kotlin/${rootProject.name}.js"
    val outputPath = "${rootProject.layout.projectDirectory}/dist/"
    args(inputPath, outputPath)
}