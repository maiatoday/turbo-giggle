package net.maiatoday.turbogiggle

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.star
import net.maiatoday.turbogiggle.ui.theme.Spike1
import net.maiatoday.turbogiggle.ui.theme.Spike2
import net.maiatoday.turbogiggle.ui.theme.Spike3
import net.maiatoday.turbogiggle.ui.theme.Spike4

val spikySplash = RoundedPolygon.star(
    numVerticesPerRadius = 14,
    innerRadius = 0.4f,
    innerRounding = CornerRounding(radius = 0.1f)
)
val splashColors = listOf(Spike1, Spike2, Spike3, Spike4)

@Composable
fun SpikySplash(modifier: Modifier = Modifier, colors:List<Color> = splashColors) {
    val spikyTransition = rememberInfiniteTransition(label = "spiky transition")
    val rotate by spikyTransition.animateFloat(
        initialValue = 45f,
        targetValue = -45f,
        animationSpec = infiniteRepeatable(
            animation = tween(4_000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "Spiky rotate "
    )
    Spacer(
        modifier
            .background(Color.Red)
            .graphicsLayer {
                scaleY = 0.5f
                rotationZ = rotate
            }
            .drawWithCache {
                val matrix = fromCanonicalToView(width = size.width, height = size.height)
                val sizedSpikySplash = RoundedPolygon(spikySplash).apply { transform(matrix) }
                val spikyBrush = Brush.radialGradient(colors)
                onDrawBehind {
                    drawPath(
                        path = sizedSpikySplash
                            .toPath()
                            .asComposePath(),
                        brush = spikyBrush
                    )
                }
            }
    )
}

@Preview
@Composable
private fun DefaultSpikySplash() {
    SpikySplash(modifier = Modifier
        .fillMaxSize()
        .background(Color.Red))
}