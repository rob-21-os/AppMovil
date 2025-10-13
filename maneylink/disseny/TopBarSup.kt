package com.example.maneylink.disseny


import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

/**
 * Composable que muestra una barra superior personalizada con título, menú de configuración y acciones de usuario.
 * Incluye dos menús desplegables: uno para opciones de la app y otro para el perfil del usuario.
 * El fondo es transparente y el texto se muestra en blanco para adaptarse a temas oscuros.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onConfigClick: (String) -> Unit,
    onUserClick: (String) -> Unit
) {
    TopAppBar(


        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        },
        navigationIcon = {
            MenuButton(
                icon = Icons.Default.Menu,
                contentDescription = "Menú Configuración",
                menuItems = listOf(
                    "Sobre la app" to { onConfigClick("about") },
                    "Ajustes" to { onConfigClick("settings") }
                )
            )
        },
        actions = {
            MenuButton(
                icon = Icons.Default.Person,
                contentDescription = "Perfil Usuario",
                menuItems = listOf(
                    "Perfil" to { onUserClick("profile") },
                    "Cerrar sesión" to { onUserClick("logout") }



                ),

            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent, // 👈 Fondo transparente
            titleContentColor = Color.White
        )
    )
}
