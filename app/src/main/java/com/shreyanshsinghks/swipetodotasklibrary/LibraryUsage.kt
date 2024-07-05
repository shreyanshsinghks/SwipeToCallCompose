package com.shreyanshsinghks.swipetodotasklibrary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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