package com.example.utils.actions

interface Reportable {
    fun report(message: String)
}

class ReportableImpl(
    private val groupName: String
) : Reportable {
    override fun report(message: String) {
        println("$groupName: $message")
    }
}

inline fun <T> group(name: String, action: Reportable.() -> T): T {
    startGroup(name)
    try {
        return ReportableImpl(groupName = name).action()
    } finally {
        endGroup()
    }
}
