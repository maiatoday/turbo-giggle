package net.maiatoday.turbogiggle

import android.os.Build
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toAndroidRectF
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.flatten
import net.maiatoday.turbogiggle.ui.theme.Aubergette
import net.maiatoday.turbogiggle.ui.theme.Licorice
import net.maiatoday.turbogiggle.ui.theme.OrangeSquash
import net.maiatoday.turbogiggle.ui.theme.Pasta
import net.maiatoday.turbogiggle.ui.theme.Sherbet
import kotlin.math.min

const val daisy =
    "m 120.55184,115.28212 3.16067,-9.87285 c 0.669,-2.08973 1.9577,-4.56981 2.16256,-6.755105 0.24351,-2.597493 0.85397,-5.067863 1.18681,-7.640349 0.0741,-0.571936 -0.17134,-1.17428 -0.072,-1.741687 0.86701,-4.952388 4.88071,-11.907575 5.32323,-16.627939 0.58145,-6.202464 3.34119,-12.403345 5.25122,-18.369621 0.79396,-2.480048 3.73887,-13.979176 4.99052,-15.588693 2.03157,-2.612399 3.81248,-5.05137 5.68323,-7.919516 0.19997,-0.306499 0.10706,-0.749162 0.3327,-1.039247 0.31904,-0.410258 1.15575,-0.263357 1.4748,-0.673618 0.22564,-0.290083 0.10707,-0.749157 0.3327,-1.039245 0.46291,-0.595235 1.7308,-1.059066 2.37857,-1.530046 0.2201,-0.16002 2.46982,-2.015369 2.54491,-2.049668 1.52572,-0.696866 -0.57215,1.628137 1.30846,-0.153994 0.13464,-0.127695 -0.007,-0.440237 0.16635,-0.519623 0.17381,-0.07939 0.39725,0.262203 0.57105,0.182814 0.17382,-0.07938 -0.007,-0.440234 0.16635,-0.519623 1.10771,-0.505937 4.19127,0.506817 4.90112,0.423278 1.60433,-0.188812 5.46503,0.601152 7.01899,1.674162 1.06028,0.732121 4.55927,5.784891 4.61807,7.207196 0.0152,0.367194 -0.34792,0.672055 -0.3327,1.039246 0.0426,1.031419 1.41221,2.355877 1.45245,3.329375 0.0152,0.367194 -0.2218,0.692831 -0.3327,1.039247 0.21435,0.641502 0.57379,1.263056 0.64306,1.924501 0.0384,0.3669 -0.3175,0.6736 -0.3327,1.039246 -0.0411,0.984493 0.35142,1.97925 0.31033,2.963744 -0.0229,0.548449 -0.42887,1.016251 -0.49905,1.558867 -0.15757,1.217887 0.13524,2.78511 -0.0223,4.002998 -0.14041,1.085218 -0.8577,2.032524 -0.99811,3.117737 -0.62729,4.848129 -2.41509,9.510472 -3.92043,14.212638 -4.45874,13.927605 -8.16065,28.386452 -16.1633,40.655765 -1.30663,2.00327 -2.76493,4.43006 -4.44678,6.02384 -0.26945,0.25534 -0.0632,0.78392 -0.3327,1.03926 -0.19057,0.18053 -0.57788,0.13167 -0.73742,0.33681 -0.33838,0.43514 -0.0949,1.17586 -0.49905,1.55887 -1.48746,1.4096 -2.30887,3.58043 -3.54301,5.1674 -0.15944,0.20516 -0.54687,0.15625 -0.73741,0.33681 -0.27283,0.25854 -0.15817,1.40318 -0.49905,1.55886 -0.1738,0.0794 -0.39724,-0.26219 -0.57105,-0.18281 -0.34761,0.15878 -0.0633,0.78391 -0.3327,1.03925 -0.38106,0.3611 -2.01319,0.50125 -1.47479,0.67361 0.84502,0.27052 0.007,-0.44682 0.90373,-0.85643 0.17382,-0.0794 0.39726,0.2622 0.57106,0.18282 0.17381,-0.0794 -0.007,-0.44024 0.16635,-0.51963 0.18473,-0.0844 2.65466,-0.56244 3.35431,-0.64478 1.30363,-0.15342 3.14265,-0.11985 4.49643,-0.27918 2.72274,-0.32044 -0.62197,-0.15953 0.90375,-0.85641 0.32372,-0.14787 5.6941,-0.67014 6.54227,-0.76997 0.77289,-0.091 1.45992,-0.82995 2.21223,-1.01042 0.53952,-0.12944 3.28192,-3.18486 3.46376,-3.26791 0.38863,-0.17753 0.56448,-0.60977 0.90373,-0.85645 0.94543,-0.68738 2.45684,-0.84808 3.52067,-1.16441 2.06526,-0.6141 3.79796,-2.19037 5.89923,-2.69445 2.56031,-0.61423 5.03522,-1.52777 7.61238,-2.14604 2.29402,-0.55032 4.15258,-2.23887 6.47029,-2.51164 0.94263,-0.11094 2.27793,-0.0181 3.18797,-0.12516 0.97525,-0.11479 1.79267,-0.84281 2.78327,-0.82762 0.39623,0.006 0.7638,0.45639 1.14209,0.36563 0.42293,-0.10146 0.5151,-0.67892 0.90376,-0.85642 0.0112,-0.005 7.41544,-0.93064 7.85073,-0.92396 0.59435,0.009 1.11881,0.49711 1.71315,0.54844 3.1788,0.27453 6.14782,1.39527 9.30319,2.40542 2.52245,0.80753 8.29742,1.92243 10.27895,3.29067 0.23059,0.15921 0.17412,0.54323 0.40471,0.70244 0.32609,0.22517 0.81599,0.14046 1.1421,0.36563 0.46117,0.31844 0.44842,0.98971 0.80941,1.40488 2.82985,3.25468 3.45939,6.44086 3.61996,10.32494 0.0901,2.17924 1.1441,8.32738 -0.71011,10.08447 -1.49675,1.4184 -8.49531,4.75936 -11.22739,5.57175 -0.4149,0.12337 -0.91982,-0.0235 -1.30846,0.15399 -0.38864,0.17753 -0.5151,0.67892 -0.90375,0.85644 -0.7773,0.35502 -1.77106,0.10505 -2.61692,0.30797 -1.22097,0.29292 -2.3186,0.80698 -3.52067,1.16443 -1.61286,0.47958 -1.30722,-0.19704 -3.18796,0.12517 -3.60277,0.61718 -8.08489,2.23869 -11.94244,1.90555 -2.75253,-0.23772 -5.81905,-0.43737 -8.66014,-0.48093 -1.27088,-0.0195 -2.79417,0.47934 -3.92537,0.46198 -0.39625,-0.006 -0.74585,-0.35958 -1.1421,-0.36562 -2.27617,-0.0349 -4.03438,0.28626 -6.37592,0.25033 -3.6411,-0.0558 -7.63315,1.50841 -11.22016,1.93056 -2.10755,0.24805 -4.86889,0.78099 -7.11333,0.58715 -0.39626,-0.0341 -0.74587,-0.35958 -1.1421,-0.36563 -1.31161,-0.0201 -3.02184,0.40652 -4.49642,0.27917 -0.39626,-0.0341 -0.74586,-0.35959 -1.14211,-0.36563 -0.28017,-0.004 -0.45722,0.3411 -0.73741,0.3368 -0.59435,-0.009 -1.12798,-0.61731 -1.71314,-0.54844 -1.02913,0.12113 -0.81379,0.31236 -1.87952,-0.0288 -0.3807,-0.12188 -1.0312,-0.71205 -1.14211,-0.36563 -0.0554,0.17321 0.3807,0.12187 0.57105,0.18281 l 2.28423,0.73127 5.13946,1.64533 c 3.36409,1.07697 6.22357,3.075 9.20884,4.66674 2.03578,1.08545 4.17262,1.49481 6.11524,2.53059 1.05913,0.56472 2.02018,1.60385 3.09361,2.13612 0.71458,0.35435 1.56963,0.37692 2.28421,0.73126 0.79892,0.39618 1.1858,1.31826 1.9515,1.77053 1.62918,0.96224 11.95543,3.95956 13.5846,4.92181 2.49614,1.47429 4.83321,3.16359 7.32934,4.6379 0.68487,0.4045 1.59934,0.32675 2.2842,0.73126 1.24769,0.73691 2.43687,2.10565 3.49833,2.83857 0.74681,0.51569 1.99778,0.80668 2.6889,1.43371 0.85235,0.77329 2.04703,2.5277 2.76091,3.17538 1.20162,1.09017 -0.0326,-1.67336 1.54681,1.06805 0.0954,0.16558 -0.20181,0.33748 -0.16636,0.51964 0.11519,0.59052 1.14732,2.38198 1.61882,2.80975 0.14546,0.13193 0.47566,0.0173 0.57104,0.18281 0.13426,0.23302 0.40635,2.08334 0.47671,2.44412 0.49598,2.54297 0.13841,-0.56812 0.97576,0.88526 0.19077,0.33114 -0.52348,0.70811 -0.3327,1.03925 0.0954,0.16556 0.47566,0.0172 0.57105,0.18281 0.18605,0.32295 0.38299,1.96361 0.4767,2.44412 0.072,0.36893 -0.68613,1.23416 -0.49906,1.55888 0.162,0.28116 0.63561,0.51372 0.80942,1.40487 0.0941,0.48254 -0.5193,1.06926 -0.49906,1.55887 0.0313,0.75696 -0.24086,1.51018 -0.0944,2.26131 0.0354,0.18216 0.47565,0.0172 0.57104,0.18281 0.18709,0.32471 -0.571,1.18995 -0.49905,1.55887 0.17208,0.8822 0.30465,1.56192 0.47672,2.44414 0.0376,0.19285 -0.50588,2.00563 -0.6654,2.07848 -0.34762,0.15877 -0.76142,-0.24375 -1.14213,-0.36563 -1.06572,-0.34118 -0.85036,-0.14992 -1.87949,-0.0288 -0.16127,0.0189 -4.36174,-0.8235 -4.73478,-0.94292 -1.17429,-0.37594 -1.34941,-0.34118 -2.45056,-0.21163 -1.22123,0.14373 -2.77614,-1.42343 -3.99735,-1.2797 -0.27587,0.0325 -0.46156,0.30434 -0.73741,0.3368 -1.21583,0.14309 -2.75174,-1.2606 -3.99737,-1.27971 -0.44301,-0.007 -0.86546,0.1608 -1.30847,0.154 -1.92843,-0.0296 -13.24729,-4.91733 -14.51466,-5.79245 -0.957,-0.66081 -1.94052,-1.97442 -2.92727,-2.65576 -1.2604,-0.87032 -3.03024,-1.4081 -4.2357,-2.50177 -0.41143,-0.37325 -0.39799,-1.03161 -0.80941,-1.40486 -0.29091,-0.26394 -0.7997,-0.1633 -1.14211,-0.36563 -1.09152,-0.6447 -1.92946,-1.88314 -2.92725,-2.65578 -4.47517,-3.46527 -17.73117,-9.45389 -21.46264,-13.74553 -1.03483,-1.19017 -1.24661,-1.46625 -2.35621,-2.47293 -0.41142,-0.37325 -0.39798,-1.03162 -0.8094,-1.40487 -0.29569,-0.26827 -1.37723,-0.77373 -1.54681,-1.06807 -0.20236,-0.35122 -0.036,-0.87085 -0.23836,-1.22207 -0.19077,-0.33114 -0.95133,-0.0345 -1.14209,-0.36563 -0.0954,-0.16556 0.26173,-0.35406 0.16635,-0.51962 -0.1908,-0.33113 -0.95134,-0.0345 -1.14212,-0.36564 -0.0954,-0.16556 0.26174,-0.35406 0.16635,-0.51962 -0.19076,-0.33113 -0.95132,-0.0345 -1.14209,-0.36563 -0.0954,-0.16556 0.2018,-0.33748 0.16635,-0.51963 -0.10581,-0.5424 -1.81729,-2.3004 -2.35622,-2.47293 -2.63928,6.49832 -3.47514,12.37803 -2.22962,18.76408 0.1066,0.54652 -0.52181,1.00809 -0.49905,1.55886 0.0231,0.55787 0.45681,1.36711 0.64306,1.9245 0.50472,1.51047 0.63209,3.92794 0.78706,5.40787 0.21241,2.02838 -0.13447,3.42816 0.216,5.22506 0.072,0.36892 -0.68613,1.23414 -0.49905,1.55887 0.0954,0.16556 0.47565,0.0172 0.57105,0.18282 0.41014,0.71187 -4.10014,12.80745 -4.36504,13.6349 -0.50932,1.59095 -1.58468,7.29343 -2.66161,8.31397 -0.19057,0.18053 -0.49161,0.22453 -0.7374,0.33681 -0.16635,0.51962 -0.19917,1.09909 -0.49905,1.55886 -0.28274,0.43348 -1.15577,0.26335 -1.47482,0.67361 -0.5308,0.68258 -0.77871,1.82391 -1.4028,2.41531 -0.38106,0.36109 -1.04566,0.36159 -1.47482,0.67362 -0.6254,0.4547 -1.22513,1.68811 -1.97385,2.23249 -1.29316,0.94021 -2.73499,1.78919 -4.01972,2.72328 -1.06288,-0.40547 -0.76886,1.09057 -1.07011,1.37604 -2.88757,2.7364 -7.71677,5.45153 -11.79844,5.38893 -2.213413,-0.0339 -4.793702,-1.7216 -5.948889,-3.05021 -2.482392,-2.85507 0.445094,-0.0358 -1.047741,-2.62693 -0.213295,-0.37023 -0.806683,-0.4977 -0.975758,-0.88526 -1.198561,-2.74737 -0.245076,-5.92826 -0.360005,-8.70842 -0.08174,-1.97733 -0.603625,-3.95207 -0.620693,-5.92749 -0.03111,-3.59829 0.36439,-7.15612 0.305389,-10.78692 -0.01606,-0.98801 -0.402131,-1.98474 -0.310346,-2.96375 0.219441,-2.34082 3.820416,-16.4273 3.411643,-18.52314 -0.275879,-1.41438 2.1834,-6.8202 2.661609,-8.31396 0.941288,-2.94027 2.099531,-6.06264 3.970061,-8.46798 0.5925,-0.76191 0.28903,-2.4458 0.9981,-3.11774 0.84369,-0.7995 1.02226,-1.22659 1.4028,-2.41529 0.15978,-0.4991 1.87572,-1.59453 1.40282,-2.41531 -0.19078,-0.33114 -0.75199,-0.41154 -1.14211,-0.36563 -0.92353,0.1087 -1.17505,0.75156 -1.64115,1.19324 -0.96248,0.91207 -4.053094,1.52635 -4.995475,1.83803 -4.524192,1.49632 -8.558224,4.0385 -13.106918,5.54293 -1.278271,0.42276 -2.37428,1.3691 -3.687012,1.68402 -1.54846,0.37148 -3.81994,-0.0298 -5.23382,0.61598 -0.388648,0.17751 -0.493929,0.72088 -0.903754,0.85644 -0.219151,0.0725 -3.98401,0.80918 -4.662776,0.79878 -0.396245,-0.006 -0.745847,-0.33149 -1.142104,-0.36563 -1.729808,-0.14939 -3.504018,0.76536 -5.233821,0.61596 -0.396259,-0.0341 -0.745859,-0.35959 -1.142106,-0.36563 -0.280172,-0.004 -0.457224,0.34111 -0.737406,0.33682 -2.025331,-0.0311 -3.988418,-0.75741 -6.043226,-0.78891 -2.989859,-0.0459 -9.652734,-3.53869 -12.230456,-5.06118 -1.448273,-0.85541 -3.049278,-1.04801 -4.402067,-1.98215 -1.758897,-1.21451 -3.472776,-2.48013 -5.045124,-3.90664 -0.780604,-0.7082 -0.838205,-2.10155 -1.618808,-2.80975 -2.35966,-2.14081 -4.094839,-4.90842 -2.976911,-8.40045 0.19981,-0.62414 0.356604,-2.14784 0.831752,-2.59811 2.467864,-2.33863 5.606136,-3.46377 8.610486,-5.26375 1.52427,-0.91324 3.452128,-2.42722 5.328177,-2.87729 2.644729,-0.63446 6.988942,-2.15557 9.491891,-2.11719 1.325038,0.0203 2.327863,0.40097 3.75902,0.0576 3.909018,-0.93776 8.945271,-1.36342 12.918204,-1.02029 1.729808,0.14939 3.50402,-0.76538 5.233823,-0.61597 0.396256,0.0341 0.745858,0.35959 1.142103,0.36563 0.714327,0.0109 1.34259,-0.40804 2.045863,-0.49081 5.714742,-0.67257 -2.709359,0.13251 2.450562,0.21163 2.123404,0.0326 4.410928,-0.95402 6.542278,-0.76995 4.605661,0.39775 -2.644496,0.52796 2.450556,0.21163 2.378114,-0.14763 6.949883,-0.0673 9.397549,0.14411 0.79247,0.0684 1.49176,0.66283 2.28421,0.73126 0.93331,0.64408 1.33153,-0.50175 2.04587,-0.49078 1.0102,0.0155 2.0144,0.26296 3.0216,0.39445 0.46195,0.0603 3.58398,0.60444 3.59268,0.57726 0.1109,-0.34642 -0.76141,-0.24375 -1.14212,-0.36563 l -4.56842,-1.46252 c -0.76139,-0.24375 -1.59933,-0.32675 -2.28421,-0.73126 -0.24213,-0.14304 -0.15611,-0.5699 -0.40469,-0.70245 -1.7578,-0.93723 -3.87991,-1.03122 -5.710534,-1.82815 -1.645917,-0.71652 -3.218259,-1.83761 -4.806766,-2.68458 -1.394049,-1.84381 -3.748822,-1.4575 -5.544189,-2.34777 -1.702035,-0.844 -2.930315,-2.29241 -4.640415,-3.20421 -2.160383,-1.15188 2.759624,3.26786 -3.259965,-1.61651 -0.433078,-0.35142 -0.397988,-1.03163 -0.809401,-1.40489 -0.290912,-0.26393 -0.82982,-0.12381 -1.142105,-0.36563 -0.562984,-0.43592 -0.817479,-1.15174 -1.380458,-1.58769 -0.312283,-0.24181 -0.829819,-0.12381 -1.142104,-0.36563 -1.258864,-0.97478 -2.159254,-2.29425 -3.331967,-3.35819 -2.076957,-1.88432 -3.7732,-3.8213 -5.688175,-5.83115 -0.877005,-0.92045 -2.153557,-1.6632 -2.927263,-2.655754 -1.166304,-1.49622 -1.666611,-3.42085 -2.832913,-4.91707 -0.266113,-0.341401 -0.762461,-0.515032 -0.975752,-0.885262 -0.426596,-0.740431 -0.138558,-1.669003 -0.476704,-2.444118 -0.427724,-0.980453 -1.223675,-1.819074 -1.61881,-2.809748 -1.362935,-3.417176 -1.898283,-7.357715 -1.574107,-10.815735 1.572989,-1.088051 -0.510813,-1.62655 -0.476701,-2.444128 0.07963,-1.908595 0.778663,-1.325712 0.831753,-2.598113 0.194238,-4.655326 2.35198,-9.426749 4.896173,-13.327392 0.576031,-0.883144 1.976565,-1.318827 2.544911,-2.049669 0.225597,-0.290098 0.107105,-0.749148 0.332702,-1.039246 0.390907,-0.502667 1.680145,-0.623575 2.21221,-1.010423 3.639283,-2.64602 8.32559,-3.893467 13.012558,-3.281612 1.428458,0.186476 2.9069,0.01179 4.330069,0.24046 2.048299,0.32912 13.779081,4.061933 15.418419,4.936011 1.138446,0.607007 1.816894,1.999939 2.927264,2.655762 6.6296,3.91568 9.74989,8.839251 13.39987,15.174482 0.63484,1.101919 1.79175,2.323928 2.0235,3.512187 0.48429,2.483009 1.17455,5.041309 2.00117,7.51519 0.85561,2.560627 1.89066,4.842536 2.00116,7.515188 0.0787,1.904488 1.00536,3.782092 1.3581,5.590686 0.29644,1.519924 0.0897,2.169324 0.14399,3.483354 0.0249,0.60137 0.52482,1.31832 0.64307,1.92451 0.071,0.36435 -0.52349,0.70811 -0.3327,1.03925 0.0954,0.16555 0.47565,0.0172 0.57104,0.18281 0.20236,0.35122 -0.70141,1.20766 -0.49905,1.55887 0.0954,0.16557 0.47566,0.0172 0.57105,0.18281 0.3376,0.58598 -2.88829,7.30501 -3.06632,7.61154 z"

