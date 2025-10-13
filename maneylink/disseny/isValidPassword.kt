package com.example.maneylink.disseny

/**
 * Valida si una contraseña cumple con los requisitos de seguridad establecidos.
 *
 * La contraseña debe contener al menos:
 * - Una letra mayúscula.
 * - Un número.
 * - Un carácter especial (como !, @, #, $, %, etc.).
 * - Tener una longitud mínima de 6 caracteres.
 *
 * @param password Cadena de texto que representa la contraseña a validar.
 * @return `true` si la contraseña cumple con todos los criterios; `false` en caso contrario.
 *
 * Ejemplo de uso:
 * ```
 * val esValida = isValidPassword("Hola123!")
 * ```
 */

fun isValidPassword(password: String): Boolean {
    val regex = Regex(
        """^(?=.*[A-Z])(?=.*\d)(?=.*[!@#\$%^&*()_+\-=\[\]{};':"\\|,.<>/?]).{6,}$"""
    )
    return regex.matches(password)
}
