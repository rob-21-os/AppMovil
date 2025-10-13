package com.example.maneylink.dadesUtils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Composable que dibuja un gráfico de barras verticales a partir de una lista de valores enteros.
 * Cada barra se alinea en la parte inferior y muestra su valor encima.
 * El diseño es responsivo y distribuye las barras de forma equitativa en el ancho disponible.
 */
@Composable
fun BarChart(data: List<Int>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp), // altura total del gráfico
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom // 👈 clave para alinear las barras abajo
    ) {
        data.forEach { value ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .height(value.dp)
                        .width(20.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.Blue)
                        .shadow(4.dp, RoundedCornerShape(6.dp))
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "$value", fontSize = 12.sp)
            }
        }
    }
}