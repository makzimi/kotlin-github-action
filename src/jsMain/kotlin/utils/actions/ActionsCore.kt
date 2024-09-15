@file:JsModule("@actions/core")
package com.example.utils.actions

external interface InputOptions {
    var required: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external fun setOutput(name: String, value: Any)
external fun setFailed(message: String)
external fun getInput(name: String, options: InputOptions = definedExternally): String

external fun startGroup(name: String)
external fun endGroup()
