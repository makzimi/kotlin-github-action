package com.example.common

import com.example.utils.actions.getInput

data class GHAInput(
    val owner: String,
    val repo: String,
    val token: String,
    val testInput: String? = null,
 ) {
    enum class Key(val value: String) {
        Owner("owner"),
        Repo("repo"),
        TestInput("testInput"),
        ;
    }
}

fun GHAInput.Key.get(): String {
    return getInput(name = this.value)
}

fun GHAInput.Key.getOrDefault(default: () -> String): String {
    return getInput(name = this.value).ifEmpty(default)
}

fun GHAInput.Key.getOrNull(): String? {
    return getInput(name = this.value).ifEmpty { null }
}
