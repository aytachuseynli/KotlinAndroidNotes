package com.aytachuseynli.nothing.FlowExamples

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// UserStateFlow.kt
fun mainUserStateFlow() = runBlocking {
    val userStateFlow = MutableStateFlow(false) // User is initially not logged in

    // Simulate user logging in
    userStateFlow.value = true

    // Collecting the user login state
    launch {
        userStateFlow.collect { isLoggedIn ->
            if (isLoggedIn) {
                println("User is logged in - Show account screen")
            } else {
                println("User is logged out - Show login screen")
            }
        }
    }
}

// SharedFlow.kt
fun mainSharedFlow() = runBlocking {
    val newMessageFlow = MutableSharedFlow<String>()

    // Simulate message reception
    launch {
        newMessageFlow.emit("Hello, you've got a message!")
    }

    // Collect new messages
    launch {
        newMessageFlow.collect { message ->
            println("New message: $message")
        }
    }
}

// StateFlow.kt
fun mainStateFlow() = runBlocking {
    val scoreFlow = MutableSharedFlow<Int>(replay = 1)

    // Update the score to 3
    launch { scoreFlow.emit(3) }

    // New collector joins and gets the last emitted score
    launch {
        scoreFlow.collect { score ->
            println("Current Score: $score")
        }
    }
}
