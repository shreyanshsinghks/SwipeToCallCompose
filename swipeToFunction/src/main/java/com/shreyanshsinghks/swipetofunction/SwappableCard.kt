package com.shreyanshsinghks.swipetofunction

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun <T> SwappableCard(
    item: T,
    onSwipeLeft: (T) -> Unit,
    onSwipeRight: (T) -> Unit,
    swipeThreshold: Float = 0.4f,
    leftColor: Color = Color.Red,
    rightColor: Color = Color.Green,
    leftIcon: @Composable () -> Unit,
    rightIcon: @Composable () -> Unit,
    content: @Composable (T) -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    val animatedOffsetX by animateFloatAsState(
        targetValue = offsetX,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    val dragState = rememberDraggableState { delta ->
        offsetX += delta
    }

    val density = LocalDensity.current
    val cardWidthPx = with(density) { 400.dp.toPx() }
    val actualSwipeThreshold = cardWidthPx * swipeThreshold

    val swipeProgress = (animatedOffsetX / actualSwipeThreshold).coerceIn(-1f, 1f)
    val scale by animateFloatAsState(targetValue = 1f - (swipeProgress.absoluteValue * 0.05f))
    val iconScale by animateFloatAsState(targetValue = swipeProgress.absoluteValue)

    var isActionInitiated by remember { mutableStateOf(false) }

    LaunchedEffect(isActionInitiated) {
        if (isActionInitiated) {
            delay(300)
            if (offsetX > 0) {
                onSwipeRight(item)
            } else {
                onSwipeLeft(item)
            }
            isActionInitiated = false
            offsetX = 0f
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(if (offsetX > 0) rightColor else leftColor),
            contentAlignment = if (offsetX > 0) Alignment.CenterStart else Alignment.CenterEnd
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .scale(iconScale)
            ) {
                if (offsetX > 0) rightIcon() else leftIcon()
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(animatedOffsetX.roundToInt(), 0) }
                .draggable(
                    state = dragState,
                    orientation = Orientation.Horizontal,
                    onDragStopped = {
                        if (offsetX.absoluteValue >= actualSwipeThreshold) {
                            offsetX = if (offsetX > 0) cardWidthPx else -cardWidthPx
                            isActionInitiated = true
                        } else {
                            offsetX = 0f
                        }
                    }
                )
                .scale(scale),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            content(item)
        }
    }
}