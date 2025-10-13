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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.example.maneylink.dadesServidor.User
import com.example.maneylink.disseny.BotonAccion
import com.example.maneylink.disseny.CampoTexto
import com.example.maneylink.disseny.GradientBackground
import com.example.maneylink.disseny.TopBar

/**
 * Pantalla para eliminar un usuario del sistema.
 *
 * Muestra un formulario con campos para nombre, email, contraseña y rol.
 * Valida que todos los campos estén completos antes de ejecutar la acción.
 * Actualmente llama a `singup`, pero debería conectarse con una función `deleteUser` en el ViewModel.
 *
 * @param user Usuario actual autenticado.
 * @param viewModel ViewModel que gestiona la lógica de autenticación.
 * @param navController Controlador de navegación para gestionar transiciones entre pantallas.
 */

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DeleteUser(user: User, viewModel: AuthViewModel, navController: NavHostController) {
    val context = LocalContext.current

    val token = viewModel.token.value

    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var rol by rememberSaveable { mutableStateOf("") }


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
                        "profile" -> navController.navigate("profile")
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
                    text = "Eliminar usuari",
                    fontSize = 50.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Introdueix les dades",
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                CampoTexto("Nom d'usuari", name) { name = it }
                CampoTexto("Email d'usuari", email) { email = it }
                CampoTexto("Contrasenya", password) { password = it }
                CampoTexto("Escribe rol", rol) { rol = it }

                BotonAccion("Eliminar usuari") {
                    if (name.isBlank() || email.isBlank() || password.isBlank() || rol.isBlank()) {
                        Toast.makeText(context, "Completa tots els camps", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        //  viewModel.singup(name, email, password) // ¿Debería ser deleteUser?
                        Toast.makeText(context, "Espere", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                }

                BotonAccion("Enrere") {
                    navController.popBackStack()
                }
            }
        }
    }
}