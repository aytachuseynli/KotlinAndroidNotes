
# API Call Handling with `sealed class` and `Result` Wrapper
---

## 1. `Result` Sealed Class

The `Result` class encapsulates the outcome of an API call, offering three distinct states: `Success`, `Error`, and `Loading`. By using this class, we can standardize how results from various API requests are handled and propagated.

```kotlin
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
```
---

## 2. `handleApiCallResult` Function with `inline`, `crossinline`, and `noinline`

In order to handle API calls while maintaining efficiency and managing state transitions, we implement a function that supports asynchronous operations with coroutines. This function leverages the `inline`, `crossinline`, and `noinline` modifiers to optimize performance and enforce the correct usage of lambdas.

```kotlin
inline fun <T> handleApiCallResult(
    crossinline apiCall: suspend () -> T,
    noinline onResult: (Result<T>) -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        onResult(Result.Loading) // Emit Loading state
        try {
            val response = apiCall()
            withContext(Dispatchers.Main) {
                onResult(Result.Success(response)) // Emit Success state
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                onResult(Result.Error(e.message ?: "An unknown error occurred")) // Emit Error state
            }
        }
    }
}
```

### Key Modifiers:
- **`inline`**: The function is inlined to avoid the creation of lambda objects, thus improving performance, especially in high-frequency operations like API calls.
- **`crossinline`**: Used to prevent `return` from being called within the `apiCall` lambda. This ensures that control flow within the lambda is always handled properly when used inside a coroutine.
- **`noinline`**: Allows the `onResult` lambda to be passed around as a variable, enabling deferred execution and invocation in different contexts, such as within `launch` or `withContext`.

---

## 3. Example Usage in a Fragment

The following example demonstrates how to use the `handleApiCallResult` function within a fragment to handle API responses effectively. The UI updates based on the result states—`Loading`, `Success`, and `Error`.

```kotlin
handleApiCallResult(
    apiCall = { userRepository.getUserProfile() },
    onResult = { result ->
        when (result) {
            is Result.Loading -> showLoading(true)
            is Result.Success -> {
                showLoading(false)
                binding.userNameText.text = result.data.name
            }
            is Result.Error -> {
                showLoading(false)
                Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
)

fun showLoading(isLoading: Boolean) {
    binding.progressBar.isVisible = isLoading
}
```
---

## 4. Summary of Function Modifiers (`inline`, `crossinline`, and `noinline`)

| Modifier        | Purpose                                   | Explanation                                                             |
|-----------------|-------------------------------------------|-------------------------------------------------------------------------|
| `inline`        | Performance optimization                 | Inlines the function body, avoiding the creation of additional lambda objects, thus improving performance. |
| `crossinline`   | Prevents early returns within lambdas     | Ensures that lambdas used within coroutines do not prematurely return, preserving the coroutine flow. |
| `noinline`      | Allows lambdas to be passed around as variables | Enables deferred execution by allowing lambdas to be stored and invoked in different contexts. |

---

## Conclusion

This approach provides a robust, flexible, and efficient way to handle API calls and manage UI state in Android applications. The use of the `Result` sealed class allows for clear separation of success, error, and loading states, while the `inline`, `crossinline`, and `noinline` modifiers optimize the function's performance and ensure that lambdas behave correctly in coroutines. By following this pattern, we ensure that our API handling code is both efficient and maintainable.
