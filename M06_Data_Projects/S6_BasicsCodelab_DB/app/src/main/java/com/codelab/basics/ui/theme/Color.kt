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

package com.codelab.basics.ui.theme

import androidx.compose.ui.graphics.Color

val Navy = Color(0xFF073042)
val Blue = Color(0xFF4285F4)
val LightBlue = Color(0xFFD7EFFE)
val Chartreuse = Color(0xFFEFF7CF)
val CustomRed = Color(0xFFFF0000)

// Pokémon Type Colors
val NormalTypeColor = Color(0xFFA8A77A)
val FireTypeColor = Color(0xFFEE8130)
val WaterTypeColor = Color(0xFF6390F0)
val ElectricTypeColor = Color(0xFFF7D02C)
val GrassTypeColor = Color(0xFF7AC74C)
val IceTypeColor = Color(0xFF96D9D6)
val FightingTypeColor = Color(0xFFC22E28)
val PoisonTypeColor = Color(0xFFA33EA1)
val GroundTypeColor = Color(0xFFE2BF65)
val FlyingTypeColor = Color(0xFFA98FF3)
val PsychicTypeColor = Color(0xFFF95587)
val BugTypeColor = Color(0xFFA6B91A)
val RockTypeColor = Color(0xFFB6A136)
val GhostTypeColor = Color(0xFF735797)
val DragonTypeColor = Color(0xFF6F35FC)
val DarkTypeColor = Color(0xFF705746)
val SteelTypeColor = Color(0xFFB7B7CE)
val FairyTypeColor = Color(0xFFD685AD)


// Function to get color based on Pokémon type
fun getTypeColor(type: String): Color {
    return when (type) {
        "Normal" -> NormalTypeColor
        "Fire" -> FireTypeColor
        "Water" -> WaterTypeColor
        "Electric" -> ElectricTypeColor
        "Grass" -> GrassTypeColor
        "Ice" -> IceTypeColor
        "Fighting" -> FightingTypeColor
        "Poison" -> PoisonTypeColor
        "Ground" -> GroundTypeColor
        "Flying" -> FlyingTypeColor
        "Psychic" -> PsychicTypeColor
        "Bug" -> BugTypeColor
        "Rock" -> RockTypeColor
        "Ghost" -> GhostTypeColor
        "Dragon" -> DragonTypeColor
        "Dark" -> DarkTypeColor
        "Steel" -> SteelTypeColor
        "Fairy" -> FairyTypeColor
        else -> Navy // Default color
    }
}