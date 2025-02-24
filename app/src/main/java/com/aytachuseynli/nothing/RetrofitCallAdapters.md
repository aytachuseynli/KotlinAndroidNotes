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

// Inverse filtering
flow.filterNot { user -> user.isDeleted }

// Take/Drop operations
flow.drop(2)
flow.take(5)

// Distinct values
flow.distinctUntilChanged()
```

### 3. Combining Operators
```kotlin
// Zip operation
users.zip(ages) { user, age -> "$user is $age years old" }

// Combine operation
names.combine(scores) { name, score -> "$name: $score" }

// Merge operation
merge(flow1, flow2)
```

### 4. Buffer and Rate Limiting
```kotlin
// Buffering
flow.buffer()

// Debounce
searchFlow.debounce(300L)

// Sampling
flow.sample(1000L)
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

// SharedFlow conversion
val sharedFlow = flow.shareIn(
    scope = viewModelScope,
    started = SharingStarted.Eagerly,
    replay = 1
)
```

### 7. Flatting Operators
```kotlin
// Sequential flat mapping
flow.flatMapConcat { user -> getUserDetails(user.id) }

// Parallel flat mapping
flow.flatMapMerge { user -> getUserPosts(user.id) }

// Latest value flat mapping
searchFlow.flatMapLatest { query -> searchUsers(query) }
```

## Best Practices
- Choose appropriate operators for performance
- Implement proper error handling
- Manage memory with buffer and rate limiting
- Select StateFlow or SharedFlow based on use case
- Consider lifecycle management
- Handle thread management appropriately
