package com.aytachuseynli.nothing

// Example demonstrating Generics, Covariance, and Contravariance in Kotlin

// GENERIC CLASS EXAMPLE
// A simple Box class that works with any type T
class Box<T>(var item: T) {
    fun fetchItem(): T { // Renamed method to `fetchItem`
        return item
    }
}

fun testGenericBox() {
    val intBox = Box(5)  // T will be Int
    val stringBox = Box("Hello")  // T will be String

    println(intBox.fetchItem())  // Output: 5
    println(stringBox.fetchItem())  // Output: Hello
}


// GENERIC FUNCTION EXAMPLE
// A generic function that works with any type T
fun <T> printItem(item: T) {
    println(item)
}

fun testGenericFunction() {
    printItem(10)  // Output: 10
    printItem("Hello")  // Output: Hello
}


// TYPE CONSTRAINTS EXAMPLE
// A generic function with a constraint: T must be a subtype of Number
fun <T: Number> doubleValue(value: T): Double {
    return value.toDouble() * 2
}

fun testTypeConstraints() {
    println(doubleValue(5))  // Output: 10.0
    println(doubleValue(3.5))  // Output: 7.0
}


// COVARIANCE EXAMPLE (out keyword)
// Covariance allows a type to be more general.
// This means we can assign a List<String> to a List<Any> if we mark it as covariant (out T).
class CovariantBox<out T>(val item: T)

fun testCovariance() {
    val stringBox: CovariantBox<String> = CovariantBox("Hello")
    val anyBox: CovariantBox<Any> = stringBox  // Covariance allows this assignment

    println(anyBox.item)  // Output: Hello
}


// CONTRAVARIANCE EXAMPLE (in keyword)
// Contravariance allows a type to be more specific.
// This means we can assign a Printer<Any> to a Printer<String> if we mark it as contravariant (in T).
class Printer<in T> {
    fun printItem(item: T) {
        println(item)
    }
}

fun testContravariance() {
    val stringPrinter: Printer<String> = Printer()
    val anyPrinter: Printer<String> = stringPrinter  // Contravariance allows this assignment

    stringPrinter.printItem("Hello")  // Output: Hello
    anyPrinter.printItem(123.toString())  // Valid because Printer<Any> can accept any type
}

fun main() {
    println("Generic Class Example:")
    testGenericBox()

    println("\nGeneric Function Example:")
    testGenericFunction()

    println("\nType Constraints Example:")
    testTypeConstraints()

    println("\nCovariance Example:")
    testCovariance()

    println("\nContravariance Example:")
    testContravariance()
}
