package pt.isec.a21280304.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import pt.isec.a21280304.calculator.databinding.ActivityMainBinding
import java.lang.Double.parseDouble

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityMainBinding

    private var lastNum: Double = 0.0
    private var currentNum: Double = 0.0
    private var operation: String = ""
    private var calc: String = ""
    private var res: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btn1.setOnClickListener(onButtonNumber)
        binding.btn2.setOnClickListener(onButtonNumber)
        binding.btn3.setOnClickListener(onButtonNumber)
        binding.btn4.setOnClickListener(onButtonNumber)
        binding.btn5.setOnClickListener(onButtonNumber)
        binding.btn6.setOnClickListener(onButtonNumber)
        binding.btn7.setOnClickListener(onButtonNumber)
        binding.btn8.setOnClickListener(onButtonNumber)
        binding.btn9.setOnClickListener(onButtonNumber)
        binding.btn0.setOnClickListener(onButtonNumber)
        binding.btnadd.setOnClickListener(onButtonOperation)
        binding.btnsub.setOnClickListener(onButtonOperation)
        binding.btnmul.setOnClickListener(onButtonOperation)
        binding.btndiv.setOnClickListener(onButtonOperation)

        binding.btncomma.setOnClickListener{
            if(binding.calculation.text != ""){
                binding.calculation.text =
                    binding.calculation.text.toString() + binding.btncomma.text
            }
            else {
                binding.calculation.text = ""
            }
        }


        binding.btnc.setOnClickListener{
            lastNum = 0.0
            currentNum = 0.0
            res = 0.0
            operation = ""
            binding.calculation.text = ""
        }

        binding.btneq.setOnClickListener{
            if(currentNum != 0.0 || lastNum != 0.0) {
                calc = convertComma(binding.calculation.text.toString())
                currentNum = parseDouble(calc)
                res = getOperator(operation).invoke(lastNum, currentNum)
                calc = convertPoint(res.toString())
                binding.calculation.text = calc
                lastNum = res;
                currentNum = 0.0
            }
        }
    }

    fun convertComma(calc : String): String {
        return calc.replace(',', '.', true)
    }

    fun convertPoint(calc : String): String{
        return calc.replace('.', ',', true)
    }

    private fun getOperator(operator : String): (Double, Double) -> Double {
        return when(operator) {
            "+" -> { a, b -> a + b }
            "-" -> { a, b -> a - b }
            "/" -> { a, b -> a / b }
            "x" -> { a, b -> a * b }
            else -> throw Exception("Error, operator not found")
        }
    }

    val onButtonNumber = View.OnClickListener { v ->
        val btn = v as Button
        if(binding.calculation.text != "" && res != 0.0){
            lastNum = 0.0
            currentNum = 0.0
            binding.calculation.text = btn.text
        } else {
            binding.calculation.text = binding.calculation.text.toString()+btn.text
        }
    }

    private val onButtonOperation = View.OnClickListener { v ->
        val btn = v as Button
        if(binding.calculation.text == ""){
            lastNum = 0.0
        } else {
            if (lastNum == 0.0){
                calc = convertComma(binding.calculation.text.toString())
                lastNum = parseDouble(calc)
                operation = btn.text.toString()
                binding.calculation.text = ""
            }
            else{
                operation = btn.text.toString()
                calc = convertComma(binding.calculation.text.toString())
                currentNum = parseDouble(calc)
                binding.calculation.text = ""
            }
        }
    }

    override fun onClick(v: View?) {
    }
}