# SwipeToTask Library 🎭

![GitHub release (latest by date)](https://img.shields.io/github/v/release/shreyanshsinghks/SwipeToTaskLibrary)
![GitHub](https://img.shields.io/github/license/shreyanshsinghks/SwipeToTaskLibrary)

SwipeToTask is a sleek, customizable swipeable card component for Jetpack Compose. It enables users to perform actions by swiping cards left or right, perfect for task management, item deletion, or any other swipe-based interaction.

![Demo GIF](link_to_demo_gif.gif)

## Features ✨

- 👈👉 Swipe left and right actions
- 🎨 Customizable colors and icons
- 📐 Adjustable corner radius
- 📜 Seamless integration with LazyColumn for efficient list rendering
- 🚀 Easy to implement and customize

## Installation 🛠️

1. Add the JitPack repository to your project-level `settings.gradle.kts`:

    ```kotlin
    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            google()
            mavenCentral()
            maven {
                url = uri("https://jitpack.io")
            }
        }
    }
    ```

2. Add the dependency to your app-level `build.gradle.kts`:

    ```kotlin
    dependencies {
        implementation("com.github.shreyanshsinghks:SwipeToTaskLibrary:1.0")
    }
    ```

## Usage 📖

Here's an example of how to use the library:

```kotlin
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shreyanshsinghks.swipetofunction.SwappableCard

@Composable
fun SimpleSwipeToTaskDemo() {
    // Create a mutable list of tasks
    val tasks = remember { mutableStateListOf("Buy groceries", "Walk the dog", "Call mom", "Read a book", "Exercise") }

    // Use LazyColumn for efficient list rendering
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        // Render each task as a SwappableCard
        items(tasks) { task ->
            SwappableCard(
                item = task,
                // Action for left swipe: Delete task
                onSwipeLeft = {
                    tasks.remove(task)
                },
                // Action for right swipe: Mark task as complete
                onSwipeRight = {
                    tasks.remove(task)
                    // Note: In a real app, you might want to move this to a 'completed' list instead
                },
                // Icon for left swipe action
                leftIcon = {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.White)
                },
                // Icon for right swipe action
                rightIcon = {
                    Icon(Icons.Default.Check, contentDescription = "Complete", tint = Color.White)
                },
                // Background color for left swipe
                leftColor = Color.Red,
                // Background color for right swipe
                rightColor = Color.Green
            ) { currentTask ->
                // Content of the SwappableCard: A simple card with the task text
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = currentTask,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YourAppTheme {
                // Call the demo function
                SimpleSwipeToTaskDemo()
            }
        }
    }
}
```

Here's a more simple example of how to use the library:

```kotlin
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shreyanshsinghks.swipetofunction.SwappableCard

@Composable
fun SimpleSwipeToTaskDemo() {
    // Create a mutable list of tasks
    val tasks = remember { mutableStateListOf("Buy groceries", "Walk the dog", "Call mom", "Read a book", "Exercise") }

    // Use LazyColumn for efficient list rendering
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        // Render each task as a SwappableCard
        items(tasks) { task ->
            SwappableCard(
                item = task,
                onSwipeLeft = { },
                onSwipeRight = { },
            ) { currentTask ->
                // Content of the SwappableCard: A simple card with the task text
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = currentTask,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
```
```

### SwappableCard Parameters 📝

| Parameter       | Type                      | Description                                            |
|-----------------|---------------------------|--------------------------------------------------------|
| `item`          | `T`                       | The data item associated with the card.                |
| `onSwipeLeft`   | `(T) -> Unit`             | Callback function for left swipe action.               |
| `onSwipeRight`  | `(T) -> Unit`             | Callback function for right swipe action.              |
| `modifier`      | `Modifier`                | (Optional) Compose modifier for the card.              |
| `swipeThreshold`| `Float`                   | (Optional) Threshold to trigger swipe action. Default is 0.4. |
| `cornerRadius`  | `Dp`                      | (Optional) Corner radius of the card. Default is 16.dp.|
| `leftColor`     | `Color`                   | (Optional) Background color for left swipe. Default is Red.|
| `rightColor`    | `Color`                   | (Optional) Background color for right swipe. Default is Green.|
| `leftIcon`      | `@Composable () -> Unit`  | (Optional) Composable for the left swipe icon.         |
| `rightIcon`     | `@Composable () -> Unit`  | (Optional) Composable for the right swipe icon.        |
| `content`       | `@Composable (T) -> Unit` | Composable function for the card content.              |
