package net.maiatoday.turbogiggle

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.maiatoday.turbogiggle.ui.theme.Cherry
import net.maiatoday.turbogiggle.ui.theme.Custard
import net.maiatoday.turbogiggle.ui.theme.Pasta
import net.maiatoday.turbogiggle.ui.theme.SwimmingCap

@Composable
fun ShimmerPane(
    modifier: Modifier = Modifier,
    paneColors: List<Color> = listOf(Pasta, SwimmingCap, Custard, Cherry),
    height: Dp = 120.dp
) {

    //region some size setup
    val deltaPx = height.toPx()
    val deltaDoublePx = deltaPx * 2

    val infiniteTransition = rememberInfiniteTransition(label = "pane transition")
    val offset by infiniteTransition.animateFloat(
        initialValue = 0.0f,
        targetValue = deltaDoublePx,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
        ), label = "Pane shimmer offset"
    )

    Spacer(modifier.drawWithCache {
        val brush = Brush.linearGradient(
            colors = paneColors,
            start = Offset(offset, offset),
            end = Offset(offset + deltaPx, offset + deltaPx),
            tileMode = TileMode.Repeated
        )
        onDrawBehind {
            drawRect(brush = brush, style = Fill)
        }

    })
}

@Preview
@Composable
private fun PreviewPane() {
    ShimmerPane(Modifier.fillMaxSize())
}