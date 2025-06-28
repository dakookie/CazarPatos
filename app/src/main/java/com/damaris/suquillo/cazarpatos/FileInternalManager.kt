import android.app.Activity
import android.content.Context
import java.io.FileNotFoundException
import android.util.Log
import com.damaris.suquillo.cazarpatos.FileHandler

class FileInternalManager(val actividad: Activity) : FileHandler {
    override fun SaveInformation(datosAGrabar: Pair<String, String>) {
        val texto = datosAGrabar.first + System.lineSeparator() + datosAGrabar.second
        actividad.openFileOutput("fichero.txt", Context.MODE_PRIVATE).bufferedWriter().use { fos ->
            fos.write(texto)
        }
    }

    override fun ReadInformation(): Pair<String, String> {
        return try {
            actividad.openFileInput("fichero.txt").bufferedReader().use {
                val datoLeido = it.readText()
                val parts = datoLeido.split(System.lineSeparator(), limit = 2)
                if (parts.size == 2) {
                    parts[0] to parts[1]
                } else {
                    datoLeido to ""
                }
            }
        } catch (e: FileNotFoundException) {
            Log.e("FileInternalManager", "fichero.txt no encontrado, devolviendo valores por defecto.", e)
            "" to ""
        } catch (e: Exception) {
            Log.e("FileInternalManager", "Error al leer fichero.txt.", e)
            "" to ""
        }
    }
}