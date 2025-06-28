package com.damaris.suquillo.cazarpatos

import android.app.Activity
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

class FileExternalManager(val actividad: Activity) : FileHandler{
    fun isExternalStorageWritable(): Boolean{
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }
    override fun SaveInformation(datosAGrabar:Pair<String,String>) {
        if(isExternalStorageWritable()) {
            FileOutputStream(
                File(
                    actividad.getExternalFilesDir(null),
                    SHAREDINFO_FILENAME
                )
            ).bufferedWriter().use { outputStream->
                outputStream.write(datosAGrabar.first)
                outputStream.write(System.lineSeparator())
                outputStream.write(datosAGrabar.second)
            }
        } else {
        Log.w("FileExternalManager", "Almacenamiento externo no escribible. No se guardaron los datos.")
    }
    }
    fun isExternalStorageReadable(): Boolean{
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }
    override fun ReadInformation():Pair<String,String> {
        if(isExternalStorageReadable()) {
            return try {
                FileInputStream(
                    File(
                        actividad.getExternalFilesDir(null),
                        SHAREDINFO_FILENAME
                    )
                ).bufferedReader().use {
                    val datoLeido= it.readText()
                    val textArray= datoLeido.split(System.lineSeparator(), limit = 2)
                    val email = if (textArray.isNotEmpty()) textArray[0] else ""
                    val clave = if (textArray.size > 1) textArray[1] else ""
                    return(email to clave)
                }
            } catch (e: FileNotFoundException) {
                Log.e("FileExternalManager", "Archivo '${SHAREDINFO_FILENAME}' no encontrado en almacenamiento externo. Devolviendo valores por defecto.", e)
                "" to ""
            } catch (e: Exception) {
                Log.e("FileExternalManager", "Error al leer archivo '${SHAREDINFO_FILENAME}' del almacenamiento externo.", e)
                "" to ""
            }
        }
        return("".to(""))
    }
}