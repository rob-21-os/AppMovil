package com.example.moneylink.Pantalles.Salas


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moneylink.dadesServidor.AuthViewModel
import com.example.moneylink.dataClass.SalaRequest
import com.example.moneylink.dataClass.User
import com.example.moneylink.disseny.BotonAccion
import com.example.moneylink.disseny.GradientBackground
import com.example.moneylink.disseny.TopBar
import kotlinx.coroutines.flow.collectLatest
import kotlin.system.exitProcess

/**
 * Pantalla de **edición de una sala** dentro de la aplicación **MoneyLink**.
 *
 * Este composable permite al usuario modificar la información de una sala existente,
 * como su nombre (y opcionalmente descripción), asociada a su cuenta o grupo familiar.
 *
 * ### Funcionalidades principales:
 * - Carga los datos actuales de la sala mediante `viewModel.loadSalaMes()`.
 * - Muestra un formulario editable para actualizar el nombre de la sala.
 * - Permite guardar los cambios en el servidor con `viewModel.updateSala()`.
 * - Muestra notificaciones de éxito o error usando `Toast`.
 * - Incluye navegación hacia atrás (`popBackStack()`) o hacia el perfil de usuario.
 *
 * ### Flujo general:
 * 1. Al iniciarse, se ejecuta un `LaunchedEffect` que carga los datos de la sala desde el backend.
 * 2. Cuando la sala se obtiene correctamente, los campos se rellenan automáticamente.
 * 3. El usuario puede modificar el nombre y presionar **Guardar cambios**.
 * 4. Se envía un objeto [SalaRequest] al servidor con la información actualizada.
 * 5. Se muestra un mensaje de estado (`uiMessage`) y se regresa a la pantalla anterior.
 *
 * ### Parámetros:
 * @param user Objeto [User] con la información del usuario autenticado.
 * @param viewModel Instancia de [AuthViewModel] que maneja la lógica de negocio
 *                  y comunicación con el backend (actualización, carga y mensajes de UI).
 * @param navController Controlador de navegación [NavHostController] usado para moverse entre pantallas.
 * @param salaId Identificador único de la sala que se desea editar.
 *
 * ### Componentes relacionados:
 * - **TopBar:** Barra superior de la aplicación con acciones de perfil y cierre de sesión.
 * - **GradientBackground:** Fondo con degradado visual usado en todas las pantallas principales.
 * - **OutlinedTextField:** Campo de texto material design para modificar el nombre de la sala.
 * - **BotonAccion:** Botón personalizado para confirmar o regresar.
 * - **AuthViewModel.updateSala:** Método responsable de enviar la actualización al servidor.
 *
 * ### Acciones del usuario:
 * - **Guardar cambios:** Actualiza la información de la sala en el servidor y navega hacia atrás.
 * - **Volver:** Regresa sin guardar modificaciones.
 * - **Logout:** Cierra la sesión y redirige al login desde la barra superior.
 *
 * ### Validaciones y comportamiento:
 * - Si la sala no se encuentra (nula), se muestra un mensaje de error con opción para volver.
 * - Si el campo `nombre` está vacío, se recomienda manejar la validación en el ViewModel.
 * - Los mensajes del sistema o del servidor se reciben desde `viewModel.uiMessage`.
 *
 * @see AuthViewModel.updateSala
 * @see SalaRequest
 * @see TopBar
 * @see GradientBackground
 * @see BotonAccion
 *
 */

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UpDataSala(
    user: User,
    viewModel: AuthViewModel,
    navController: NavHostController,
    salaId: Int
) {
    val context = LocalContext.current
    val token = viewModel.token.value
    val salaMes by viewModel.salaMes.observeAsState()

    var nombre by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(salaId) {
        viewModel.loadSalaMes(token, salaId,0)

    }

    LaunchedEffect(salaMes) {
        salaMes.let {
            nombre = it?.name ?: "sin nombre"
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = "Editar Sala",
                onConfigClick = { },
                onMesSeleccionado = {},
                onUserClick = { option ->
                    when (option) {
                        "Exit" -> {
                            viewModel.logout(token)//se envia el toke al servidor para el logout

                            exitProcess(0)
                        }
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
    ) { padding ->
        GradientBackground(colors = listOf(Color(0xFF00C9FF), Color(0xFF92FE9D))) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = "EDITAR SALA",
                        fontSize = 20.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (salaMes == null) {
                    item {
                        Text("No se encontró la sala con ID $salaId")
                        BotonAccion("Volver") { navController.popBackStack() }
                    }
                } else {
                    item {
                        OutlinedTextField(
                            value = nombre,
                            onValueChange = { nombre = it },
                            label = { Text("Nombre de la sala") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }


                    item {
                        Button(
                            onClick = {
                                val request = SalaRequest(
                                    name = nombre,
                                   // description = descripcion
                                )
                                viewModel.updateSala(token, salaId, request)

                                navController.popBackStack()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Guardar cambios")
                        }
                    }

                    item {
                        BotonAccion("Volver") { navController.popBackStack() }
                    }

                }

            }
            LaunchedEffect(Unit) {
                viewModel.uiMessage.collectLatest { message ->
                    message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    }

                }

            }
        }
    }
}
