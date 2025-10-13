package com.example.maneylink.dadesServidor


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * ViewModel responsable de gestionar la autenticación del usuario.
 * Coordina operaciones de login, registro, actualización de perfil y logout.
 * Utiliza corrutinas y StateFlow para mantener el estado reactivo del usuario.
 *
 */
class AuthViewModel : ViewModel() {

    private val _mensaje = MutableLiveData<String?>(null)
    val mensaje: LiveData<String?> = _mensaje

    private val _loginResponse = MutableLiveData<LoginResponse>()

    val loginResponse: LiveData<LoginResponse> = _loginResponse


    private val _token = mutableStateOf<String?>(null)
    val token: State<String?> = _token

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val repository = UserRepository()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password) //devuelve LoginResponse

                _currentUser.value = response.user
                _token.value = response.token


                _mensaje.value = response.message

                Log.d("AuthViewModel", "Token: ${response.token}")
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error al iniciar sesión: ${e.message}")
                _currentUser.value = null
                _token.value = null
                _mensaje.value = null
            }
        }
    }

    private val _signupState = MutableLiveData<String>()
    val signupState: LiveData<String> get() = _signupState

    fun registerUser(signupRequest: SignupRequest) {
        viewModelScope.launch {
            try {
                val response = repository.registerUser(signupRequest)//api.addUser(signupRequest)


                _signupState.value = response.message


            } catch (e: Exception) {
                _signupState.value = "Excepción: ${e.message}"
            }
        }
    }


    fun updateProfile(userId: String?, updateRequest: UpdateUserProfileRequest) {
        viewModelScope.launch {
            try {
                val updated = repository.updateProfile(userId, updateRequest)
                _currentUser.value = updated
            } catch (e: Exception) {
                // mostrar un mensaje de error
                _currentUser.value = null
            }
        }
    }

        fun logout(token: String?) {
            viewModelScope.launch {
                try {
                    repository.logout(token)
                    _currentUser.value = null

                } catch (e: Exception) {
                    _currentUser.value = null
                }
            }
        }
    }

