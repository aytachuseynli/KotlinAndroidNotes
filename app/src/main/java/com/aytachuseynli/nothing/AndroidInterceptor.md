# Android Interceptors: Overview and Implementation

## Introduction
Interceptors in Android, particularly with **OkHttp**, provide a mechanism to **intercept, modify, and monitor** network requests and responses. They are commonly used in conjunction with **Retrofit** to enhance API communication.

## Purpose of Interceptors
Interceptors serve several key purposes in Android applications:

- **Monitoring Requests & Responses**: Log network traffic for debugging and analysis.
- **Modifying Requests**: Add headers, authentication tokens, or query parameters dynamically.
- **Caching Mechanism**: Implement custom caching strategies.
- **Error Handling & Retry Mechanism**: Handle timeouts and retry failed requests.
- **Security Enhancements**: Encrypt or validate requests and responses.

## Types of Interceptors
OkHttp provides two types of interceptors:

### 1. **Application Interceptors**
- Operate at a high level, before network caching.
- Cannot observe **redirects**.
- Best for modifying requests (e.g., adding authentication headers).

### 2. **Network Interceptors**
- Operate at a lower level, after caching.
- Can **observe and manipulate network traffic** before reaching the app.
- Useful for handling **response modifications or logging**.

## Implementing Interceptors

### 1. **Adding Custom Headers (Authentication)**
A common use case is adding an **Authorization Token** to all outgoing requests.

```kotlin
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer YOUR_ACCESS_TOKEN")
            .build()
        return chain.proceed(modifiedRequest)
    }
}
```

### 2. **Logging HTTP Requests and Responses**
To enable **detailed logging**, use `HttpLoggingInterceptor`:

```kotlin
val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY // Logs request and response body
}
```

- **BODY**: Logs request and response bodies.
- **HEADERS**: Logs request and response headers.
- **BASIC**: Logs request method, URL, and response status.
- **NONE**: Disables logging.

### 3. **Retry Mechanism for Failed Requests**
To implement automatic retry for failed requests:

```kotlin
class RetryInterceptor(private val maxRetries: Int) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var attempt = 0
        var response: Response
        do {
            response = chain.proceed(chain.request())
            attempt++
        } while (!response.isSuccessful && attempt < maxRetries)
        return response
    }
}
```

### 4. **Adding Multiple Interceptors to OkHttpClient**
Interceptors are registered via `OkHttpClient` and integrated with Retrofit:

```kotlin
val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(AuthInterceptor()) // Adds authentication headers
    .addInterceptor(loggingInterceptor) // Enables request/response logging
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl("https://api.example.com/")
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
```

- **`addInterceptor()`** → Registers an **Application Interceptor**.
- **`addNetworkInterceptor()`** → Registers a **Network Interceptor**.

## Conclusion
Interceptors provide a powerful mechanism for managing API requests efficiently. By leveraging them effectively, developers can:

- Enhance **security** with authentication and encryption.
- Improve **performance** through request modifications and caching.
- Simplify **debugging** with logging and retry mechanisms.

Integrating interceptors with **OkHttp and Retrofit** ensures a **scalable and maintainable** networking layer in Android applications.

