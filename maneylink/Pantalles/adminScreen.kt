package com.example.maneylink.Pantalles

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.maneylink.disseny.GradientBackground
import com.example.maneylink.dadesServidor.User
import com.example.maneylink.disseny.BotonAccion
import com.example.maneylink.disseny.CampoTexto
import com.example.maneylink.disseny.TopBar

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
fun AdminScreen(user: User, viewModel: AuthViewModel, navController: NavHostController) {
    val currentUser by viewModel.currentUser.collectAsState()
    val context = LocalContext.current
    val token = viewModel.token.value


    var username by remember { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }


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
                      //  "profile" -> navController.navigate("profile")
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


        GradientBackground(colors = listOf(Color(0xFF00C9FF), Color(0xFF92FE9D))) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Configuració ADMIN",
                    fontSize = 30.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Introdueix les dades que vols modificar",
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                //CampoTexto("Nom", username) { username = it }
                //CampoTexto("Email", email) { email = it }
                //CampoTexto("Password", password) { password = it }

                BotonAccion("Guardar") {
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "Completa tots els camps", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        viewModel.login(email, password)
                        Toast.makeText(context, "Espere", Toast.LENGTH_SHORT).show()
                    }
                    navController.popBackStack()
                }

                BotonAccion("Enrere") {
                    navController.popBackStack()
                }

                BotonAccion("Crear Usuari") {

                    navController.navigate("adminAdd")

                }



            }
        }
    }
}