const val roundScribble =
    "m 30.35228,87.013696 c 7.004825,-11.946528 11.715506,-25.490619 19.778188,-36.728247 4.28366,-5.97049 9.454785,-11.403096 14.898172,-16.38905 5.055388,-4.630565 10.332838,-9.32629 16.594669,-12.326701 1.393603,-0.667757 2.894958,-0.493358 4.301681,-0.936133 0.400747,-0.126138 0.620135,-0.61304 1.020884,-0.739177 1.364899,-0.429611 2.857843,0.47767 4.280491,0.324082 4.565517,-0.492893 -3.494772,-1.707291 2.52043,0.04238 1.837819,0.534573 0.800961,-0.490762 2.780947,-0.457474 0.386663,0.0065 3.939708,0.945262 4.519818,1.08445 0.40854,0.09802 0.85168,-0.07683 1.26022,0.02119 1.08393,0.26007 5.43213,2.202799 5.99818,3.126216 2.42712,3.959476 2.81795,12.336682 2.74483,16.685684 -0.049,2.911723 -1.46891,5.794084 -1.90838,8.539796 -0.1328,0.829702 -0.0282,1.680285 -0.0424,2.520431 -0.35442,1.086534 -0.79661,2.148267 -1.06326,3.259604 -0.88499,3.688513 1.25269,-1.748622 -0.30289,3.020281 -0.5091,1.560734 -1.46397,3.171857 -1.84482,4.759149 -0.062,0.258383 0.32174,0.507753 0.23933,0.760368 -0.29133,0.893125 -1.01125,1.606114 -1.30259,2.49924 -0.0824,0.252613 0.31355,0.505227 0.23933,0.760368 -0.97168,3.340549 -5.464005,10.483627 -7.294505,13.995753 -1.683916,3.230878 -5.891695,5.614711 -8.948633,7.41297 -1.118342,0.657869 -2.663586,0.571365 -3.801829,1.196647 -1.092151,0.599958 -2.395683,1.589756 -3.562501,1.957019 -0.85498,0.269107 -2.381309,0.09985 -3.280795,0.196949 -2.487625,0.268571 -4.086699,0.373399 -5.519518,-1.605484 -0.110203,-0.152161 -0.443439,-0.08129 -0.499849,-0.260518 -0.05641,-0.179213 0.257362,-0.311983 0.260516,-0.499844 0.03034,-1.804128 -0.102181,-3.67579 0.345275,-5.540713 0.521248,-2.172489 6.667472,-13.395281 7.815542,-14.995441 0.589836,-0.822103 1.588301,-1.261281 2.302287,-1.978208 1.511689,-1.517911 2.966333,-3.094336 4.365245,-4.716775 3.563467,-4.132865 7.158687,-7.771094 11.532623,-11.151238 0.536031,-0.41424 1.187498,-0.652338 1.781252,-0.978508 0.68059,-0.492784 1.38426,-0.955176 2.04177,-1.478356 2.32728,-1.85182 1.99125,-1.944529 4.34405,-3.456562 3.9371,-2.530174 -0.7945,1.021926 5.08324,-2.435676 0.51212,-0.301256 0.74558,-0.982283 1.2814,-1.239027 0.37888,-0.181545 0.85947,0.147325 1.26022,0.02119 0.80149,-0.252276 1.24027,-1.22608 2.04176,-1.478356 7.44755,-0.804037 -1.63473,0.514544 3.04147,-0.957321 0.17922,-0.05641 0.31198,0.257362 0.49985,0.260517 1.02047,0.01716 2.32098,-0.350575 3.28079,-0.196953 0.74211,0.118777 1.26858,0.866728 1.9994,1.042073 2.82514,0.677842 5.05175,1.997319 7.75824,3.407926 7.31299,3.81149 9.53927,4.903671 13.911,12.335366 1.24998,2.124913 2.37359,8.775878 2.32973,11.384306 -0.0308,1.833002 -0.62984,2.084571 -0.82393,4.019973 -0.13368,1.333006 0.0989,6.149296 -0.12713,7.561284 -0.0891,0.556582 -0.60679,0.963653 -0.78156,1.499545 -1.02288,3.135814 -0.77549,6.697343 -1.92956,9.800013 -2.83292,7.616153 -6.06934,15.462893 -10.4631,22.514363 -1.74345,2.79803 -1.07974,0.88291 -3.32318,2.71738 -0.5997,0.49038 -0.95434,1.234 -1.54191,1.73887 -3.02675,2.60068 -6.78216,4.12827 -10.18766,6.13157 -2.99278,1.76052 -4.64241,1.84943 -7.36433,3.15367 -1.85552,0.88909 -0.10887,0.81765 -2.54162,1.21783 -2.20667,0.36301 -5.85168,1.2296 -8.08233,0.87257 -0.3459,-0.0554 -4.575426,-1.26111 -4.759149,-1.84481 -1.707502,-5.42484 2.450925,-12.01988 4.816459,-16.55855 1.38943,-2.66587 2.56227,-5.45631 4.16829,-7.99758 3.87039,-6.124266 12.00659,-12.105426 17.17927,-17.107042 3.02683,-2.926739 5.95242,-6.024947 9.46966,-8.412665 5.37106,-3.646192 1.81031,-0.155575 6.62516,-4.174554 0.91227,-0.76147 1.60031,-1.781144 2.56281,-2.478047 3.50485,-2.53772 7.61258,-4.498115 11.20854,-6.87075 0.84454,-0.557232 1.45774,-1.420972 2.30228,-1.978204 4.17691,-2.755956 14.9202,-6.86603 20.05124,-7.982643 1.32389,-0.288108 2.71726,-0.148178 4.04116,-0.436284 0.77891,-0.169508 1.48856,-0.63243 2.28109,-0.717993 0.59073,-0.06377 1.16612,0.297534 1.76007,0.28171 2.18078,-0.05811 5.53809,-1.036993 7.8218,-0.372724 0.54124,0.157432 0.95832,0.624125 1.49955,0.781557 1.52146,0.442554 3.69824,0.256697 5.28019,0.845118 0.49605,0.184509 4.11478,1.82157 4.99848,2.605183 0.99001,0.877872 0.68999,1.3455 1.21784,2.541617 2.55521,5.790186 2.15013,8.745337 1.76632,14.904432 -0.85368,13.699417 -5.95301,26.652007 -11.49891,39.136479 -1.39942,2.90377 -2.13567,6.07388 -3.42912,9.01845 -2.50589,5.70475 -6.03771,11.58439 -8.85761,16.99484 -2.97066,5.6997 -5.88366,12.94471 -12.11723,15.93158 -0.71887,0.34445 -1.58243,0.33418 -2.28109,0.71799 -1.02118,0.56097 -1.82681,0.97775 -3.04147,0.95732 -3.83417,-0.0645 -5.4673,-1.43958 -6.71617,-5.40732 -0.45324,-1.43999 0.56031,-3.33514 0.58461,-4.78034 0.0114,-0.67734 -0.35417,-1.35694 -0.21815,-2.02057 0.1509,-0.73626 0.78005,-1.295 1.04208,-1.9994 0.6039,-1.62357 0.89664,-3.34645 1.34496,-5.01967 1.05157,-3.92465 3.0582,-7.08725 4.97103,-10.75733 2.40289,-4.61036 4.56895,-9.6288 7.79436,-13.73523 1.35842,-1.72948 3.28412,-2.92757 4.8651,-4.45626 2.94953,-2.85199 5.81105,-6.09024 8.96981,-8.673185 1.04886,-0.857667 2.46509,-1.162422 3.5625,-1.957017 1.57177,-1.138053 2.94137,-2.978027 4.60458,-3.956408 2.15298,-1.266503 5.03957,-2.182816 7.36433,-3.153668 1.49144,-0.622852 2.78115,-1.711087 4.32286,-2.196349 5.93502,-1.868082 0.6165,0.12708 4.04117,-0.436283 5.1688,-0.850279 9.59401,-0.720312 14.34101,1.753799 1.33294,0.694717 2.828,1.141517 3.99879,2.084147 5.64276,4.543087 3.14437,16.659724 1.6392,22.465724 -1.26529,4.88065 -1.20007,2.69945 -3.14741,7.25839 -1.41532,3.31345 -2.01261,6.96316 -3.4503,10.27867 -4.71205,10.86671 -9.92385,21.47972 -15.41294,32.01147 -2.9021,5.56818 -6.74949,15.02955 -10.92058,19.73342 -2.21821,2.50155 -4.89555,4.75842 -7.42789,6.93431 -2.54438,2.18621 -5.69712,4.08865 -8.66693,5.6529 -3.32936,1.75364 -16.16098,7.27164 -18.94559,2.20261 -1.75592,-3.19644 2.29685,-9.28516 3.732,-12.03873 2.18697,-4.19608 4.71798,-7.09163 7.99131,-10.45444 0.66825,-0.68651 1.07579,-1.61436 1.80245,-2.23872 8.93674,-7.67876 24.70949,-17.63173 36.49757,-11.48784 1.11114,0.57911 3.05546,1.24765 3.99879,2.08414 1.06172,0.17025 0.50761,1.29424 0.97852,1.78126 0.2612,0.27014 0.71851,0.27175 0.99968,0.52103 0.81169,0.71975 2.12975,2.78372 2.45688,3.82301 0.12614,0.40075 -0.14734,0.85947 -0.0212,1.26021 0.46331,1.47199 1.46376,2.90987 1.43598,4.56221 -0.0989,5.8814 -2.14984,12.75346 -4.3378,18.07929 -3.54877,8.63827 -0.1795,-0.57028 -2.34466,4.49863 -2.81996,6.60187 -5.80296,13.65088 -9.66036,19.7546 -2.04654,3.23831 -5.41815,5.65247 -7.70961,8.69437 -2.38635,3.16788 1.30592,-0.27095 -2.32347,3.23842 -1.39429,1.34818 -0.87136,0.0787 -2.30228,1.97821 -1.05015,1.39406 -1.23555,2.57962 -2.584,3.73826 -1.51717,1.30362 -1.70227,2.72179 -2.84451,4.23812 -3.12962,4.15457 -6.86998,7.55893 -10.77225,10.9119 -1.02911,0.88424 -3.61351,2.80878 -4.84392,3.19605 -0.41924,0.13196 -2.02366,0.85216 -1.78124,0.97851 0.16661,0.0868 0.17368,-0.33323 0.26052,-0.49985"

