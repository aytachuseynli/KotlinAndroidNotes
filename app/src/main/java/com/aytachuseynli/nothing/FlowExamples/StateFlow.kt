package com.aytachuseynli.nothing.FlowExamples

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    // Creating a StateFlow for data streaming.
    // Initial value is set to 5.
    val stateFlow = MutableStateFlow(5)

    // Updating the value
    stateFlow.value = 1
    stateFlow.value = 2

    // Collector subscribes later
    launch {
        stateFlow.collect { value ->
            println("StateFlow Collector: $value")
        }
    }

    // Emitting a new value
    stateFlow.value = 8

    // Notes about StateFlow:
    /*
    StateFlow:
    - StateFlow represents a state that can be continuously updated.
    - It starts with an initial value and retains that value.
    - StateFlow always holds the latest value, and new collectors receive this value.
    - StateFlow carries only one value, thus always represents a state.
    - StateFlow can continuously update the last value over time.
    - Use case: Ideal for managing UI state, tracking user login status, etc.
    - StateFlow guarantees that collectors always receive the most recent value.
    */
}
