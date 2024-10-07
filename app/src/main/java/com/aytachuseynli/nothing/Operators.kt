package com.aytachuseynli.nothing


fun main() {
    // Sample data
    val numbers = listOf(1, 2, 3, 4, 5, 6)
    val names = listOf("Alice", "Bob", "Charlie", "David")

    // 1. map: Squares each number and returns a new list.
    val squares = numbers.map { it * it }
    println("Squares: $squares") // [1, 4, 9, 16, 25, 36]

    // 2. filter: Selects only the even numbers and returns a new list.
    val evenNumbers = numbers.filter { it % 2 == 0 }
    println("Even Numbers: $evenNumbers") // [2, 4, 6]

    // 3. reduce: Sums all the numbers and returns a single result.
    val sum = numbers.reduce { acc, number -> acc + number }
    println("Sum: $sum") // 21

    // 4. flatMap: Creates a flat list by multiplying each number by 1, 2, and 3.
    val result = numbers.flatMap { listOf(it * 1, it * 2, it * 3) }
    println("FlatMap Result: $result") // [1, 2, 3, 2, 4, 6, 3, 6, 9, 4, 8, 12]

    // 5. distinct: Removes duplicate numbers and returns only unique values.
    val duplicateNumbers = listOf(1, 2, 2, 3, 4, 4)
    val distinctNumbers = duplicateNumbers.distinct()
    println("Distinct Numbers: $distinctNumbers") // [1, 2, 3, 4]

    // 6. groupBy: Groups numbers into "Even" and "Odd" categories.
    val grouped = numbers.groupBy { if (it % 2 == 0) "Even" else "Odd" }
    println("Grouped: $grouped") // {Odd=[1, 3, 5], Even=[2, 4, 6]}

    // 7. first and last: Retrieves the first and last numbers.
    val firstNumber = numbers.first()
    val lastNumber = numbers.last()
    println("First Number: $firstNumber, Last Number: $lastNumber") // First Number: 1, Last Number: 6

    // 8. any and all:
    // any: Checks if at least one number is even.
    val hasEven = numbers.any { it % 2 == 0 }
    // all: Checks if all numbers are even.
    val allEven = numbers.all { it % 2 == 0 }
    println("Has Even: $hasEven, All Even: $allEven") // Has Even: true, All Even: false

    // 9. find: Finds the first even number, returns null if not found.
    val firstEven = numbers.find { it % 2 == 0 }
    println("First Even: $firstEven") // First Even: 2

    // 10. sorted and sortedBy:
    // sorted: Sorts the numbers in ascending order.
    val sortedNumbers = numbers.sorted()
    // sortedBy: Sorts names by their length.
    val sortedNames = names.sortedBy { it.length }
    println("Sorted Numbers: $sortedNumbers") // [1, 2, 3, 4, 5, 6]
    println("Sorted Names: $sortedNames") // [Bob, Alice, David, Charlie]

//    11. zip: Combines two lists into pairs.
    data class User(val name: String, val age: Int)

    val usersName = listOf("Alice", "Bob", "Charlie")
    val ages = listOf(25, 30, 35)

    val users = usersName.zip(ages) { name, age -> User(name, age) }
    println("Users: $users") // [User(name=Alice, age=25), User(name=Bob, age=30), User(name=Charlie, age=35)]

}
