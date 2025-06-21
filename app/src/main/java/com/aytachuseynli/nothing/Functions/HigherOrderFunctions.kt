package com.aytachuseynli.nothing.Functions

fun main() {
    val sum = operateOnNumbers(5, 3) { x, y -> x + y }
    val product = operateOnNumbers(5, 3) { x, y -> x * y }

    println("Sum: $sum")
    println("Product: $product")

    val numbers = listOf(1, 2, 3, 4, 5)
    val evenNumbers = filterNumbers(numbers) { it % 2 == 0 }
    println("Even Numbers: $evenNumbers")

    performOperation {
        println("Operation is being performed inline")
    }

    performOperationWithNoInline {
        println("Operation is being performed with noinline")
    }

    performOperationWithCrossInline {
        println("Operation is being performed with crossinline")
        // return // This would cause a compilation error
    }
}

/**
 * High-Order Function:
 * A function that either takes another function as a parameter
 * or returns a function as a result.
 *
 * Lambda Expression:
 * An anonymous function usually used for passing short blocks of code.
 */

// Applies a given operation to two numbers
fun operateOnNumbers(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
    return operation(a, b)
}

// Filters a list of numbers based on a condition
fun filterNumbers(numbers: List<Int>, predicate: (Int) -> Boolean): List<Int> {
    val result = mutableListOf<Int>()
    for (number in numbers) {
        if (predicate(number)) {
            result.add(number)
        }
    }
    return result
}

// Inline function: inlines the lambda for performance optimization
inline fun performOperation(operation: () -> Unit) {
    println("Before operation")
    operation() // Lambda is inlined here
    println("After operation")
}

// Noinline: prevents lambda from being inlined
inline fun performOperationWithNoInline(noinline operation: () -> Unit) {
    println("Before operation")
    operation() // Lambda is not inlined, can be stored or passed around
    println("After operation")
}

// Crossinline: prevents non-local returns from the lambda
inline fun performOperationWithCrossInline(crossinline operation: () -> Unit) {
    println("Before operation")

    val runnable = Runnable {
        operation() // Cannot return from here due to crossinline restriction
    }

    runnable.run()

    println("After operation")
}
