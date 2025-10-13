package com.example.maneylink.disseny

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

/**
 * Componente Compose que muestra un campo desplegable con una lista de opciones.
 *
 * @param label Etiqueta que se muestra encima del campo de texto.
 * @param opciones Lista de opciones disponibles para seleccionar.
 * @param seleccion Opción actualmente seleccionada.
 * @param onSeleccionar Callback que se ejecuta cuando el usuario selecciona una opción.
 *
 * Este componente utiliza `ExposedDropdownMenuBox` de Material 3 para mostrar
 * un menú desplegable con estilo moderno. El campo es de solo lectura y se actualiza
 * automáticamente al seleccionar una opción del menú.
 *
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CamposDesplegable(label: String, opciones: List<String>, seleccion: String, onSeleccionar: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = seleccion,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        onSeleccionar(opcion)
                        expanded = false
                    }
                )
            }
        }
    }
}