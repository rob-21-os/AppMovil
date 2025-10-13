package com.example.maneylink.extensiones

import com.example.maneylink.dadesServidor.User
import com.example.maneylink.dadesServidor.UserPerfile
import com.example.maneylink.dadesServidor.UserPreferences


/**
 * Convierte un objeto [User] en un objeto [UserPerfile] para representar el perfil del usuario.
 *
 * Esta función de extensión copia los campos principales del usuario y asigna valores por defecto
 * para propiedades adicionales del perfil como `isActive`, `profileImageUrl`, `createdAt` y `preferences`.
 * También asegura que la lista de `salas` no sea nula, utilizando `emptyList()` si es necesario.
 *
 * @receiver Objeto [User] que se desea transformar.
 * @return Objeto [UserPerfile] con los datos del perfil del usuario.
 *
 * Ejemplo de uso:
 * ```
 * val perfil = usuario.toUserProfile()
 * ```
 */

fun User.toUserProfile(): UserPerfile {
    return UserPerfile(
        id = this.id,
        name = this.name,
        email = this.email,
        role = this.role,
        isActive = true,
        profileImageUrl = null,
        createdAt = "",
        salas = this.salas ?: emptyList(), // ✅ Asegura que no sea null
        preferences = UserPreferences("es", true, false)
    )
}
