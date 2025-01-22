package com.aytachuseynli.nothing

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.aytachuseynli.nothing.ui.theme.NothingTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GlobalScopeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NothingTheme {
                MyButton()
            }
        }
    }
}

@Composable
fun MyButton() {
    // Create a CoroutineScope tied to the Composable lifecycle
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            coroutineScope.launch {
                myCoroutines()
            }
        }) {
            Text(text = "Start Coroutines")
        }
    }
}

// Updated function to suspend for coroutine compatibility
suspend fun myCoroutines() {
    Log.d("GlobalScope", "Before runBlocking")
    coroutineScope {
        launch {
            delay(3000)
            Log.d("GlobalScope", "Finished launch 1")
        }
        launch {
            delay(3000)
            Log.d("GlobalScope", "Finished launch 2")
        }
        Log.d("GlobalScope", "Started coroutines")
        delay(5000)
        Log.d("GlobalScope", "Finished coroutines")
    }
    Log.d("GlobalScope", "After coroutines")
}
