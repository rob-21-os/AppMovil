package com.example.maneylink.disseny


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


/**
 * Composable que muestra un campo de texto con etiqueta.
 *
 * @param label Texto que se muestra como etiqueta del campo.
 * @param valor Valor actual del campo de texto.
 * @param onValorChange Función que se ejecuta cuando el valor cambia.
 *
 * El campo ocupa todo el ancho disponible y deja un espacio inferior de 12dp.
 * Ideal para formularios o entradas de usuario.
 */
@Composable
fun CampoTexto(label: String, valor: String, onValorChange: (String) -> Unit) {
    Column {
        TextField(
            value = valor,
            onValueChange = onValorChange,
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
    }
}

