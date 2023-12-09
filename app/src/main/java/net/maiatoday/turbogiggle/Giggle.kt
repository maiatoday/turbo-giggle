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
import net.maiatoday.turbogiggle.ui.theme.Aubergette
import net.maiatoday.turbogiggle.ui.theme.Cherry
import net.maiatoday.turbogiggle.ui.theme.Custard
import net.maiatoday.turbogiggle.ui.theme.Licorice
import net.maiatoday.turbogiggle.ui.theme.OrangeSquash
import net.maiatoday.turbogiggle.ui.theme.Sherbet
import net.maiatoday.turbogiggle.ui.theme.SwimmingCap

@Composable
fun Giggle(modifier: Modifier = Modifier) {
    Box(modifier.background(Sherbet)) {

        ShimmerPane(
            Modifier
                .height(280.dp)
                .width(250.dp)
        )
        Daisy(colors = listOf(SwimmingCap, Aubergette))
        Bean(colors = listOf(Custard, OrangeSquash),
            modifier = Modifier
                .fillMaxSize()
        )
        SpikySplash(
            Modifier
                .size(500.dp)
                .offset(100.dp, 400.dp)
        )
    }
}

@Preview
@Composable
private fun DefaultGiggle() {
    Giggle(modifier = Modifier.fillMaxSize())
}