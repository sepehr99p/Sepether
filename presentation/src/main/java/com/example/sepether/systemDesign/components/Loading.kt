package com.example.sepether.systemDesign.components

import android.content.res.Resources.getSystem
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.sepether.systemDesign.theme.Shapes

@Composable
fun LoadingView() {
    Spacer(modifier = Modifier.height(24.dp))
//    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
    Text(text = "Sepether")
    Spacer(modifier = Modifier.height(24.dp))
    Row {
        LoadingShimmerEffect()
    }

}


@Composable
fun RowScope.LoadingShimmerEffect(
) {
    val gradient = listOf(
        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f),
        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
    )

    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(durationMillis = 1000, easing = LinearEasing))
    )

    var positionInRootTopBar by remember { mutableStateOf(Offset.Zero) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.px
    val screenWidth = configuration.screenWidthDp.px

    val diff = screenWidth * translateAnim * 4

    val initialX = diff - (positionInRootTopBar.x) - (screenWidth * 2)
    val endX = (screenWidth * 2) + initialX

    val initialY = -positionInRootTopBar.y + diff
    val endY = screenHeight - positionInRootTopBar.y + diff
    val brush = Brush.linearGradient(
        colors = gradient,
        start = Offset(initialX, initialY),
        end = Offset(endX, endY),
    )
    Box(
        modifier = Modifier
            .padding(10.dp)
            .clip(Shapes.medium)
            .background(brush)
            .weight(1f)
            .height(8.dp)
            .onGloballyPositioned {
                positionInRootTopBar = it.positionInRoot()
            }
    )
}

val Int.px: Int get() = (this * getSystem().displayMetrics.density).toInt()