package com.aytachuseynli.nothing.Functions



fun main() {
    val sum = operateOnNumbers(5, 3) { x, y -> x + y }
    val product = operateOnNumbers(5, 3) { x, y -> x * y }

    println("Sum: $sum")
    println("Product: $product")



    val numbers = listOf(1, 2, 3, 4, 5)
    val evenNumbers = filterNumbers(numbers) { it % 2 == 0 }
    println(evenNumbers)

    performOperation {
        println("Operation is being performed inline" )
    }

    performOperationWithNoInline {
        println("Operation is being performed  noinline")
    }

    performOperationWithCrossInline {
        println("Operation is being performed crossinline")
        // return // Bu, derleme hatasına neden olur
    }

}

/***
    Yüksek Seviyeli Fonksiyon: Başka bir fonksiyonu argüman olarak alabilen veya bir fonksiyonu döndüren fonksiyondur.
    Lambda İfadesi: Adlandırılmamış fonksiyonlardır; genellikle bir fonksiyona geçici bir fonksiyon sağlamak için kullanılır.
 ***/

fun operateOnNumbers(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
    return operation(a, b)
}


fun filterNumbers(numbers: List<Int>, predicate: (Int) -> Boolean): List<Int> {
    val result = mutableListOf<Int>()
    for (number in numbers) {
        if (predicate(number)) {
            result.add(number)
        }
    }
    return result
}

inline fun performOperation(operation: () -> Unit) {
    println("Before operation")
    operation() // Lambda burada yerleştirilir
    println("After operation")
}

inline fun performOperationWithNoInline(noinline operation: () -> Unit) {
    println("Before operation")
    operation() // Bu lambda inline edilmez
    println("After operation")
}

inline fun performOperationWithCrossInline(crossinline operation: () -> Unit) {
    println("Before operation")

    // Yeni bir coroutine içinde veya bir başka kontekste çağrılabilir
    val runnable = Runnable {
        operation() // Bu durumda return yapamaz
    }

    runnable.run()

    println("After operation")
}