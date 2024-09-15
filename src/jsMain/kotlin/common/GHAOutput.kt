package com.example.common

data class GHAOutput(
    val success: Boolean,
    val result: String,
    val errorText: String? = null,
) {
    enum class Key(val value: String) {
        Result("result"),
        ;
    }
}

