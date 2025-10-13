package com.example.maneylink.Pantalles

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.maneylink.dadesServidor.AuthViewModel
import com.example.maneylink.dadesServidor.SignupRequest
import com.example.maneylink.disseny.BotonAccion
import com.example.maneylink.disseny.CampoTexto
import com.example.maneylink.disseny.GradientBackground
import com.example.maneylink.disseny.isValidEmail
import com.example.maneylink.disseny.isValidPassword
import kotlin.system.exitProcess

/**
 * Pantalla de inicio de sesión para la aplicación MoneyLink.
 *
 * Muestra un formulario con campos para email y contraseña, junto con botones para iniciar sesión,
 * salir de la app o navegar a la pantalla de registro.
 * Valida los campos antes de enviar los datos al ViewModel.
 * Al detectar un usuario válido, ejecuta la función `onSuccess` para continuar con el flujo de navegación.
 *
 * @param viewModel ViewModel que gestiona la lógica de autenticación.
 * @param navController Controlador de navegación para cambiar entre pantallas.
 * @param onSuccess Acción que se ejecuta cuando el login es exitoso.
 */
@Composable
fun LoginScreen( viewModel: AuthViewModel, navController: NavHostController, onSuccess: () -> Unit) {//LoginResponse

    val MensajeSever  by viewModel.mensaje.observeAsState()


    val context = LocalContext.current

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    GradientBackground(colors = listOf(Color(0xFF00C9FF), Color(0xFF92FE9D))) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "MoneyLink",
                fontSize = 50.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Introdueix les teves dades per iniciar sessió",
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            CampoTexto("Email d'usuari", email) { email = it }
            CampoTexto("Contrasenya", password) { password = it }

            BotonAccion("Login") {
                when {

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
                        viewModel.login(email, password)

                        Toast.makeText(context, "Espere...", Toast.LENGTH_SHORT).show()
                    }
                }
            }


            //mensaje del servidor a mostrar por pantalla
            LaunchedEffect(MensajeSever) {
                    MensajeSever?.let {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    }

            }




                BotonAccion("EXIT") {
                    exitProcess(0)
                }


            BotonAccion("Crear usuari") {
                navController.navigate("newUser")
            }
        }



    }
}
