package com.example.maneylink.Pantalles

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.maneylink.dadesServidor.AuthViewModel
import com.example.maneylink.dadesServidor.SignupRequest
import com.example.maneylink.disseny.GradientBackground
import com.example.maneylink.dadesServidor.User
import com.example.maneylink.disseny.BotonAccion
import com.example.maneylink.disseny.CampoTexto
import com.example.maneylink.disseny.CamposDesplegable
import com.example.maneylink.disseny.TopBar
import com.example.maneylink.disseny.isValidEmail
import com.example.maneylink.disseny.isValidPassword

/**
 * Pantalla de administración para modificar datos de usuario.
 *
 * Muestra una interfaz con opciones de navegación, fondo degradado y botones de acción.
 * Permite al administrador iniciar sesión con nuevos datos o navegar hacia atrás.
 * Incluye una barra superior con menús de configuración y usuario.
 *
 * @param user Usuario actual autenticado.
 * @param viewModel ViewModel que gestiona la lógica de autenticación.
 * @param navController Controlador de navegación para gestionar transiciones entre pantallas.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AdminAddUserScreen(user: User, viewModel: AuthViewModel, navController: NavHostController) {

    val context = LocalContext.current
    //resivir el token
    val token = viewModel.token.value

    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

        val opcionesROl = listOf("USER", "ADMIN")

        var rol by rememberSaveable { mutableStateOf(opcionesROl.first()) }






    //franja superior
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
                        //"profile" -> navController.navigate("profile")
                        "logout" -> {
                            viewModel.logout(token)//se envia el toke al servidor para el logout
                            navController.navigate("login") {
                                popUpTo("home") { inclusive = true }
                            }
                        }
                    }
                }
            )
        }
    ) { innerPadding: PaddingValues ->


        //clase difuminado pantalla
        GradientBackground(
            colors = listOf(Color(0xFF00C9FF), Color(0xFF92FE9D)) // azul → verde lima
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "afegir usuari",
                    fontSize = 50.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Introdueix les teves dades",
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(24.dp))




                CampoTexto("Nom d'usuari", name) { name = it }
                CampoTexto("email d'usuari", email) { email = it }
                CampoTexto("Contrasenya", password) {
                    password = it
                }
                //CamposDesplegable("Nombre del Rol", opcionesROl, rol) { rol = it }



                BotonAccion("Guardar") {
                    when {
                        name.isBlank() -> {
                            Toast.makeText(context, "El nom és obligatori", Toast.LENGTH_SHORT).show()
                        }
                        email.isBlank() -> {
                            Toast.makeText(context, "El correu electrònic és obligatori", Toast.LENGTH_SHORT).show()
                        }
                        !isValidEmail(email) -> {
                            Toast.makeText(context, "El correu no és vàlid", Toast.LENGTH_SHORT).show()
                        }
                        password.isBlank() -> {
                            Toast.makeText(context, "La contrasenya és obligatòria", Toast.LENGTH_SHORT).show()
                        }
                        !isValidPassword(password) -> {
                            Toast.makeText(context, "La contrasenya ha de tenir una majúscula, un número i un caràcter especial", Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            val request = SignupRequest(name, email, password)
                            viewModel.registerUser(request)
                            navController.popBackStack()
                            Toast.makeText(context, "Espere...", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                BotonAccion("enrere") {
                    navController.popBackStack()
                }
            }
        }
    }
}



