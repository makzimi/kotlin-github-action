package com.example.webpack

import fs.readFileSync
import fs.readdirSync
import fs.statSync
import com.example.objectwrapper.jsObject
import com.example.webpack.externals.webpack
import com.example.webpack.externals.fs.writeFileSync
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class WebpackManager(
    private val webpackInputParams: WebpackInputParams
) {
    suspend fun bundle() = suspendCoroutine { cont ->
        val webpackConfig = webpackInputParams.toWebpackConfig()
        val config = generateWebpackConfigJsObject(webpackConfig)
        webpack(config) { error, _ ->
            if (error != null) {
                cont.resumeWithException(Throwable(error.message))
            } else {
                cont.resume(Unit)
            }
        }
    }

    fun fixWebpackEval() {
        val directory = webpackInputParams.toInputDirectory()
        recursiveFixWebpackEval(directory = directory)
    }

    private fun recursiveFixWebpackEval(directory: String) {
        val files = readdirSync(directory)
        files.forEach { file ->
            val path = "$directory/$file"
            if (statSync(path).isDirectory()) {
                recursiveFixWebpackEval(path)  // Recursively handle directories
            } else if (file.endsWith(".js")) {
                val content = readFileSync(path, "utf8") as String
                if (content.contains("eval('require')")) {
                    val fixedContent = content.replace("eval('require')", "require")
                    writeFileSync(path, fixedContent)
                }
            }
        }
    }

    private fun WebpackInputParams.toInputDirectory(): String {
        return "$buildDir/js/packages/$name/kotlin/"
    }

    private fun WebpackInputParams.toWebpackConfig(): WebpackConfig {
        return WebpackConfig(
            projectName = name,
            inputFilePath = "$buildDir/js/packages/$name/kotlin/$name.js",
            outputDirPath = outputDir,
            outputFileName = "index.js",
            modules = listOf(
                "$buildDir/js/node_modules",
                "$buildDir/js/packages/$name/node_modules",
                "$buildDir/js/packages_imported",
            ),
            aliases = mapOf(
                "node-fetch$" to "node-fetch/lib/index.js",
                "abort-controller$" to "abort-controller/dist/abort-controller.js",
            )
        )
    }

    private data class WebpackConfig(
        val projectName: String,
        val inputFilePath: String,
        val outputDirPath: String,
        val outputFileName: String,
        val modules: List<String>,
        val aliases: Map<String, String>,
    )

    private fun generateWebpackConfigJsObject(webpackConfig: WebpackConfig): dynamic {
        return jsObject {
            this.name = webpackConfig.projectName
            this.entry = webpackConfig.inputFilePath
            this.target = "node"
            this.output = jsObject {
                this.path = webpackConfig.outputDirPath
                this.filename = webpackConfig.outputFileName
                this.library = jsObject {
                    this.type = "commonjs2"
                }
            }
            this.resolve = jsObject {
                this.modules = webpackConfig.modules.toTypedArray()
                this.extensions = arrayOf(".js")
                this.alias = jsObject {
                    for (alias: Map.Entry<String, String> in webpackConfig.aliases.entries) {
                        this[alias.key] = alias.value
                    }
                }
            }
            this.mode = "development"
        }
    }
}
