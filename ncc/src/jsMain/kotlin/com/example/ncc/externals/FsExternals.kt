package com.example.ncc.externals

@JsModule("fs")
@JsNonModule
external object fs {
    // Assuming T$45 is a specific options type, you need to declare it
    interface WriteFileOptions {
        var encoding: String?
        var mode: Number?
        var flag: String?
    }

    // Declare the writeFileSync function
    @JsName("writeFileSync")
    fun writeFileSync(path: String, data: Any, options: WriteFileOptions = definedExternally)
}
