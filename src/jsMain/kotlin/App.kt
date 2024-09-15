package com.example

import com.example.common.GHAInput
import com.example.common.GHAOutput
import com.example.utils.actions.group
import com.example.utils.actions.setFailed
import com.example.utils.actions.setOutput
import com.example.common.GHAInput.Key.Owner
import com.example.common.GHAInput.Key.Repo
import com.example.common.GHAInput.Key.TestInput
import com.example.common.getOrDefault
import com.example.common.getOrNull
import com.example.common.GHAOutput.Key.Result
import kotlinx.coroutines.delay

suspend fun main() {
    val input = buildGHAInput()

    try {
        group("Action body") {
            val output = runAction(input = input)

            if (output.success) {
                setOutput(Result.value, output.result)
            } else {
                setFailed(output.errorText.orEmpty())
            }
        }
    } catch (e: Exception) {
        setFailed("Error while performing GitHub Action: ${e.message}")
    }
}

private suspend fun runAction(input: GHAInput): GHAOutput {
    println("run action with testInput = ${input.testInput}")
    delay(1000)
    println("finish action...")
    return GHAOutput(
        success = true,
        result = "Success result for \"${input.testInput}\"",
        errorText = null,
    )
}

private fun buildGHAInput(): GHAInput = group("Reading input values") {
    val repoAsList = Env.GITHUB_REPOSITORY.split("/")
    return@group GHAInput(
        token = Env.GITHUB_TOKEN,
        owner = Owner.getOrDefault { repoAsList.first() },
        repo = Repo.getOrDefault { repoAsList[1] },
        testInput = TestInput.getOrNull(),
    )
}
