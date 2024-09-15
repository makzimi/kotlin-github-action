package com.example.ncc

import com.example.objectwrapper.jsObject
import com.example.ncc.externals.fs.writeFileSync
import fs.MakeDirectoryOptions
import fs.mkdirSync
import com.example.ncc.externals.AssetMap
import com.example.ncc.externals.forEach
import com.example.ncc.externals.ncc
import kotlinx.coroutines.await
import path.path

data class CombinedCode(
    val inputPath: String,
    val outputPath: String,
    val fileName: String,
    val code: String,
    val mapping: String?,
    val assets: AssetMap?,
)

fun CombinedCode.copyCode(fileName: String) {
    writeFileSync(
        path = path.join(outputPath, fileName),
        data = code,
        options = jsObject(),
    )
}

fun CombinedCode.copyMapping() {
    mapping?.also { nonNullMapping ->
        writeFileSync(
            path = path.join(outputPath, "$fileName.map"),
            data = nonNullMapping,
            options = jsObject()
        )
    }
}

fun CombinedCode.copyAssets() {
    assets?.forEach { (assetFileName, asset) ->
        val assetFilePath = path.join(outputPath, assetFileName)

        mkdirSync(
            path = path.dirname(assetFilePath),
            options = jsObject<MakeDirectoryOptions> {
                recursive = true
            }
        )

        writeFileSync(
            path = assetFilePath,
            data = asset.source,
            options = jsObject {
                mode = asset.permissions
            }
        )
    }
}

suspend fun combineCode(
    inputPath: String,
    outputPath: String,
    fileName: String,
): CombinedCode {
    val nccResult = ncc(
        input = inputPath,
        options = jsObject {
            sourceMap = true
            license = "LICENSES"
        }
    ).await()

    return CombinedCode(
        inputPath = inputPath,
        outputPath = outputPath,
        fileName = fileName,
        code = nccResult.code,
        mapping = nccResult.map,
        assets = nccResult.assets,
    )
}

internal fun createOutputFolder(outputPath: String) {
    mkdirSync(
        path = outputPath,
        options = jsObject<MakeDirectoryOptions> {
            recursive = true
        }
    )
}