package com.example.webpack.externals

@JsModule("webpack")
external fun webpack(config: dynamic, callback: (error: Error?, stats: dynamic) -> Unit)
