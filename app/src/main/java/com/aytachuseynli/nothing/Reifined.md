## `reified` Keyword in Kotlin

In Kotlin, the `reified` keyword is used with **inline functions** to allow access to the actual type arguments of a **generic type** at runtime. Normally, due to **type erasure** in Java and Kotlin, generic type parameters are not available at runtime. However, using `reified`, you can retain the type information.

### 🔹 Why Do We Need `reified`?
When working with generics in Kotlin, the type parameters are **erased** at runtime. This means you **cannot** do something like this:

```kotlin
fun <T> getClassType(): Class<T> {
    return T::class.java  // ❌ ERROR: Cannot use 'T' as a reified type parameter
}
```

But with `reified`, you **can**:

```kotlin
inline fun <reified T> getClassType(): Class<T> {
    return T::class.java  // ✅ Works because T is now "reified"
}
```

### 🔹 Example Usage

#### ✅ **Checking Type at Runtime**
Without `reified`, you have to pass `Class<T>` explicitly:

```kotlin
fun <T> isType(value: Any, clazz: Class<T>): Boolean {
    return clazz.isInstance(value)
}

val result = isType("Hello", String::class.java)  // true
```

With `reified`, you **don’t need to pass `Class<T>`**:

```kotlin
inline fun <reified T> isType(value: Any): Boolean {
    return value is T
}

val result = isType<String>("Hello")  // true
```

#### ✅ **Simplifying Generics in JSON Parsing**
Assume you are using `Gson` to parse JSON:

```kotlin
fun <T> parseJson(json: String, clazz: Class<T>): T {
    return Gson().fromJson(json, clazz)
}
```

With `reified`, you don’t need to pass `Class<T>`:

```kotlin
inline fun <reified T> parseJson(json: String): T {
    return Gson().fromJson(json, T::class.java)
}

val user: User = parseJson<User>(jsonString)  // No need to pass Class<User>
```

### 🔹 When Can You Use `reified`?
- Only inside **inline functions**
- When you need access to **generic type information at runtime**

### 🔹 Limitations of `reified`
- Cannot be used in **normal (non-inline) functions**
- Cannot be used in **class-level generics**
- Increases method count if overused (due to inlining)

### 🔹 Conclusion
The `reified` keyword is useful for retaining **generic type information** at runtime, simplifying **type checks** and **JSON parsing**, and making code **cleaner** by eliminating explicit `Class<T>` parameters.


## Scenario: Fetching Data from a Database for Different Types

Let's say you have a database layer in your app, and you want to create a generic function to fetch data of different types (e.g., `User`, `Product`). Using `reified`, you can get the type information at runtime and perform actions based on that.

### Example Code:

```kotlin
// A generic function to fetch data from the database
inline fun <reified T> fetchDataFromDatabase(): List<T> {
    // Database mock
    val database = Database()

    return when (T::class) {
        User::class -> database.getUsers() as List<T>  // Fetch User data
        Product::class -> database.getProducts() as List<T>  // Fetch Product data
        else -> throw IllegalArgumentException("Unsupported type")
    }
}

class Database {
    fun getUsers(): List<User> {
        // Return a list of users from the database
        return listOf(User("Alice"), User("Bob"))
    }

    fun getProducts(): List<Product> {
        // Return a list of products from the database
        return listOf(Product("Laptop"), Product("Phone"))
    }
}

data class User(val name: String)
data class Product(val name: String)

fun main() {
    // Fetch User data
    val users: List<User> = fetchDataFromDatabase()
    println(users)

    // Fetch Product data
    val products: List<Product> = fetchDataFromDatabase()
    println(products)
}
