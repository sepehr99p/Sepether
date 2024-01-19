package com.example.sepether.systemDesign.components

import android.content.res.Resources.getSystem
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sepether.R
import com.example.sepether.systemDesign.theme.Shapes
import com.example.sepether.ui.weather.components.SimpleText
import com.example.sepether.ui.weather.components.today.WeatherInfoItem
import com.example.sepether.utils.WeatherType
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun WeatherLoadingView() {
    Column {
        Spacer(modifier = Modifier.height(24.dp))
        CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
        Text(text = "Sepether")
        Spacer(modifier = Modifier.height(24.dp))

        ShimmerNewItem()
    }


}


@Composable
fun ColumnScope.LoadingShimmerEffect(
) {
    val gradient = listOf(
        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f),
        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(durationMillis = 1000, easing = LinearEasing)),
        label = ""
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
            .fillMaxWidth()
            .height(24.dp)
            .onGloballyPositioned {
                positionInRootTopBar = it.positionInRoot()
            }

    )
}

val Int.px: Int get() = (this * getSystem().displayMetrics.density).toInt()

@Composable
fun ShimmerNewItem() {
    SimpleText(
        modifier = Modifier.shimmerEffectNew(true),
        value = "Today"
    )
    Spacer(modifier = Modifier.height(16.dp))
    Image(
        painter = painterResource(id = R.drawable.ic_cloudy),
        contentDescription = null,
        modifier = Modifier.shimmerEffectNew(true).width(150.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        modifier = Modifier.shimmerEffectNew(true),
        text = "°C",
        fontSize = 50.sp,
        color = MaterialTheme.colorScheme.onPrimary
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        modifier = Modifier.shimmerEffectNew(true),
        text = "",
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.onPrimary
    )
    Spacer(modifier = Modifier.height(32.dp))
    Row(
        modifier = Modifier.fillMaxWidth().shimmerEffectNew(true),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        WeatherInfoItem(
            modifier = Modifier.shimmerEffectNew(true),
            value = 34,
            unit = "hpa",
            icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
            iconTint = MaterialTheme.colorScheme.onPrimary,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
        )
        WeatherInfoItem(
            modifier = Modifier.shimmerEffectNew(true),
            value = 23,
            unit = "%",
            icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
            iconTint = MaterialTheme.colorScheme.onPrimary,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
        )
        WeatherInfoItem(
            modifier = Modifier.shimmerEffectNew(true),
            value = 43,
            unit = "km/h",
            icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
            iconTint = MaterialTheme.colorScheme.onPrimary,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
        )
    }

}

@Composable
fun ShimmerItem(
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_cloudy),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(60.dp)
                .clip(CircleShape)
                .shimmerEffectNew(isLoading)
                .alpha(if (isLoading) 0f else 1f)
        )
        Spacer(
            modifier = Modifier
                .width(8.dp)
                .background(Color.Gray)
        )
        Column(modifier = Modifier
            .fillMaxHeight()
            .align(Alignment.CenterVertically)) {
            Text(
                text = "Name- MR. Ravi Sahu",
                modifier = Modifier
                    .shimmerEffectNew(isLoading)
                    .alpha(if (isLoading) 0f else 1f)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Age- 24 ",
                modifier = Modifier
                    .shimmerEffectNew(isLoading)
                    .alpha(if (isLoading) 0f else 1f)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Contact here -  ",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .shimmerEffectNew(isLoading)
                .alpha(if (isLoading) 0f else 1f)
        )

    }
}

fun Modifier.shimmerEffectNew(toShow:Boolean): Modifier = composed {

    if (toShow){
        var size by remember {
            mutableStateOf(IntSize.Zero)
        }
        val transition = rememberInfiniteTransition(label = "")
        val startOffsetX by transition.animateFloat(
            initialValue = -2 * size.width.toFloat(),
            targetValue = 2 * size.width.toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(1000)
            ), label = ""
        )
        background(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFFB8B5B5),
                    Color(0xFF8F8B8B),
                    Color(0xFFB8B5B5),
                ),
                start = Offset(startOffsetX, 0f),
                end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
            )
        )
            .onGloballyPositioned {
                size = it.size
            }
    }else{
        Modifier
    }
}