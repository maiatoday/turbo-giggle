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
import androidx.graphics.shapes.toPath
import androidx.graphics.shapes.transformed
import net.maiatoday.turbogiggle.ui.theme.Cherry
import net.maiatoday.turbogiggle.ui.theme.Licorice
import net.maiatoday.turbogiggle.ui.theme.OrangeSquash
import net.maiatoday.turbogiggle.ui.theme.Plumberry
import net.maiatoday.turbogiggle.ui.theme.Sherbet
import net.maiatoday.turbogiggle.ui.theme.SwimmingCap

val spikySplash = RoundedPolygon.star(
    numVerticesPerRadius = 14,
    innerRadius = 0.4f,
    innerRounding = CornerRounding(radius = 0.1f)
)
val splashColors = listOf(Cherry, Licorice, Plumberry, OrangeSquash,  SwimmingCap)
val colorStops = arrayOf(
    0.0f to Sherbet,
    0.3f to SwimmingCap,
    0.2f to Licorice,
    0.4f to OrangeSquash,
    0.5f to Cherry,
    1f to Licorice,

)
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
            .graphicsLayer {
                scaleY = 0.5f
                rotationZ = rotate
            }
            .drawWithCache {
                val matrix = fromBoundsToView(width = size.width, height = size.height)
                val sizedSpikySplash = RoundedPolygon(spikySplash).apply { transformed(matrix) }
                val spikyBrush = Brush.radialGradient(colorStops = colorStops)
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
        .background(Sherbet))
}