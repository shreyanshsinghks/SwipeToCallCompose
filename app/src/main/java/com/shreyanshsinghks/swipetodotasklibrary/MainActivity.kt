package com.shreyanshsinghks.swipetodotasklibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.shreyanshsinghks.swipetodotasklibrary.ui.theme.SwipeToDoTaskLibraryTheme
import com.shreyanshsinghks.swipetofunction.SwappableCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SwipeToDoTaskLibraryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        val items = listOf("Item 1", "Item 2", "Item 3")  // Example list

                        LazyColumn {
                            items(items) { item ->
                                SwappableCard(
                                    item = item,
                                    onSwipeLeft = { Unit },
                                    onSwipeRight = { Unit },
                                    rightIcon = {Icons.Rounded.Create},
                                    leftIcon = {Icons.Rounded.Add}
                                ) {
                                    // Card content
                                    Text(text = "hello")
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}
