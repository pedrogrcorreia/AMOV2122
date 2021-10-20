package pt.isec.a21280304.aula2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import pt.isec.a21280304.aula2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val tvMsg1 : TextView = findViewById<TextView>(R.id.tvMsg);
//        tvMsg1.text = "Arq. Moveis"

        binding.tvMsg.text = "P4 Amov"

        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(ProcBotao());
        binding.btn3.setOnClickListener(ProcBotao2(binding.tvMsg))
        binding.btn4.setOnClickListener(procBotao3)
//        binding.btn5.setOnClickListener {
//            binding.tvMsg.text = "Botão 5"
//        } -> exemplo com função lambda

    }

    override fun onClick(v: View?) {
        binding.tvMsg.text = "Botão 1"
    }

    inner class ProcBotao : View.OnClickListener {
        override fun onClick(p0: View?){
          binding.tvMsg.text = "Botão 2"
        }
    }

    class ProcBotao2(val tv : TextView) : View.OnClickListener {
        override fun onClick(p0: View?){
            tv.text = "Botão 3"
        }
    }

    val procBotao3 = object : View.OnClickListener {
        override fun onClick(v: View?) {
            binding.tvMsg.text = "Botão 4"
        }
    }

    fun onBotao5(view: android.view.View) {
        //binding.tvMsg.text = "Botão 5"
        // Método para associar o texto de todos os botões
        val btn = view as Button
        binding.tvMsg.text = btn.text
    }
}