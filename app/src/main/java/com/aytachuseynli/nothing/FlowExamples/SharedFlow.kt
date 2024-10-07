package com.aytachuseynli.nothing.FlowExamples

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    // Creating a SharedFlow for data streaming.
    // replay = 3 means that the last 3 values will be stored and available for new subscribers.
    val sharedFlow = MutableSharedFlow<Int>(replay = 3)

    // Emitting data
    sharedFlow.emit(1)
    sharedFlow.emit(2)
    sharedFlow.emit(3)
    sharedFlow.emit(4)
    sharedFlow.emit(5)

    // Collector subscribes later
    launch {
        sharedFlow.collect { value ->
            println("SharedFlow Collector: $value")
        }
    }

    // Emitting new data
    sharedFlow.emit(6)

    // Notes about SharedFlow:
    /*
    SharedFlow:
    - SharedFlow is designed for multiple collectors to receive the same data.
    - The replay parameter determines how many previous values are stored for new subscribers.
    - Each emit call sends data to all current collectors.
    - Collectors can collect data whenever they want, preventing data loss.
    - Use case: Ideal for notification systems, events, and state management.
    - Difference from StateFlow: StateFlow holds only the latest state, while SharedFlow can hold multiple values.
    */
}
