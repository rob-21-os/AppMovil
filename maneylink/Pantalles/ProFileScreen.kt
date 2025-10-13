package com.example.maneylink.Pantalles

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.maneylink.dadesServidor.AuthViewModel
import com.example.maneylink.dadesServidor.UpdateUserProfileRequest
import com.example.maneylink.disseny.GradientBackground
import com.example.maneylink.dadesServidor.UserPerfile
import com.example.maneylink.dadesServidor.UserPreferences
import com.example.maneylink.disseny.BotonAccion
import com.example.maneylink.disseny.CampoTexto
import com.example.maneylink.disseny.TopBar



/**
 * Pantalla de perfil de usuario en la aplicación MoneyLink.
 *
 * Permite al usuario modificar sus datos personales como nombre, email y contraseña.
 * Incluye una barra superior con opciones de configuración y navegación.
 * Ofrece botones para guardar cambios, volver atrás o crear un nuevo usuario.
 *
 * @param user Usuario autenticado que accede a la pantalla.
 * @param viewModel ViewModel que gestiona la lógica de autenticación y actualización de perfil.
 * @param navController Controlador de navegación para gestionar transiciones entre pantallas.
 */

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun ProfileScreen(user: UserPerfile, viewModel: AuthViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val token = viewModel.token.value

    var name by rememberSaveable { mutableStateOf(user.name) }
    var email by rememberSaveable { mutableStateOf(user.email) }
    var role by rememberSaveable { mutableStateOf(user.role) }
    var profileImageUrl by rememberSaveable { mutableStateOf(user.profileImageUrl ?: "") }
    var isActive by rememberSaveable { mutableStateOf(user.isActive) }
    var language by rememberSaveable { mutableStateOf(user.preferences.language) }
    var receiveNotifications by rememberSaveable { mutableStateOf(user.preferences.receiveNotifications) }
    var darkModeEnabled by rememberSaveable { mutableStateOf(user.preferences.darkModeEnabled) }

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
    ) {
        GradientBackground(colors = listOf(Color(0xFF00C9FF), Color(0xFF92FE9D))) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Configuració",
                    fontSize = 30.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Introdueix les dades que vols modificar",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(12.dp))

                CampoTexto("Nom", name) { name = it }
                CampoTexto("Email", email) { email = it }
                CampoTexto("Rol", role) { role = it }
                CampoTexto("Foto de perfil (URL)", profileImageUrl) { profileImageUrl = it }

                Switch(
                    checked = isActive,
                    onCheckedChange = { isActive = it },
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(text = if (isActive) "Usuari actiu" else "Usuari inactiu")

                CampoTexto("Idioma", language) { language = it }
                Switch(
                    checked = receiveNotifications,
                    onCheckedChange = { receiveNotifications = it },
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(text = "Notificacions: ${if (receiveNotifications) "Activades" else "Desactivades"}")

                Switch(
                    checked = darkModeEnabled,
                    onCheckedChange = { darkModeEnabled = it },
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(text = "Mode fosc: ${if (darkModeEnabled) "Sí" else "No"}")

                Spacer(Modifier.height(16.dp))

                BotonAccion("Guardar") {
                    if (email.isBlank() || name.isBlank()) {
                        Toast.makeText(context, "Completa tots els camps", Toast.LENGTH_SHORT).show()
                    } else {
                        val updatedPreferences = UserPreferences(
                            language = language,
                            receiveNotifications = receiveNotifications,
                            darkModeEnabled = darkModeEnabled
                        )

                        val updateRequest = UpdateUserProfileRequest(
                            name = name,
                            email = email,
                            role = role,
                            isActive = isActive,
                            profileImageUrl = profileImageUrl,
                            preferences = updatedPreferences
                        )

                            viewModel.updateProfile(token, updateRequest)

                            Toast.makeText(context, "Perfil actualitzat", Toast.LENGTH_SHORT).show()
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


