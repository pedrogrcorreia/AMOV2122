package pt.isec.a21280304.aula1

import android.app.Application
import android.util.Log

class Aplicacao : Application() {

    private var _valor = 0

    val valor : Int
        get(){_valor++; return _valor}

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "APLICACAO.onCreate: "+this.valor)
    }
}