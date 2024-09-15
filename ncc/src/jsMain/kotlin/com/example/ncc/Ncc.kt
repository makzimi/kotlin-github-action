package com.example.ncc

import process

suspend fun main() {
    runCatching {
        val (inputPath, outputPath) = readArgs(process.argv)

        val combinedCode = combineCode(
            inputPath = inputPath,
            outputPath = outputPath,
            fileName = "index.js"
        )

        createOutputFolder(outputPath = outputPath)

        with(combinedCode) {
            copyCode(fileName = "index.js")
            copyMapping()
            copyAssets()
        }
    }.onFailure { throwable ->
        console.error(throwable)
        process.exit(1)
    }
}

data class Args(
    val inputPath: String,
    val outputPath: String,
)

private fun readArgs(argv: Array<String>): Args {
    if (argv.size < 4) {
        throw IllegalArgumentException("We need 2 arguments: inputPath and outputPath")
    }

    return Args(
        inputPath = argv[2],
        outputPath = argv[3],
    )
}