package com.shreyanshsinghks.swipetofunction

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
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
    modifier: Modifier = Modifier,
    swipeThreshold: Float = 0.4f,
    cornerRadius: Dp = 16.dp,
    leftColor: Color = Color.Red,
    rightColor: Color = Color.Green,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
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
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Background with rounded corners
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(cornerRadius))
                .background(if (offsetX > 0) rightColor else leftColor),
            contentAlignment = if (offsetX > 0) Alignment.CenterStart else Alignment.CenterEnd
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .scale(iconScale)
            ) {
                if (offsetX > 0) {
                    rightIcon?.invoke()
                } else {
                    leftIcon?.invoke()
                }
            }
        }

        // Card content
        Box(
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
                .scale(scale)
        ) {
            content(item)
        }
    }
}