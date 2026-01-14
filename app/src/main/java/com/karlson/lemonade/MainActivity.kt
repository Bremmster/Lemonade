package com.karlson.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.karlson.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                MakeLemonade()
            }
        }
    }
}

@Composable
fun MakeLemonade(modifier: Modifier = Modifier) {
    var lemonadeState by remember { mutableStateOf(LemonadeState.TREE) }
    var squeezesRequired: Int by remember { mutableIntStateOf(2) }
    var squeezesDone: Int by remember { mutableIntStateOf(0) }

    Box(
        modifier
            .fillMaxSize()
    ) {
        Column(
            Modifier
                .align(Alignment.TopCenter)
                .background(Color(0xFFFFEB3B))
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(
                text = "Lemonade",
                fontSize = 18.sp,
                fontWeight = FontWeight(
                    weight = 800
                ),
                modifier = modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
            )
        }
        Column(
            Modifier.align(Alignment.Center),
        ) {
            Button(
                onClick = {
                    lemonadeState = when (lemonadeState) {
                        LemonadeState.TREE -> {
                            squeezesRequired = (2..4).random()
                            LemonadeState.LEMON
                        }

                        LemonadeState.LEMON -> {
                            squeezesDone++
                            if (squeezesDone >= squeezesRequired) {
                                squeezesDone = 0
                                LemonadeState.LEMONADE_GLASS
                            } else {
                                LemonadeState.LEMON
                            }
                        }

                        LemonadeState.LEMONADE_GLASS -> LemonadeState.EMPTY_GLASS
                        LemonadeState.EMPTY_GLASS -> LemonadeState.TREE
                    }
                },
                colors = ButtonColors(
                    contentColor = Color(0xFFC3ECD2),
                    containerColor = Color(0xFFC3ECD2),
                    disabledContainerColor = Color(0xFFC3ECD2),
                    disabledContentColor = Color(0xFFC3ECD2)
                ),
                shape = RoundedCornerShape(24.dp),
                modifier = modifier.align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painterResource(getImage(lemonadeState)),
                    contentDescription = stringResource(getContentDescription(lemonadeState)),
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text = stringResource(getText(lemonadeState)),
                fontSize = 18.sp,
                modifier = modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

enum class LemonadeState {
    TREE,
    LEMON,
    LEMONADE_GLASS,
    EMPTY_GLASS,
}

fun getImage(state: LemonadeState): Int =
    when (state) {
        LemonadeState.TREE -> R.drawable.lemon_tree
        LemonadeState.LEMON -> R.drawable.lemon_squeeze
        LemonadeState.LEMONADE_GLASS -> R.drawable.lemon_drink
        LemonadeState.EMPTY_GLASS -> R.drawable.lemon_restart
    }


fun getContentDescription(state: LemonadeState): Int =
    when (state) {
        LemonadeState.TREE -> R.string.description_lemon_tree
        LemonadeState.LEMON -> R.string.description_lemon
        LemonadeState.LEMONADE_GLASS -> R.string.description_lemonade_glass
        LemonadeState.EMPTY_GLASS -> R.string.description_empty_glass
    }


fun getText(state: LemonadeState): Int =
    when (state) {
        LemonadeState.TREE -> R.string.step_tap_the_lemon_tree
        LemonadeState.LEMON -> R.string.step_squeeze_the_lemon
        LemonadeState.LEMONADE_GLASS -> R.string.step_drink_the_lemonade
        LemonadeState.EMPTY_GLASS -> R.string.step_empty_glass
    }


@Preview(showBackground = true)
@Composable
fun MakeLemonadePreview() {
    LemonadeTheme {
        MakeLemonade()
    }
}