const val spikyScribble =
    "m 55.239144,28.183236 c 1.315217,-0.187888 2.673112,-0.181903 3.945652,-0.563665 0.648872,-0.19466 1.048319,-0.913104 1.690994,-1.127329 0.534739,-0.178246 1.149017,0.15485 1.690995,0 0.807931,-0.230838 1.470403,-0.825693 2.25466,-1.127329 1.690994,-0.563665 3.381986,-1.12733 5.07298,-1.690994 0.375777,0 0.770837,0.118831 1.127332,0 4.05435,-1.351451 6.714609,-2.460061 10.709629,-3.381989 4.497737,-1.037939 9.499439,-1.714402 14.091618,-2.254659 7.288356,-0.857453 14.646096,-2.254659 21.982926,-2.254659 0.85397,0 3.16194,-0.391853 3.94565,0 0.92956,0.464779 2.25466,1.19228 2.25466,1.12733 0,-0.187888 -0.43081,-0.132857 -0.56367,0 -0.13285,0.132857 0.16806,0.479638 0,0.563664 -0.41704,0.208526 -2.14115,0.292797 -2.81832,0.563665 -0.55166,0.220664 -1.12733,0.375777 -1.69099,0.563665 -2.25466,0.939441 -4.53657,1.815989 -6.76398,2.818324 -3.21917,1.448628 -6.45845,2.865186 -9.5823,4.509317 -9.898873,5.209933 -19.449262,11.086917 -29.310566,16.346278 -4.027543,2.148022 -7.558013,5.052398 -11.836958,6.763977 -1.270016,0.508005 -2.695699,0.571794 -3.945654,1.127329 -0.485625,0.215834 -0.652005,0.889667 -1.127329,1.127329 -1.149689,0.574844 -3.743857,1.358714 -5.072983,1.690994 -0.220126,0.05503 -1.690995,-0.304625 -1.690995,0.563666 0,0.187888 0.375777,0 0.563666,0 0.751551,0 1.503106,0 2.25466,0 3.822012,0 7.491857,-0.545571 11.273292,-1.127329 3.572079,-0.549553 7.157429,-1.024959 10.70963,-1.690994 2.463808,-0.461966 4.84732,-1.328021 7.327643,-1.690995 7.863274,-1.150723 15.769074,-2.000581 23.673914,-2.818326 20.23174,-2.092936 40.53099,-4.509317 60.8758,-4.509317 1.56401,0 18.03727,-0.451168 18.03727,0.563666 0,0.187888 -0.37578,0 -0.56367,0 -0.56366,0 -1.12733,0 -1.69099,0 -1.50311,0 -3.01064,-0.115285 -4.50932,0 -7.48313,0.575624 -14.64521,1.634913 -21.98292,3.381988 -10.71473,2.551126 -21.6138,5.169001 -32.1289,8.45497 -0.96575,0.301799 -1.84067,0.866624 -2.81832,1.127331 -1.85139,0.493705 -3.79841,0.586671 -5.63664,1.127329 -1.37278,0.403757 -2.58817,1.238498 -3.94566,1.690994 -2.76713,0.922377 -5.61286,1.598787 -8.45497,2.254658 -4.653736,1.073941 -8.941436,2.569699 -13.527953,3.945654 -1.774479,0.532344 -3.850333,0.680752 -5.636647,1.127329 -2.565963,0 -0.03224,-0.265713 -1.690994,0.563665 -0.230603,0.115301 -2.660441,0.157883 -2.818323,0 -0.751554,-0.751554 0.93944,-0.563665 -0.563666,-0.563665 -0.199707,0 -2.46852,0.213863 -1.690994,-0.563663 0.50007,-0.500071 4.458052,0 5.072983,0 4.697206,0 9.394412,0 14.091618,0 15.811366,0 31.602816,-0.05845 47.347836,1.690994 10.1381,1.126453 20.38203,3.324135 30.4379,5.072981 5.0733,0.882316 10.19833,1.981554 15.21894,2.818323 2.49838,0.416396 4.57641,0.597209 6.76398,1.690994 0.27211,0.136057 5.15647,1.607505 3.94565,2.818326 -0.39857,0.398571 -1.12733,0 -1.69099,0 -5.26254,0 -10.53626,0.70762 -15.78261,1.127329 -11.24796,0.899837 -22.5451,1.27341 -33.81989,1.690994 -7.90667,0.292838 -15.77847,1.315019 -23.67392,1.690994 -7.51927,0.358061 -15.00758,0.563664 -22.54658,0.563664 -1.31522,0 -2.655981,0.257937 -3.945658,0 -6.52788,-1.305576 -0.955101,-1.127329 -5.072981,-1.127329 -0.01543,0 -3.381989,-0.850482 -3.381989,-0.563666 0,0.187889 0.375777,0 0.563663,0 0.563666,0 1.127332,0 1.690995,0 4.876027,0 -0.06098,-0.237662 3.945654,0.563666 4.344216,0.868844 8.633606,1.235675 12.964286,2.25466 3.77045,0.887164 7.4962,1.959893 11.2733,2.818323 6.66858,1.515589 13.5486,2.288476 20.29193,3.381989 11.66017,1.890837 23.40336,4.198675 34.94721,6.763975 1.33528,0.296727 2.60437,0.859073 3.94565,1.127331 0.55272,0.110543 1.14416,-0.13671 1.691,0 1.72924,0.432311 3.3591,1.201307 5.07298,1.690997 6.83674,1.95335 12.7606,3.51105 18.60094,7.8913 0.56103,0.42078 5.3728,4.20949 5.07298,4.50932 -0.26571,0.26571 -0.77084,-0.11883 -1.12733,0 -3.0102,1.0034 -6.35438,0.56366 -9.5823,0.56366 -12.96429,0 -25.92858,0 -38.89287,0 -5.90167,0 -12.20019,0.60376 -18.03727,-0.56366 -5.32894,-1.06579 -10.49378,-2.77036 -15.78261,-3.94565 -0.55024,-0.12228 -1.14416,0.13671 -1.691,0 -0.25777,-0.0644 -0.31158,-0.47964 -0.56366,-0.56367 -0.49756,-0.16585 -1.66251,0 -2.25466,0 -0.18789,0 -0.56366,-0.18788 -0.56366,0 0,0.0875 2.79191,-0.16482 3.94565,0 1.71483,0.24498 3.39923,0.681 5.07298,1.12733 4.91749,1.31133 9.70044,2.61166 14.65528,3.94565 6.70254,1.80453 13.65743,3.0316 20.29194,5.07299 3.26877,0.42712 5.97275,2.36669 9.01863,3.38199 1.46986,0.48995 3.03946,0.63737 4.50932,1.12732 8.6561,2.88537 17.07104,6.31803 25.36491,10.14597 2.81386,1.2987 4.43355,1.2105 7.32764,2.81832 2.75281,1.52934 -0.39819,0.80672 2.25466,1.691 0.55837,0.18612 2.46735,0.77635 2.81833,1.12733 0.13285,0.13286 -0.13286,0.43081 0,0.56366 0.13285,0.13286 0.4308,-0.13285 0.56366,0 0.80814,0.80814 -2.23911,0.56367 -3.38199,0.56367 -5.07298,0 -10.14596,0 -15.21895,0 -10.19571,0 -20.32412,-1.01674 -30.43789,-1.691 -4.33241,-0.28883 -8.62353,-1.41969 -12.96429,-1.69099 -1.50018,-0.0938 -3.01003,0.10709 -4.50932,0 -4.4466,-0.31762 -10.87521,-0.72572 -15.21894,-1.69099 -3.29273,-0.73172 0.92897,-0.56367 -2.25466,-0.56367 -0.37578,0 -1.50311,0 -1.12733,0 3.59446,0 6.83354,3.73367 9.5823,5.63665 2.57106,1.77996 5.3295,3.27972 7.8913,5.07298 0.9856,0.68992 1.80334,1.60876 2.81833,2.25466 1.06334,0.67667 2.34943,0.96821 3.38199,1.69099 0.87072,0.60951 1.38393,1.64515 2.25465,2.25466 1.03256,0.72279 2.30121,1.04253 3.38199,1.691 1.96663,1.17998 3.70174,2.71434 5.63665,3.94565 5.77974,3.67802 11.92108,7.08787 18.03727,10.14596 3.69446,1.84723 7.4588,4.04728 11.2733,5.63665 3.96736,1.65307 7.9657,3.13736 11.83695,5.07298 3.01183,1.50591 6.79022,3.83817 10.14597,4.50932 0.56166,0.11233 4.49496,1.14169 3.94565,1.691 -0.49732,0.49732 -9.78919,-0.95998 -10.70963,-1.12733 -8.46076,-1.53832 -16.89268,-3.1716 -25.36491,-4.50932 -1.86515,-0.2945 -3.75776,-0.37578 -5.63665,-0.56367 -2.25466,-0.37577 -4.51202,-0.73568 -6.76397,-1.12733 -8.75559,-1.5227 -17.07553,-4.08931 -25.92858,-5.07298 -4.30036,-0.47781 -6.8927,-1.58072 -11.2733,-2.25466 -2.16875,-0.33365 -3.94565,-0.6703 -3.94565,-0.56366 0,0.0341 1.5844,-0.0426 1.69099,0 0.78017,0.31206 1.52014,0.71926 2.25466,1.12733 0.93944,0.56366 1.87889,1.12733 2.81833,1.69099 0.56366,0.56367 1.02773,1.24882 1.69099,1.691 0.49437,0.32957 1.12733,0.37577 1.691,0.56366 0.75155,0.56366 1.51311,1.11423 2.25465,1.691 2.52052,1.96039 5.15053,3.99217 7.89131,5.63664 1.08078,0.64847 2.31864,1.01432 3.38199,1.691 1.01499,0.6459 1.84816,1.5432 2.81832,2.25466 1.87888,1.31521 3.75777,2.63043 5.63665,3.94565 0.75155,0.37578 1.54785,0.67295 2.25466,1.12733 3.86461,2.48439 7.35351,5.49588 11.27329,7.8913 7.52878,4.60092 15.22212,8.45762 21.98293,14.09162 2.28785,1.90654 5.35285,2.98625 7.8913,4.50932 2.01267,1.2076 4.09274,2.89186 6.20032,3.94565 0.23678,0.1184 2.2541,0.56423 1.69099,1.12733 -0.16752,0.16752 -8.3861,0 -8.45497,0 -3.54795,0 -7.17633,-0.29444 -10.70963,0 -1.32398,0.11033 -2.61913,0.48997 -3.94565,0.56367 -8.76112,0.48673 -17.71772,0 -26.49224,0 -0.93945,0 -1.87889,0 -2.81833,0 -5.82454,0 -11.64907,0 -17.4736,0 -0.75156,0 -1.72324,0.53143 -2.25466,0 -0.13286,-0.13286 0,-0.37578 0,-0.56367"

