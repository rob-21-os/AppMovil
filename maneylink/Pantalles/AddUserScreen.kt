package com.example.maneylink.Pantalles

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.maneylink.dadesServidor.AuthViewModel
import com.example.maneylink.disseny.BotonAccion
import com.example.maneylink.disseny.CampoTexto
import com.example.maneylink.disseny.GradientBackground
import com.example.maneylink.disseny.TopBar
import androidx.compose.foundation.layout.PaddingValues
import com.example.maneylink.dadesServidor.SignupRequest
import com.example.maneylink.disseny.CamposDesplegable
import com.example.maneylink.disseny.isValidEmail
import com.example.maneylink.disseny.isValidPassword

/**
 * Pantalla para registrar un nuevo usuario en la aplicación.
 *
 * Muestra un formulario con campos para nombre, email, contraseña y rol.
 * Incluye una barra superior con opciones de configuración y usuario.
 * Valida que todos los campos estén completos antes de enviar los datos al ViewModel.
 *
 * @param viewModel ViewModel responsable de la lógica de autenticación y registro.
 * @param navController Controlador de navegación para gestionar transiciones entre pantallas.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddUserScreen(viewModel: AuthViewModel, navController: NavHostController) {

    val context = LocalContext.current
    //resivir el token
    val token = viewModel.token.value

    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
/*
    val opcionesROl = listOf("USER", "ADMIN")

    var rol by rememberSaveable { mutableStateOf(opcionesROl.first()) }

    CamposDesplegable("Nombre del Rol", opcionesROl, rol) { rol = it }
*/




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


