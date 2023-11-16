/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Code available at https://developer.android.com/codelabs/jetpack-compose-theming#0

package com.codelab.basics

import com.codelab.basics.ui.theme.getTypeColor
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.style.TextAlign
import com.codelab.basics.ui.theme.PokemonGbFontFamily
import androidx.compose.ui.unit.sp
import com.codelab.basics.ui.theme.CustomRed
import kotlinx.coroutines.delay



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dbClass = DBClass(this)
        val pokemonRecords = dbClass.getPokemonRecords()

        setContent {
            BasicsCodelabTheme {
                MyApp(modifier = Modifier.fillMaxSize(), pokemonData = pokemonRecords.toList())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier, pokemonData: List<Array<String>>) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier = modifier.fillMaxSize(),
        color = CustomRed // Set the background color to red
    ) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            PokemonList(pokemonData = pokemonData)
        }
    }
}
@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Welcome to the Pokémon Database!",
            textAlign = TextAlign.Center, // Center the text alignment
            modifier = Modifier.padding(horizontal = 16.dp) // Add padding for better centering
        )
        Button(
            onClick = onContinueClicked,
            modifier = Modifier.padding(24.dp)
        ) {
            Text("Go Catch 'em all!")
        }
    }
}

@Composable
fun PokemonList(modifier: Modifier = Modifier, pokemonData: List<Array<String>>) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = pokemonData) { data ->
            PokemonCard(pokemon = data)
        }
    }
}

@Composable
private fun PokemonCard(pokemon: Array<String>) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    // Retrieve image resource
    val imageResId = context.resources.getIdentifier(pokemon[5], "drawable", context.packageName)
    val imagePainter = if (imageResId != 0) painterResource(id = imageResId) else painterResource(id = R.drawable.pokemon_logo)

    Card(
        colors = CardDefaults.cardColors(containerColor = getTypeColor(pokemon[3])),
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .animateContentSize(animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)),
            verticalAlignment = Alignment.CenterVertically // This will center the image vertically
        ) {
            // Image on the left, vertically centered
            Image(
                painter = imagePainter,
                contentDescription = "Pokemon Sprite",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )

            // Text and other components next to the image
            Column(
                modifier = Modifier
                    .padding(start = 16.dp) // Add some space between the image and text
            ) {
                Text(
                    text = "#${pokemon[2]} ${pokemon[1]}",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = PokemonGbFontFamily, // Custom font
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp // Adjust the size as needed
                    )
                )

                // Expandable content
                if (expanded) {
                    Spacer(Modifier.height(8.dp)) // Space between name/number and type

                    // Type text shown when expanded
                    Text(
                        text = "Type: ${pokemon[3]}",
                        style = MaterialTheme.typography.bodyLarge.copy(fontFamily = PokemonGbFontFamily) // Custom font with default bodyLarge style
                    )

                    // Evolution text shown when expanded
                    Text(
                        text = if (pokemon[4].isNotEmpty() && pokemon[4] != "Does not evolve") "Evolution: ${pokemon[4]}" else "",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontFamily = PokemonGbFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp) // Custom font with default bodyLarge style
                    )
                }
            }

            Spacer(Modifier.weight(1f)) // This will push the IconButton to the end

            // Icon button to expand and collapse card details
            IconButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
                    contentDescription = if (expanded) stringResource(R.string.show_less) else stringResource(R.string.show_more)
                )
            }
        }
    }
}

@Composable
private fun calculateImageSize(screenWidth: Dp): Dp {
    // Define image size based on screen width
    return when {
        screenWidth < 400.dp -> 80.dp  // Smaller size for small screens
        screenWidth < 600.dp -> 100.dp // Medium size for medium screens
        else -> 120.dp                  // Larger size for large screens
    }
}
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)


@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

