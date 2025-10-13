package com.example.maneylink.Pantalles

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.maneylink.dadesServidor.AuthViewModel
import com.example.maneylink.dadesServidor.Gasto
import com.example.maneylink.dadesServidor.User
import com.example.maneylink.dadesUtils.generarPDF
import com.example.maneylink.disseny.BotonAccion
import com.example.maneylink.disseny.CampoTexto
import com.example.maneylink.disseny.CamposDesplegable
import com.example.maneylink.disseny.GradientBackground
import com.example.maneylink.disseny.TopBar
import java.io.File
import java.io.FileOutputStream

/**
 * Composable que representa la pantalla de edición de datos financieros del usuario.
 *
 * @param user Objeto que contiene la información actual del usuario (nombre y email).
 * @param viewModel ViewModel que gestiona la autenticación y acciones del usuario.
 * @param navController Controlador de navegación para cambiar entre pantallas.
 *
 * Esta pantalla permite al usuario visualizar y editar su nombre y correo electrónico.
 * Incluye opciones para eliminar el usuario (simulada), volver atrás, y acceder a perfil o cerrar sesión.
 * También muestra una franja superior con opciones de configuración.
 */


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Presupuesto(
    user: User,
    viewModel: AuthViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    val token = viewModel.token.value


    // Lista de gastos
    var gastos by rememberSaveable {
        mutableStateOf(
            mutableListOf(
                Gasto("Alquiler", 800.0, "Vivienda"),
                Gasto("Comida", 300.0, "Alimentación"),
                Gasto("Transporte", 100.0, "Transporte")
            )
        )
    }

    // Campos para nuevo gasto
   // var nuevoNombre by rememberSaveable { mutableStateOf("") }
    val opcionesGasto = listOf("Alimentación", "Transporte", "Ocio", "Educación", "Salud")

    var nuevoNombre by rememberSaveable { mutableStateOf(opcionesGasto.first()) }
    var nuevaCantidad by rememberSaveable { mutableStateOf("") }
    var nuevaCategoria by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBar(
                title = "MoneyLink",
                onConfigClick = { option ->
                    when (option) {
                        "about" -> println("Mostrar información de la app")
                        "settings" -> println("Abrir ajustes")
                    }
                },
                onUserClick = { option ->
                    when (option) {
                        "profile" -> navController.navigate("profile")
                        "logout" -> {
                            viewModel.logout(token)
                            navController.navigate("login") {
                                popUpTo("home") { inclusive = true }
                            }
                        }
                    }
                }
            )
        }
    ) { innerPadding: PaddingValues ->
        GradientBackground(colors = listOf(Color(0xFF00C9FF), Color(0xFF92FE9D))) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "GESTIÓN DE GASTOS FAMILIARES",
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )


                // Mostrar lista de gastos
                Text(
                    text = "Gastos registrados:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )

                gastos.forEachIndexed { index, gasto ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(gasto.nombre, fontWeight = FontWeight.Bold)
                                Text(gasto.categoria)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "%.2f €".format(gasto.cantidad),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                IconButton(onClick = {
                                    // Confirmación antes de eliminar
                                    gastos = gastos.toMutableList().also { it.removeAt(index) }
                                    Toast.makeText(
                                        context,
                                        "Gasto eliminado: ${gasto.nombre}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar gasto",
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }

                // Formulario para nuevo gasto
                Text(
                    text = "Añadir nuevo gasto",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp)
                )


                CamposDesplegable("Nombre del gasto", opcionesGasto, nuevoNombre) { nuevoNombre = it }
                //CampoTexto("Nombre del gasto", nuevoNombre) { nuevoNombre = it }
                CampoTexto("Cantidad (€)", nuevaCantidad) { nuevaCantidad = it }
                CampoTexto("Categoría", nuevaCategoria) { nuevaCategoria = it }

                BotonAccion("Agregar gasto") {
                    if (nuevoNombre.isBlank() || nuevaCantidad.isBlank() || nuevaCategoria.isBlank()) {
                        Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                    } else {
                        val cantidad = nuevaCantidad.toDoubleOrNull()
                        if (cantidad != null) {
                            gastos = gastos.toMutableList().apply {
                                add(Gasto(nuevoNombre, cantidad, nuevaCategoria))
                            }
                            nuevoNombre = ""
                            nuevaCantidad = ""
                            nuevaCategoria = ""
                            Toast.makeText(context, "Gasto añadido", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Cantidad no válida", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                // Botón para generar el PDF
                BotonAccion("Generar PDF") {
                    val pdfFile = generarPDF(context, user, gastos)
                    Toast.makeText(context, "PDF guardado en: ${pdfFile.absolutePath}", Toast.LENGTH_LONG).show()
                }

                BotonAccion("Enrere") {
                    navController.popBackStack()
                }
            }
        }
    }
}



