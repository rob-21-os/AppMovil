package com.example.maneylink


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import androidx . navigation . compose . rememberNavController
import com.example.maneylink.Pantalles.AddUserScreen
import com.example.maneylink.Pantalles.AdminAddUserScreen
import com.example.maneylink.Pantalles.AdminScreen
import com.example.maneylink.Pantalles.DeleteUser
import com.example.maneylink.Pantalles.HomeScreen
import com.example.maneylink.Pantalles.LoginScreen
import com.example.maneylink.Pantalles.Presupuesto
import com.example.maneylink.Pantalles.ProfileScreen
import com.example.maneylink.dadesServidor.AuthViewModel

import com.example.maneylink.extensiones.toUserProfile

/**
  * Proyecto de gestión de usuarios con autenticación.
  * Incluye pantallas para login, perfil, administración, y operaciones CRUD.
  * Utiliza Jetpack Compose, Navigation y ViewModel para arquitectura moderna.
 *
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel: AuthViewModel = viewModel()
            val user by viewModel.currentUser.collectAsState()


            NavHost(
                navController = navController,
                startDestination =if (user == null) "login" else "home"
            ) {
                composable("login") {
                    LoginScreen(viewModel, navController) {//LoginResponse
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
                composable("newUser") {

                    AddUserScreen(viewModel, navController)

                }
                composable("home") {
                    user?.let {
                        HomeScreen(it, viewModel, navController)
                    }
                }
                composable("admin") {
                    user?.let {
                        AdminScreen(it, viewModel, navController)
                    }
                }
                composable("adminAdd") {
                    user?.let {
                        AdminAddUserScreen(it, viewModel, navController)
                    }
                }
                composable("ElininarUsusari") {
                    user?.let {
                        DeleteUser(it, viewModel, navController)
                    }
                }
                composable("profile") {
                    user?.let {
                        ProfileScreen(it.toUserProfile(), viewModel, navController)
                    }
                }
                composable("budget") {
                    user?.let {
                        Presupuesto(it, viewModel, navController)
                    }
                }

            }
        }
    }
}


