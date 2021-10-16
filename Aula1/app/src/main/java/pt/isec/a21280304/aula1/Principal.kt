package pt.isec.a21280304.aula1

import android.app.Activity
import android.os.Bundle
import android.util.Log

const val TAG = "AMOVP4"

class Principal : Activity() {
    val app : Aplicacao by lazy {application as Aplicacao} // initialize after first opening of application

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.principal)

        Log.i(TAG, "onCreate: "+(application as Aplicacao).valor) // cast to Aplicacao (ex)
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: "+app.valor)
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart: "+app.valor)
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: "+app.valor+"    #   "+Objeto.valor)
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: "+app.valor+"    #   "+Objeto.valor)
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop: "+app.valor)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: "+app.valor)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState: "+app.valor)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState"+app.valor)
    }
}