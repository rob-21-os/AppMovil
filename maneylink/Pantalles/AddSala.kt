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
import androidx.compose.runtime.getValue
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
 * Pantalla de **creación de una nueva sala** dentro de la aplicación **MoneyLink**.
 *
 * Este composable permite a los usuarios crear una nueva sala (grupo o espacio compartido)
 * para gestionar gastos, ingresos o presupuestos familiares.
 *
 * ### Funcionalidades principales:
 * - Muestra un formulario con campos editables para el nombre y descripción de la sala.
 * - Permite enviar los datos al servidor mediante `viewModel.createSala()`.
 * - Muestra mensajes de confirmación o error a través de `Toast`.
 * - Incluye navegación de retorno al menú anterior.
 *
 * ### Flujo general:
 * 1. El usuario accede a la pantalla desde el menú o desde otra vista de gestión.
 * 2. Rellena los campos “Nombre” y “Descripción”.
 * 3. Presiona **Crear Sala** → se genera un objeto [SalaRequest] y se envía al servidor.
 * 4. Si la creación es exitosa, se regresa automáticamente a la pantalla anterior.
 * 5. Si ocurre un error, se muestra un mensaje mediante `viewModel.uiMessage`.
 *
 * ### Parámetros:
 * @param user Objeto [User] que contiene la información del usuario autenticado,
 *             incluyendo sus salas y datos de sesión.
 * @param viewModel Instancia de [AuthViewModel] que gestiona la comunicación con el backend,
 *                  incluyendo la creación de salas y manejo de mensajes UI.
 * @param navController Controlador de navegación [NavHostController] utilizado
 *                      para moverse entre pantallas dentro de la aplicación.
 *
 * ### Componentes visuales:
 * - **TopBar:** Barra superior con opciones de usuario (perfil, logout).
 * - **GradientBackground:** Fondo degradado consistente con la estética de MoneyLink.
 * - **OutlinedTextField:** Campos de texto para ingresar el nombre y la descripción.
 * - **Button:** Acción principal para confirmar la creación de la sala.
 * - **BotonAccion:** Botón personalizado de navegación hacia atrás.
 *
 * ### Acciones del usuario:
 * - **Crear Sala:** Envía los datos introducidos al servidor usando `viewModel.createSala()`.
 * - **Volver:** Regresa a la pantalla anterior sin guardar información.
 * - **Logout:** Cierra la sesión y redirige al login desde el `TopBar`.
 *
 * ### Validaciones y comportamiento:
 * - Los campos `nombre` y `descripcion` se inicializan vacíos.
 * - Si alguno de los campos está vacío, el ViewModel o la API deberían manejar la validación.
 * - Los mensajes del servidor se muestran mediante el flujo `uiMessage` del ViewModel.
 *
 * @see AuthViewModel.createSala
 * @see SalaRequest
 * @see TopBar
 * @see GradientBackground
 * @see BotonAccion
 *
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddSala(
    user: User,
    viewModel: AuthViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    val token = viewModel.token.value


    var nombre by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBar(
                title = "Añadir Sala",
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
                        text = "CREAR SALA",
                        fontSize = 20.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }

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
                                name = nombre
                            )
                            viewModel.createSala(token, request)


                            navController.popBackStack()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Crear Sala")
                    }
                }

                item {
                    BotonAccion("Volver") { navController.popBackStack() }
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
