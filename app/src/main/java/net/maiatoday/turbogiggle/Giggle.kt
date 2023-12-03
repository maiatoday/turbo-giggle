package net.maiatoday.turbogiggle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Giggle(modifier: Modifier = Modifier) {
    Box(modifier.background(Color.Red)) {
        SpikySplash(
            Modifier
                .size(300.dp)
                .offset(100.dp, 200.dp)
        )
        ShimmerPane(
            Modifier
                .height(280.dp)
                .width(150.dp)
        )
        ScribblePath(
            Modifier
                .fillMaxSize()
            // .offset(500.dp, 500.dp)
        )
    }
}

@Preview
@Composable
private fun DefaultGiggle() {
    Giggle(modifier = Modifier.fillMaxSize())
}