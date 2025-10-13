package com.example.maneylink.disseny

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Composable que muestra un botón de acción con texto personalizado.
 *
 * @param texto Texto que se muestra dentro del botón.
 * @param onClick Acción que se ejecuta al hacer clic en el botón.
 *
 * El botón ocupa todo el ancho disponible y deja un espacio inferior de 12dp.
 * Ideal para formularios o acciones destacadas en la interfaz.
 */
@Composable
fun BotonAccion(texto: String, onClick: () -> Unit) {
    Column {
        Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
            Text(texto)
        }
        Spacer(Modifier.height(12.dp))
    }
}