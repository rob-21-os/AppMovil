package com.example.maneylink.dadesServidor

import android.util.Log


/**
 * Repositorio que gestiona las operaciones de autenticación y perfil de usuario.
 * Interactúa con la API remota mediante Retrofit para login, registro, actualización y logout.
 */

class UserRepository {
    private val api = ApiClient.userApi
    /**
     * Realiza el inicio de sesión del usuario con correo y contraseña.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return Objeto [LoginResponse] con los datos del usuario y el token.
     * @throws Exception Si la respuesta es vacía o hay un error de red.
     */


    suspend fun login(email: String, password: String): LoginResponse {
        val response = api.login(email, password)
        if (response.isSuccessful) {
            val body = response.body()

            if (body != null) {
                return body  // devolver el LoginResponse completo
            } else {
                throw Exception("Respuesta vacía del servidor")
            }
        } else {
            throw Exception("Error de red: ${response.code()}")
        }
    }
    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param request Objeto [SignupRequest] con los datos del nuevo usuario.
     * @return Objeto [LoginResponse] con los datos del usuario registrado y el token.
     * @throws Exception Si la respuesta es vacía o hay un error HTTP.
     */



    suspend fun registerUser(request: SignupRequest): LoginResponse {
        val response = api.addUser(request)  // Esto devuelve Response<LoginResponse>

        if (response.isSuccessful) {
            val body = response.body()
            Log.d("RegisterUser", "Body: $body")


            if (body != null) {
                return body
            } else {
                throw Exception(body?.message ?: "Error desconocido del servidor")
            }
        } else {
            val errorMsg = response.errorBody()?.string() ?: "Error HTTPs ${response.code()}"
            throw Exception(errorMsg)
        }
    }
    /**
     * Actualiza el perfil del usuario con los datos proporcionados.
     *
     * @param id Identificador del usuario a actualizar.
     * @param request Objeto [UpdateUserProfileRequest] con los nuevos datos del perfil.
     * @return Objeto [User] actualizado.
     */



    suspend fun updateProfile(id: String?, request: UpdateUserProfileRequest): User {
        return api.updateProfile(id.toString(), request)
    }
    /**
     * Cierra la sesión del usuario enviando el token al servidor.
     *
     * @param token Token de autenticación del usuario.
     * @throws Exception Si el token es inválido o hay un error en la respuesta del servidor.
     */


    suspend fun logout(token: String?) {
        try {
            if (token.isNullOrBlank()) {
                throw Exception("Token no válido o vacío")
            }

            val response = api.logout("Bearer $token")


            if (response.isSuccessful) {
                // Logout exitoso
                Log.d("Logout", "Sesión cerrada correctamente")
            } else {
                val errorBody = response.errorBody()?.string()
                throw Exception("Error al cerrar sesión: ${errorBody ?: "Código ${response.code()}"}")
            }
        } catch (e: Exception) {
            Log.e("Logout", "Error: ${e.message}")
            // Puedes mostrar un Toast o manejar el error como prefieras
        }
    }
}