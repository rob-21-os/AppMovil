package com.example.maneylink.disseny

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Composable que aplica un fondo con degradado vertical a toda la pantalla.
 *
 * @param colors Lista de colores que definen el degradado (por defecto azul a celeste).
 * @param content Contenido composable que se renderiza sobre el fondo.
 *
 * Ideal para envolver pantallas completas con un estilo visual atractivo.
 */
@Composable
fun GradientBackground(
    colors: List<Color> = listOf(Color(0xFF2196F3), Color(0xFF21CBF3)), // azul → celeste
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(colors)
            )
    ) {
        content()
    }
}
