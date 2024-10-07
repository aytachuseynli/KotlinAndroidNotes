package com.aytachuseynli.nothing.Functions

fun main() {
    addNote("Learn Kotlin", {
        println("Note added successfully!")
    })

    runInBackground {
        println("Task running in the background...")
        // return // You cannot use return here
    }

    listNotes { note ->
        note.contains("Kotlin") // List only notes containing "Kotlin"
    }
}

// Inline function for adding a note
inline fun addNote(note: String, action: () -> Unit) {
    if (note.isNotEmpty()) {
        println("Adding note: $note")
        action() // Perform the action to add the note
    } else {
        println("Note cannot be empty!")
    }
}

// Function for filtering and listing notes
inline fun listNotes(noinline filter: (String) -> Boolean) {
    val notes = listOf("Learn Kotlin", "Learn Java", "Android development")
    println("Notes:")
    for (note in notes) {
        if (filter(note)) {
            println(note)
        }
    }
}

// Inline function for running a task in the background
inline fun runInBackground(crossinline task: () -> Unit) {
    val thread = Thread {
        task() // Execute the task, but cannot use return here
    }
    thread.start()
}