const val bean =
    "M 214.20368,195.16097 C 191.48236,221.15978 171.48455,174.95495 145.49303,161.85407 124.22836,151.13574 98.157856,165.534 75.146551,159.40634 58.07523,154.86043 53.62654,135.06526 52.819213,117.54605 51.563096,90.288042 61.757879,70.332075 91.51402,62.068502 c 29.75613,-8.263572 60.35663,11.490572 83.8927,38.730978 23.53607,27.2404 61.51828,68.36269 38.79696,94.36149 z"

object Scribbles {
    val daisyPath = PathParser().parsePathString(daisy)
    val roundScribblePath = PathParser().parsePathString(roundScribble)
    val spikyScribblePath = PathParser().parsePathString(spikyScribble)
    val beanPath = PathParser().parsePathString(bean)
}

@Composable
fun Daisy(
    modifier: Modifier = Modifier,
    colors: List<Color> =  listOf(Sherbet, OrangeSquash)
) {
    val path = remember {
        Scribbles.daisyPath.toPath()
    }
    val brush = remember {
        Brush.linearGradient(colors = colors)
    }
    ScribblePath(path, brush, modifier)
}

@Composable
fun Bean(
    modifier: Modifier = Modifier,
    colors: List<Color> =  listOf(Sherbet, OrangeSquash)
) {
    val path = remember {
        Scribbles.beanPath.toPath()
    }
    val brush = remember {
        Brush.linearGradient(colors = colors)
    }
    ScribblePath(path, brush, modifier)
}

