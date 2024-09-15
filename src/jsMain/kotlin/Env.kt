package com.example

import NodeJS.get
import kotlin.reflect.KProperty
import process

object Env {
    val GITHUB_REPOSITORY by EnvironmentVariable
    val GITHUB_TOKEN by EnvironmentVariable
}

private object EnvironmentVariable {
    operator fun getValue(environment: Any, property: KProperty<*>): String =
        process.env[property.name] ?: throw ActionFailedException("${property.name} is not found in process.env")
}

class ActionFailedException(override val message: String, cause: Throwable?): Throwable(message, cause) {
    constructor(message: String): this(message, null)
}
