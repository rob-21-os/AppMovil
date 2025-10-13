package com.example.maneylink.disseny

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Composable que muestra un botón de icono con un menú desplegable.
 *
 * @param icon Icono que se muestra en el botón.
 * @param contentDescription Descripción accesible del botón para lectores de pantalla.
 * @param menuItems Lista de pares (texto, acción) que representan las opciones del menú.
 *
 * Al hacer clic en el botón, se despliega un menú con las opciones proporcionadas.
 * Cada opción ejecuta su acción correspondiente al seleccionarse.
 */
@Composable
fun MenuButton(
    icon: ImageVector,
    contentDescription: String,
    menuItems: List<Pair<String, () -> Unit>>
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(icon, contentDescription = contentDescription, tint = Color.White)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            menuItems.forEach { (text, onClick) ->
                DropdownMenuItem(
                    text = { Text(text) },
                    onClick = {
                        expanded = false
                        onClick()
                    }
                )
            }
        }
    }
}
