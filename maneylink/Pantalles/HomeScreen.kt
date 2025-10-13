package com.example.maneylink.Pantalles

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.maneylink.dadesServidor.AuthViewModel
import com.example.maneylink.dadesUtils.BarChart
import com.example.maneylink.disseny.GradientBackground
import com.example.maneylink.dadesServidor.User
import com.example.maneylink.dadesUtils.diaDelMes
import com.example.maneylink.disseny.TopBar
import kotlin.system.exitProcess



/**
 * Pantalla principal de la aplicación MoneyLink.
 *
 * Muestra un resumen visual con selector de fecha y gráfico de barras.
 * Incluye una barra superior con menús de configuración y usuario.
 * Ofrece botones de navegación para administración (solo si el usuario es ADMIN),
 * edición de perfil, cierre de sesión y salida de la app.
 *
 * @param user Usuario autenticado que accede a la pantalla.
 * @param viewModel ViewModel que gestiona la lógica de autenticación.
 * @param navController Controlador de navegación para cambiar entre pantallas.
 */

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(user: User, viewModel: AuthViewModel, navController: NavHostController) {
        //resivir el token
    val token = viewModel.token.value


    //franja superior
    Scaffold(
        topBar = {
            TopBar(
                title = "MoneyLink  ",
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
                            Log.d("Logout", "Token enviado: $token")

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
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {


                Column(modifier = Modifier.fillMaxWidth()) {
                    //println(user.username+user.role)

                    diaDelMes()
                    BarChart(data = listOf(50, 100, 150, 50, 100, 150, 50, 100, 150))
                }

                Spacer(modifier = Modifier.weight(1f)) // empuja los botones al fondo

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {



                    if (user.role == "ADMIN") {
                        Button(onClick = {

                            navController.navigate("admin")


                        }, modifier = Modifier.weight(1f)) {
                            Text("ADMIN")
                        }
                    }


                    Button(onClick = {
                        navController.navigate("budget")

                    }, modifier = Modifier.weight(1f)) {
                        Text("datos finaciero")
                    }


                }
            }
        }
    }
}