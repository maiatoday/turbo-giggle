package net.maiatoday.turbogiggle

import android.graphics.Matrix
import android.graphics.RectF
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlin.math.min

// by default the rounded polygon library creates canonical shapes with a radius of 1 around a center at (0, 0)
// https://medium.com/androiddevelopers/the-shape-of-things-to-come-1c7663d9dbc0
fun fromCanonicalToView(
    bounds: RectF = RectF(-1f, -1f, 1f, 1f),
    width: Float,
    height: Float
): Matrix {
    val originalWidth = bounds.right - bounds.left
    val originalHeight = bounds.bottom - bounds.top
    val scale = min(width / originalWidth, height / originalHeight)
    val newLeft = bounds.left - (width / scale - originalWidth) / 2
    val newTop = bounds.top - (height / scale - originalHeight) / 2
    val matrix = Matrix()
    matrix.setTranslate(-newLeft, -newTop)
    matrix.postScale(scale, scale)
    return matrix
}

@Composable
fun Dp.toPx() = with(LocalDensity.current) { this@toPx.toPx() }