package com.example.maneylink.dadesUtils

import android.content.Context
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import com.example.maneylink.dadesServidor.Gasto
import com.example.maneylink.dadesServidor.User
import java.io.File
import java.io.FileOutputStream
import kotlin.collections.forEach

/**
 * Genera un archivo PDF con un informe de gastos familiares para un usuario específico.
 *
 * El PDF se guarda en el directorio externo de archivos de la aplicación, dentro de una carpeta llamada "pdfs".
 * El contenido incluye un encabezado con el nombre del usuario y una lista de gastos con su nombre, categoría y cantidad.
 *
 * @param context Contexto de la aplicación, necesario para acceder al almacenamiento externo.
 * @param user Usuario al que pertenece el informe. Se utiliza su nombre para personalizar el título y el nombre del archivo.
 * @param gastos Lista de objetos `Gasto` que representan los gastos a incluir en el informe.
 * @return Archivo PDF generado con el contenido del informe.
 *
 */

// Función para generar PDF con los gastos
fun generarPDF(context: Context, user: User, gastos: List<Gasto>): File {
    val pdfDir = File(context.getExternalFilesDir(null), "pdfs")
    if (!pdfDir.exists()) pdfDir.mkdirs()

    val pdfFile = File(pdfDir, "Gastos_${user.name}.pdf")

    val document = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
    val page = document.startPage(pageInfo)
    val canvas = page.canvas
    val paint = Paint()

    paint.textSize = 16f
    canvas.drawText("Informe de gastos familiares - ${user.name}", 40f, 50f, paint)
    paint.textSize = 12f

    var y = 90f
    gastos.forEach { gasto ->
        canvas.drawText("${gasto.nombre} (${gasto.categoria}): ${gasto.cantidad} €", 40f, y, paint)
        y += 20f
    }

    document.finishPage(page)
    document.writeTo(FileOutputStream(pdfFile))
    document.close()

    return pdfFile
}
