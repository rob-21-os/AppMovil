package com.example.maneylink.dadesUtils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


/**
 * Composable que muestra un selector de fecha personalizado.
 * Escala automáticamente según el ancho disponible y elimina el título por defecto.
 * Destaca el día actual en rojo y el día seleccionado en blanco sobre fondo azul.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun diaDelMes() {
    val datePickerState = rememberDatePickerState()

    BoxWithConstraints {
        val scale = if (maxWidth < 200.dp) maxWidth / 200.dp else 0.59f // Escala si es muy estrecho

        Box(
            modifier = Modifier
                .requiredWidthIn(min = 200.dp)
                .scale(scale)
        ) {
            DatePicker(
                state = datePickerState,
                title = {}, // Elimina el título
                showModeToggle = false,
                colors = DatePickerDefaults.colors(
                    todayContentColor = Color.Red,
                    selectedDayContentColor = Color.White,
                    selectedDayContainerColor = Color.Blue
                )
            )
        }
    }
}
