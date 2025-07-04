package com.damaris.suquillo.cazarpatos

import android.content.Context

class SharedPreferencesManager (val actividad: LoginActivity): FileHandler{
    override fun SaveInformation(datosAGrabar:Pair<String,String>){
        val sharedPref = actividad.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(LOGIN_KEY , datosAGrabar.first)
        editor.putString(PASSWORD_KEY, datosAGrabar.second)
        editor.apply()
    }
    override fun ReadInformation():Pair<String,String>{
        val sharedPref = actividad.getPreferences(Context.MODE_PRIVATE)
        val email = sharedPref.getString(LOGIN_KEY,"").toString()
        val clave = sharedPref.getString(PASSWORD_KEY,"").toString()
        return (email to clave)
    }
}
