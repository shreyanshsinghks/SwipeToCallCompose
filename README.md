# SwipeToTask Library 🎭

![GitHub release (latest by date)](https://img.shields.io/github/v/release/shreyanshsinghks/SwipeToTaskLibrary)
![GitHub](https://img.shields.io/github/license/shreyanshsinghks/SwipeToTaskLibrary)

#SwipeToCall feature jetpack compose
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
### Note: Please do not add the padding in the card design you have made. Please add it in the LazyColumn, or the column inside the custom card you have made.

```kotlin
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shreyanshsinghks.swipetofunction.SwappableCard

data class CardItem(val title: String, val description: String)

@Composable
fun SwappableCardList() {
    val items = remember {
        mutableStateListOf(
            CardItem("Item 1", "Description 1"),
            CardItem("Item 2", "Description 2"),
            CardItem("Item 3", "Description 3"),
            CardItem("Item 4", "Description 4"),
            CardItem("Item 5", "Description 5")
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(items) { item ->
            SwappableCard(
                item = item,
                onSwipeLeft = {
                    items.remove(item)
                },
                onSwipeRight = {
                    println("Marked as done: ${item.title}")
                },
                leftIcon = {
                    Icon(Icons.Default.Close, contentDescription = "Delete", tint = Color.White)
                },
                rightIcon = {
                    Icon(Icons.Default.Check, contentDescription = "Done", tint = Color.White)
                }
            ) { currentItem ->
                // Custom card content
                CustomCard(item = currentItem)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CustomCard(item: CardItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
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
