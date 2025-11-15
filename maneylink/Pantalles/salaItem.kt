package com.example.moneylink.Pantalles.Salas

import androidx.compose.animation.AnimatedVisibility
import com.example.moneylink.dataClass.Sala


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moneylink.dadesServidor.AuthViewModel
import kotlin.collections.isNotEmpty


@Composable
fun SalaItem(
    sala: Sala,
    expanded: Boolean, //  control externo
    onExpandChange: () -> Unit, //  notifica cambios

    navController: NavHostController,
    viewModel: AuthViewModel,
    token: String?,
    onClick: (Sala) -> Unit = {},
    onDateRangeClick: (Sala) -> Unit = {},
    onUpdateClick: (Sala) -> Unit = {},
    onDeleteClick: (Sala) -> Unit = {},
    onMesClick: (Sala) -> Unit = {},
) {
  //  var expanded by remember { mutableStateOf(false) } //  Controla la expansión

    val salaMes by viewModel.salaMes.observeAsState()

    LaunchedEffect(expanded) {
        if (expanded) {

            viewModel.loadSalaMes(token,  sala.id, 0)
        }
    }

    Card(
        shape = RoundedCornerShape(
            topStart = 0.dp,   // esquina superior izquierda
            topEnd = 25.dp,      // esquina superior derecha
            bottomEnd = 0.dp,   // esquina inferior derecha
            bottomStart = 8.dp// esquina inferior izquierda
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable { onExpandChange() }, // Expandir/colapsar al tocar el Carde xpanded = !expanded

        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // 🔹 Cabecera de la sala
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = sala.name,

                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                Text(text = sala.roleSala)


                /*
                // 🔹 Resumen básico
                Text("Ingresos: ${sala.ingresos ?: 0.0} €")
                Text("Gastos: ${sala.gastos ?: 0.0} €")
                Text(
                    text = "Balance: ${(sala.balance ?: 0.0)} €",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = if ((sala.balance ?: 0.0) >= 0)
                            Color(0xFF2E7D32)
                        else
                            Color(0xFFC62828)
                    )
                )*/

                //  Icono de expandir/colapsar
                IconButton(onClick = { onExpandChange }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                        contentDescription = if (expanded) "Colapsar" else "Expandir"
                    )
                }
            }



            // 🔽 Contenido expandible
            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(Modifier.height(12.dp))
                    Divider(color = Color.Gray.copy(alpha = 0.3f))
                    Spacer(Modifier.height(12.dp))

                    // 🔹 Miembros
                    val miembros = sala.otrosUsuariosSala ?: emptyList()
                    if (miembros.isNotEmpty()) {
                        Text(
                            text = "Miembros:",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        miembros.forEach { usuario ->
                            Text(
                                text = "• ${usuario.name} (${usuario.salaRole})",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    } else {
                        Text(
                            text = "Sin otros miembros aún",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontStyle = FontStyle.Italic,
                                color = Color.Gray
                            )
                        )
                    }

                    Spacer(Modifier.height(12.dp))
                    Divider(color = Color.Gray.copy(alpha = 0.3f))
                    Spacer(Modifier.height(12.dp))

                    // 🔹 Tiquets
                    val tiquets =salaMes?.tiquets// sala.tiquets ?: emptyList()

                    if (tiquets!!.isNotEmpty()) {
                        Text(
                            text = "Tiquets:",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 200.dp)
                        ) {
                            items(tiquets) { tiquet ->

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable {
                                             navController.navigate("EliminarTiquet/${tiquet.id}")
                                        },
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {

                                    Text(
                                        text = tiquet.description,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "${tiquet.amount}€",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = if (tiquet.esIngreso)
                                                Color(0xFF388E3C)
                                            else
                                                Color(0xFFD32F2F)
                                        )
                                    )
                                }
                                Divider(color = Color.Gray.copy(alpha = 0.1f))
                            }
                        }

                    } else {
                        Text(
                            text = "No hay tiquets aún",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontStyle = FontStyle.Italic,
                                color = Color.Gray
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 🔹 Acciones con íconos
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        var showMenu by remember { mutableStateOf(false) }

                        IconButton(onClick = { showMenu = true }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Seleccionar mes",
                                tint = Color(0xFF1976D2)
                            )
                        }

                        // 🔹 Menú desplegable (dentro del Card)
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Hace 2 meses") },
                                onClick = {
                                    showMenu = false
                                    viewModel.loadSalaMes(token, sala.id, -2)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Mes actual") },
                                onClick = {
                                    showMenu = false
                                    viewModel.loadSalaMes(token, sala.id, 0)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("En 4 meses") },
                                onClick = {
                                    showMenu = false
                                    viewModel.loadSalaMes(token, sala.id, 4)
                                }
                            )
                        }

                        IconButton(onClick = { onUpdateClick(sala) }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Editar sala",
                                tint = Color(0xFF388E3C)
                            )
                        }
                        IconButton(onClick = { onDeleteClick(sala) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar sala",
                                tint = Color(0xFFD32F2F)
                            )
                        }
                        IconButton(onClick = { onMesClick(sala) }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Ver mes / compartir",
                                tint = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

/*
@Composable
fun SalaItem(


    sala: Sala,
    viewModel: AuthViewModel,
    onClick: (Sala) -> Unit = {},
    onDateRangeClick: (Sala) -> Unit = {},
    onUpdateClick: (Sala) -> Unit = {},
    onDeleteClick: (Sala) -> Unit = {},
    onMesClick: (Sala) -> Unit = {},
) {

    val salaMes by viewModel.salaMes.observeAsState()





    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable { onClick(sala) }, // 🔹 Click general en la tarjeta
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // 🔹 Título de la sala
            Text(
                text = sala.name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 🔹 Datos financieros
            Text("Ingresos: ${sala.ingresos ?: 0.0} €")
            Text("Gastos: ${sala.gastos ?: 0.0} €")
            Text(
                text = "Balance: ${(sala.balance ?: 0.0)} €",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = if ((sala.balance ?: 0.0) >= 0) Color(0xFF2E7D32) else Color(0xFFC62828)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = Color.Gray.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(12.dp))

            // 🔹 Miembros
            val miembros = sala.otrosUsuariosSala ?: emptyList()
            if (miembros.isNotEmpty()) {
                Text(
                    text = "Miembros:",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                )
                miembros.forEach { usuario ->
                    Text(
                        text = "• ${usuario.name} (${usuario.salaRole})",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            } else {
                Text(
                    text = "Sin otros miembros aún",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontStyle = FontStyle.Italic,
                        color = Color.Gray
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = Color.Gray.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(12.dp))






            salaMes?.let { sala ->


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .heightIn(max = 400.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF0E6)),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {





                        Divider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = Color.Gray.copy(alpha = 0.3f)
                        )

                        // Total de tiquets
                        Text(
                            text = "Total Tiquets: ${sala.tiquets.size}",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Divider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = Color.Gray.copy(alpha = 0.3f)
                        )

                        // --- Lista de tiquets ---
                        if (sala.tiquets.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 200.dp)
                            ) {
                                items(sala.tiquets) { tiquet ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clickable {
                                             //   navController.navigate("EliminarTiquet/${tiquet.id}")
                                            },
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = tiquet.description,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Text(
                                            text = "${tiquet.amount}€",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = if (tiquet.esIngreso)
                                                    Color(0xFF388E3C)
                                                else
                                                    Color(0xFFD32F2F)
                                            )
                                        )
                                    }
                                    Divider(color = Color.Gray.copy(alpha = 0.1f))
                                }
                            }
                        } else {
                            Text(
                                text = "No hay tiquets aún",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontStyle = FontStyle.Italic,
                                    color = Color.Gray
                                )
                            )
                        }

                        Divider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = Color.Gray.copy(alpha = 0.3f)
                        )

                        // --- Balance ---
                    sala.balance?.let {
                        Text(
                            text = "Balance: ${sala.balance}€",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (it >= 0)
                                    Color(0xFF388E3C)
                                else
                                    Color(0xFFD32F2F)
                            )
                        )
                    }
                    }
                }












            /*

                        if (sala.tiquets.isNotEmpty()) {
                            Text(
                                text = "Tiquets (${sala.tiquets.size}):",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 200.dp)
                            ) {
                                items(sala.tiquets) { tiquet ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(tiquet.description)
                                        }
                                        Text(
                                            text = "${tiquet.amount} €",
                                            fontWeight = FontWeight.Bold,
                                            color = if (tiquet.esIngreso) Color(0xFF388E3C) else Color(0xFFD32F2F)
                                        )
                                    }
                                    Divider(color = Color.Gray.copy(alpha = 0.2f))
                                }
                            }
                        } else {
                            Text(
                                text = "No hay tiquets aún",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontStyle = FontStyle.Italic,
                                    color = Color.Gray
                                )
                            )
                        }
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally //centrar todo
                        ) {



                            Divider(
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = Color.Gray.copy(alpha = 0.3f)
                            )

                            Text(
                                text = "Total Tiquets: ${sala.tiquets.size}",
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Divider(
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = Color.Gray.copy(alpha = 0.3f)
                            )

                            // Lista de tiquets
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 200.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                items(sala.tiquets) { tiquet ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clickable {
                                                //llamamos a la pantalla para eliminar un tiquet
                                             //   navController.navigate("EliminarTiquet/${tiquet.id}")
                                            },
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = tiquet.description,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Text(
                                            text = "${tiquet.amount}€",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }
                                    Divider(color = Color.Gray.copy(alpha = 0.1f))
                                }
                            }

                            Divider(
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = Color.Gray.copy(alpha = 0.3f)
                            )

                            // tipo ticket
                            Text(
                                text = "Balance: ${sala.balance}€",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF388E3C)
                                )
                            )
                        }
            */





        Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Acciones con íconos (sin botón grande)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onDateRangeClick(sala) }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Seleccionar mes",
                        tint = Color(0xFF1976D2)
                    )
                }
                IconButton(onClick = { onUpdateClick(sala) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar sala",
                        tint = Color(0xFF388E3C)
                    )
                }
                IconButton(onClick = { onDeleteClick(sala) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar sala",
                        tint = Color(0xFFD32F2F)
                    )
                }
                IconButton(onClick = { onMesClick(sala) }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Ver mes / compartir",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}
*/
/*
@Composable
fun SalaItem(
    sala: Sala,

    onClick: (Sala) -> Unit = {},

    onDateRangeClick: (Sala) -> Unit = {},
    onUpdateClick: (Sala) -> Unit = {},              // navegar o editar
    onDeleteClick: (Sala) -> Unit = {},        // eliminar sala
    onMesClick: (Sala) -> Unit = {},           // ver sala mensual o compartir
) {
    if (sala == null) {
        // Opcional: UI para vacío
        Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text("Cargando sala...")
        }
        return
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(sala) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF0E6))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // 🔹 Nombre de la sala
            Text(
                text = sala.name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(4.dp))

            val rol = sala.roleSala?.takeIf { it.isNotBlank() }?.uppercase() ?: "SIN ROL"
            Text(
                text = "Rol: $rol",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
            )

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // 🔹 Miembros
            if (sala.otrosUsuariosSala.isNotEmpty()) {
                Text(
                    text = "Miembros:",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                )
                sala.otrosUsuariosSala.forEach { usuario ->
                    Text(
                        text = "• ${usuario.name} (${usuario.salaRole})",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            } else {
                Text(
                    text = "Sin otros miembros aún",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // 🔹 Tiquets
            if (!sala.tiquets.isNullOrEmpty()) {
                Text(
                    text = "Tiquets (${sala.tiquets.size}):",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 200.dp)
                ) {
                    items(sala.tiquets) { tiquet ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(tiquet.description)
                            Text(
                                "${tiquet.amount} €",
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Divider(color = Color.Gray.copy(alpha = 0.2f))
                    }
                }
            } else {
                Text(
                    text = "No hay tiquets aún",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // 🔹 Balance
            Text(
                text = "Balance: ${sala.balance ?: 0.0} €",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = if ((sala.balance ?: 0.0) >= 0)
                        Color(0xFF388E3C) else Color(0xFFC62828)
                )
            )

            // 🔹 Acciones
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onDeleteClick(sala) }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Mas datos sala",
                        tint = Color(0xFFB71C1C)
                    )
                }
                IconButton(onClick = { onDeleteClick(sala) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Update sala",
                        tint = Color(0xFFB71C1C)
                    )
                }
                IconButton(onClick = { onDeleteClick(sala) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar sala",
                        tint = Color(0xFFB71C1C)
                    )
                }

                IconButton(onClick = { onMesClick(sala) }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Ver mes / compartir",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}*/
