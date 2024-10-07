package com.aytachuseynli.nothing.Functions


# Kotlin Extension Functions

## What is an Extension Function?

An extension function in Kotlin allows you to add new functions to existing classes without modifying their source code or subclassing them. This is particularly useful when you need to extend a class with new capabilities but don't want or can't alter its source code.

### Syntax:
```kotlin
fun ClassName.functionName(parameters): ReturnType {
    // function body
}
```

- **ClassName:** The class being extended.
- **functionName:** The name of the new function.
- **parameters:** Parameters taken by the extension function.
- **ReturnType:** The return type of the function.

### Example:
```kotlin
fun ImageView.loadImage(url: String) {
    Glide.with(this).load(url).into(this)
}
```

In this example, the `ImageView` class is extended with a new function `loadImage`. The `this` keyword refers to the `ImageView` object in the context of the extension function.

### Usage:
```kotlin
imageView.loadImage("someUrl")
```
This function is invoked as if it were a member function of `ImageView`, even though it's defined externally.

---

## How Extension Functions Work Internally

When an extension function is called, the compiler internally converts the function call into a static method call where the receiver object is passed as the first argument.

### Decompilation Example:
When the Kotlin code is decompiled into Java, the extension function:
```kotlin
fun ImageView.loadImage(url: String) {
    Glide.with(this).load(url).into(this)
}
```

becomes:
```java
public final class ExtensionKt {
   public static final void loadImage(ImageView imageView, String url) {
      Glide.with(imageView).load(url).into(imageView);
   }
}
```
Here, `imageView` is passed as the first argument, showing how the extension function works under the hood as a static method.

---

## Key Points:

- **Defined externally:** Extension functions are defined outside the class they extend.
- **No access to private/protected members:** They can only access public members of the class and cannot access its private or protected members.
- **Non-intrusive:** They do not modify the actual class but give the appearance of adding new methods.
- **Static methods:** Internally, they are compiled as static methods.

### When to Use Extension Functions:
- When you want to add functionality to classes from libraries or SDKs that you cannot modify.
- To avoid subclassing or modifying existing code for minor enhancements.