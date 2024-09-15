package com.example.webpack

import process

suspend fun main() {
    runCatching {
        val (name, buildDir, outputDir) = readArgs(process.argv)

        val webpackInputParams = WebpackInputParams(
            name = name,
            buildDir = buildDir,
            outputDir = outputDir,
        )

        val webpackManager = WebpackManager(webpackInputParams = webpackInputParams)
        webpackManager.fixWebpackEval()
        webpackManager.bundle()
    }.onFailure { throwable ->
        console.error(throwable)
        process.exit(1)
    }
}

data class Args(
    val name: String,
    val buildDirPath: String,
    val outputPath: String,
)

private fun readArgs(argv: Array<String>): Args {
    if (argv.size < 5) {
        throw IllegalArgumentException("We need 3 arguments: name, buildDirPath and outputPath")
    }

    return Args(
        name = argv[2],
        buildDirPath = argv[3],
        outputPath = argv[4],
    )
}
