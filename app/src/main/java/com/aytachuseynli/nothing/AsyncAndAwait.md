# When to Use `async` and `await` in Kotlin

## 1️⃣ What Are `async` and `await` Used For?
- `async {}` launches a **parallel/asynchronous** task and returns a **Deferred result**.
- `await()` waits for the **completion** of the asynchronous operation and retrieves the result.

---

## 2️⃣ When Should You Use It?

✅ **For Independent and Parallel Tasks**  
> **Example:** Fetching data from a database, an API, and a file at the same time.  
```kotlin
val moviesAsync = async { getMovies() }
val seriesAsync = async { getTvSeries() }
val genresAsync = async { getGenres() }

val movies = moviesAsync.await()
val series = seriesAsync.await()
val genres = genresAsync.await()
```
📌 **Using `async` here is correct because the tasks are independent and can run in parallel.**  

---

✅ **For Running Multiple API Calls Concurrently**  
> **Example:** Fetching user profile and past orders at the same time.
```kotlin
val profileAsync = async { api.getUserProfile() }
val ordersAsync = async { api.getUserOrders() }

val profile = profileAsync.await()
val orders = ordersAsync.await()
```
📌 **Using `async` reduces waiting time because API calls execute simultaneously.**

---

## 3️⃣ When Should You NOT Use It?

❌ **For Dependent Operations**  
> If one task depends on the result of another, **do not use `async`**.
```kotlin
val userId = getUserId() // Must get user ID first
val profile = async { api.getUserProfile(userId) }.await() // Then fetch profile
```
📌 **`async` is unnecessary here because the operations must run sequentially.**  
**Instead, do this:**  
```kotlin
val userId = getUserId()
val profile = api.getUserProfile(userId) // Direct call
```

---

❌ **For a Single API or IO Operation**  
> If there's only **one** task, `async` is unnecessary.
```kotlin
val profile = async { api.getUserProfile() }.await() // UNNECESSARY!
```
📌 **Instead, call it directly:**
```kotlin
val profile = api.getUserProfile()
```

---

❌ **To Make Synchronous Code Asynchronous**  
> If the code is already **fast and non-blocking**, using `async` won't help.
```kotlin
val result = async { simpleCalculation() }.await() // UNNECESSARY!
```
📌 **Instead, call it directly:**
```kotlin
val result = simpleCalculation()
```

---

## **Summary 📌**
| **Scenario** | **Should You Use `async`?** |
|-------------|---------------------------|
| Independent tasks running in parallel? | ✅ Yes |
| Multiple API calls running concurrently? | ✅ Yes |
| Operations depend on each other? | ❌ No |
| Single IO (API, DB) operation? | ❌ No |
| CPU-intensive calculations? | ❌ No |

💡 **General Rule:** Use `async` only when you have **multiple independent tasks** that should run in parallel. If tasks must run **sequentially**, `async` is unnecessary and won’t improve performance. 🚀