@Composable
fun RoundScribble(
    modifier: Modifier = Modifier,
    colors: List<Color> =  listOf(Sherbet, OrangeSquash)
) {
    val path = remember {
        Scribbles.roundScribblePath.toPath()
    }
    val brush = remember {
        Brush.linearGradient(colors = colors)
    }
    ScribblePath(path, brush, modifier)
}

@Composable
fun SpikyScribble(
    modifier: Modifier = Modifier,
    colors: List<Color> =  listOf(Sherbet, OrangeSquash)
) {
    val path = remember {
        Scribbles.spikyScribblePath.toPath()
    }
    val brush = remember {
        Brush.linearGradient(colors = colors)
    }
    ScribblePath(path, brush, modifier)
}

@Composable
fun ScribblePath(path: Path, brush: Brush, modifier: Modifier = Modifier, strokeWidth: Dp = 8.dp) {
    val bounds = path.getBounds()
    val progress = remember {
        Animatable(0f)
    }
    LaunchedEffect(Unit) {
        progress.animateTo(
            1f,
            animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing))
        )
    }
    Spacer(modifier = modifier
        .padding(8.dp)
        .aspectRatio(bounds.width / bounds.height)
        .drawWithCache {
            val matrix = fromBoundsToComposeView(bounds, width = size.width, height = size.height)
            path.transform(matrix)
            val lines = path.asAndroidPath().flatten(0.5f)
            val pathMeasure = PathMeasure()
            pathMeasure.setPath(path, false)
            val totalLength = pathMeasure.length
            onDrawBehind {
                val currentLength = totalLength * progress.value
                lines.forEach { line ->
                    if (line.startFraction * totalLength < currentLength) {
                        drawLine(
                            brush = brush,
                            start = Offset(line.start.x, line.start.y),
                            end = Offset(line.end.x, line.end.y),
                            strokeWidth = strokeWidth.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }
            }
        })
}

fun fromBoundsToComposeView(
    bounds: Rect = Rect(-1f, -1f, 1f, 1f),
    width: Float,
    height: Float
): Matrix {
    val originalWidth = bounds.right - bounds.left
    val originalHeight = bounds.bottom - bounds.top
    val scale = min(width / originalWidth, height / originalHeight)
    val newLeft = bounds.left - (width / scale - originalWidth) / 2
    val newTop = bounds.top - (height / scale - originalHeight) / 2
    val matrix = Matrix()
    matrix.translate(-newLeft, -newTop)
    matrix.scale(scale, scale)
    return matrix
}

@Preview
@Composable
private fun PreviewDaisy() {
    Daisy(
        Modifier
            .fillMaxSize()
    )
}

@Preview
@Composable
private fun PreviewBean() {
    Bean(
        Modifier
            .background(Pasta)
    )
}

@Preview
@Composable
private fun PreviewRoundScribble() {
    RoundScribble(
        Modifier
            .background(Licorice)
    )
}

@Preview
@Composable
private fun PreviewSpikyScribble() {
    SpikyScribble(
        Modifier
            .background(Aubergette)
            .fillMaxSize()
    )
}