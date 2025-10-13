package com.example.maneylink.dadesServidor

/**
 * Datos necesarios para iniciar sesión.
 * Se envían al servidor en el cuerpo de la petición POST a /login.
 */

data class LoginRequest(

    val email: String,
    val password: String
)
/**
 * Modelo que representa al usuario autenticado.
 * Este objeto se recibe en el campo "data" de la respuesta del servidor.
 */

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    val salas: List<Sala>
)

/**
 * Representa una sala o grupo al que pertenece el usuario.
 * Cada sala tiene un nombre, un identificador y un rol específico dentro de ella.
 */


data class Sala(
    val sala_id: Int,
    val sala_name: String,
    val role_name: String
)


/**
 * Modelo para la respuesta completa del servidor al iniciar sesión.
 * Incluye estado, mensaje, identificador y los datos del usuario.
 */

data class LoginResponse(
    val status: String,
    val message: String,
     val token: String?,
    val user: User?
)


/**
 * Datos necesarios para registrar un nuevo usuario.
 * Se envían al servidor en el cuerpo de la petición POST a /signup.
 */

data class SignupRequest(
    val name: String,
    val email: String,
    val password: String
)

// Modelo de datos para gastos
data class Gasto(
    val nombre: String,
    val cantidad: Double,
    val categoria: String
)


/**
 * Representa el perfil completo de un usuario en la aplicación.
 * Incluye información personal, estado de cuenta, preferencias y salas asignadas.
 */
data class UserPerfile(
    val id: Int,
    var name: String,
    var email: String,
    var role: String,
    var isActive: Boolean,
    var profileImageUrl: String?,
    var createdAt: String,
    var salas: List<Sala>,
    var preferences: UserPreferences
)


/**
 * Preferencias personales del usuario que afectan la experiencia de uso.
 * Pueden incluir idioma, notificaciones y modo visual.
 */

data class UserPreferences(
    var language: String,
    var receiveNotifications: Boolean,
    var darkModeEnabled: Boolean
)

/**
 * Modelo para actualizar el perfil del usuario.
 * Permite enviar solo los campos modificados al servidor.
 */

data class UpdateUserProfileRequest(
    val name: String,
    val email: String,
    val role: String,
    val isActive: Boolean,
    val profileImageUrl: String,
    val preferences: UserPreferences
)

/**
 * Modelo para cambiar la contraseña del usuario.
 * Requiere la contraseña actual y la nueva.
 */


data class ChangePasswordRequest(
    val currentPassword: String,
    val newPassword: String
)



