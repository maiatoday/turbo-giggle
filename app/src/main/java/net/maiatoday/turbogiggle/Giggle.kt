package net.maiatoday.turbogiggle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Giggle(modifier: Modifier = Modifier) {
    SpikySplash(Modifier.fillMaxSize().background(Color.Red))
}

@Preview
@Composable
private fun DefaultGiggle() {
    Giggle(modifier = Modifier.fillMaxSize())
}