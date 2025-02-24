# Retrofit and Kotlin Flow

## Call Adapters in Retrofit
Call Adapters are components that determine how Retrofit processes and transforms network call results.

### Main Functions
- Converting network call results (Call<T>) to different types
- Managing asynchronous calls
- Handling thread transitions

### Common Call Adapters

#### 1. RxJava Adapter
```kotlin
// API Definition
interface ApiService {
    @GET("users")
    fun getUsers(): Single<List<User>>
    
    @GET("posts")
    fun getPosts(): Observable<List<Post>>
    
    @GET("comments")
    fun getComments(): Flowable<List<Comment>>
}
```

#### 2. Coroutines Adapter
```kotlin
interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
    
    @GET("user/{id}")
    suspend fun getUser(id: Int): Response<User>
}
```

#### 3. Java 8 Adapter
```kotlin
interface ApiService {
    @GET("users")
    fun getUsers(): CompletableFuture<List<User>>
}
```

## Result<T> vs Resource<T>

### Result<T>
```kotlin
sealed class Result<out T> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val exception: Exception): Result<Nothing>()
}
```

Use cases:
- Simple network calls
- Success/error states only
- No caching mechanism
- Separate loading state management

### Resource<T>
```kotlin
sealed class Resource<T> {
    data class Success<T>(val data: T): Resource<T>()
    data class Error<T>(val message: String, val data: T? = null): Resource<T>()
    data class Loading<T>(val data: T? = null): Resource<T>()
}
```

Use cases:
- Complex data flows
- Loading state required
- Caching mechanism present
- MVVM architecture
- Offline-first applications

## LiveData vs Flow

### LiveData Adapter
Best for:
- Lifecycle-aware data
- Simple UI observations
- Automatic configuration change handling

```kotlin
interface ApiService {
    @GET("users")
    fun getUsers(): LiveData<ApiResponse<List<User>>>
}
```

### Flow Adapter
Best for:
- Complex data operations
- Cold streams
- Coroutines integration
- Data transformation operations
- Offline-first apps
- Background operations

```kotlin
interface ApiService {
    @GET("users")
    fun getUsers(): Flow<List<User>>
}
```

## Flow Operators

### 1. Map Operators
```kotlin
// Basic mapping
flow.map { user -> user.toUserUI() }

// Non-null mapping
flow.mapNotNull { it?.name }

// Complex transformation
flow.transform { user ->
    emit(user.name)
    emit(user.age)
}
```

### 2. Filter Operators
```kotlin
// Basic filtering
flow.filter { user -> user.age > 18 }
// Filters the flow to only emit users whose age is greater than 18.

// Inverse filtering
flow.filterNot { user -> user.isDeleted }
// Filters the flow to emit users who are not marked as deleted.

// Take/Drop operations
flow.drop(2)
// Skips the first 2 items in the flow and emits the rest.

flow.take(5)
// Emits only the first 5 items from the flow and ignores the rest.

// Distinct values
flow.distinctUntilChanged()
// Emits only unique consecutive items, skipping duplicates.

```

### 3. Combining Operators
```kotlin
// Zip operation
users.zip(ages) { user, age -> "$user is $age years old" }
// Combines the corresponding items from two flows into a single item, emitting pairs until one flow is exhausted.

// Combine operation
names.combine(scores) { name, score -> "$name: $score" }
// Combines the latest values from both flows whenever one changes, emitting a new result with the current values of each flow.

// Merge operation
merge(flow1, flow2)
// Merges multiple flows into a single flow, emitting items as they arrive without waiting for pairs.

```

### 4. Buffer and Rate Limiting
```kotlin
// Buffering
flow.buffer()
// Collects emitted values in a buffer, allowing the flow to continue emitting without waiting for the collector to process each value.

// Debounce
searchFlow.debounce(300L)
// Emits an item only if no new items are emitted within the specified time (300 milliseconds in this case). Useful for handling rapid user input, like search queries.

// Sampling
flow.sample(1000L)
// Emits the latest value from the flow at the specified interval (every 1000 milliseconds in this case), ignoring intermediate values.

```

### 5. Error Handling
```kotlin
// Error catching
flow.catch { e -> emit(Resource.Error(e.message)) }

// Retry mechanism
flow.retry(3) { cause -> cause is IOException }

// Completion handling
flow.onCompletion { cause -> 
    if (cause != null) {
        // Handle error
    }
}
```

### 6. State Management
```kotlin
// StateFlow conversion
val stateFlow = flow.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5000),
    initialValue = emptyList()
)
// Converts a Flow into a StateFlow, which always holds the latest value.
// - `scope`: Defines the coroutine scope where the flow runs (usually a ViewModel scope).
// - `started`: Specifies when to start and stop the flow. `WhileSubscribed(5000)` keeps it active for 5 seconds after the last subscriber unsubscribes.
// - `initialValue`: Sets the initial value of the StateFlow.

// SharedFlow conversion
val sharedFlow = flow.shareIn(
    scope = viewModelScope,
    started = SharingStarted.Eagerly,
    replay = 1
)
// Converts a Flow into a SharedFlow, which can emit values to multiple collectors.
// - `scope`: Defines the coroutine scope where the flow runs.
// - `started`: `Eagerly` starts the flow immediately, even without subscribers.
// - `replay`: Specifies how many previously emitted values to cache and replay to new collectors (1 in this case).

```

### 7. Flatting Operators
```kotlin
// Sequential flat mapping
flow.flatMapConcat { user -> getUserDetails(user.id) }
// Transforms each emitted item into a new flow and collects the results sequentially, one after another.
// It waits for the previous flow to complete before starting the next one.

// Parallel flat mapping
flow.flatMapMerge { user -> getUserPosts(user.id) }
// Transforms each emitted item into a new flow and collects results concurrently, allowing multiple flows to run in parallel.
// The results may arrive in any order based on completion time.

// Latest value flat mapping
searchFlow.flatMapLatest { query -> searchUsers(query) }
// Cancels the previous flow and starts a new one every time a new item is emitted.
// Only the latest search result is processed, making it ideal for real-time user input.

```

## Best Practices
- Choose appropriate operators for performance
- Implement proper error handling
- Manage memory with buffer and rate limiting
- Select StateFlow or SharedFlow based on use case
- Consider lifecycle management
- Handle thread management appropriately
