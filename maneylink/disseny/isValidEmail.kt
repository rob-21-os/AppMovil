package com.example.maneylink.disseny

/**
 * Valida si una cadena de texto tiene el formato de un correo electrónico válido.
 *
 * Utiliza el patrón predefinido `android.util.Patterns.EMAIL_ADDRESS` para verificar
 * que el correo cumpla con la estructura estándar (ej. usuario@dominio.com).
 *
 * @param email Cadena de texto que representa el correo electrónico a validar.
 * @return `true` si el correo tiene un formato válido; `false` en caso contrario.
 *
 * Ejemplo de uso:
 * ```
 * val esValido = isValidEmail("usuario@ejemplo.com")
 *
 */
fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
