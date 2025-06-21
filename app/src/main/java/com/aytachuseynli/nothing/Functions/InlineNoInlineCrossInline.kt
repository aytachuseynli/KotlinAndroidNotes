package com.aytachuseynli.nothing.Functions

fun main() {
    greet() // Prints "Hello, how are you?" immediately
    greet() // Prints it again

    playGame {
        println("Game is being played!")
    }

    createGame {
        println("Game is being played!")
        // return // This is not allowed here due to crossinline restrictions.
    }
}

/**
 * 1. What is `inline`?
 * 
 * An inline function is expanded at the call site, meaning its code is directly inserted 
 * wherever it's called. This can improve performance by avoiding function call overhead, 
 * especially with lambda parameters.
 */
inline fun greet() {
    println("Hello, how are you?")
}

/**
 * 2. What is `noinline`?
 * 
 * Normally, lambdas passed to an inline function are also inlined. However, if you want 
 * to pass a lambda as a value (e.g., to store it or pass it somewhere else), you can mark 
 * it with `noinline`. This prevents it from being inlined.
 */
inline fun playGame(noinline action: () -> Unit) {
    println("Game is starting!")
    action() // Executed without being inlined
    println("Game has ended!")
}

/**
 * 3. What is `crossinline`?
 * 
 * Sometimes you need to pass a lambda into another execution context (like a thread or 
 * Runnable), but you still want to inline the function. If the lambda includes non-local 
 * control flow like `return`, it would cause issues. `crossinline` prevents non-local 
 * returns in such cases, ensuring the lambda behaves safely when passed around.
 */
inline fun createGame(crossinline action: () -> Unit) {
    println("Game is starting!")

    val newGame = Runnable {
        action() // Cannot return from here due to crossinline
    }

    Thread(newGame).start()

    println("Game has ended!")
}

/**
 * Summary:
 * - `inline`: Replaces the function call with the function body to improve performance.
 * - `noinline`: Prevents a lambda from being inlined, useful when passing lambdas around.
 * - `crossinline`: Prevents non-local returns from a lambda passed to another context (e.g., thread).
 */
