package pt.isec.a21280305.scratch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import pt.isec.a21280305.scratch.R
import pt.isec.a21280305.scratch.databinding.ActivityHistoryBinding
import pt.isec.a21280305.scratch.model.Scratches

class HistoryActivity : AppCompatActivity() {
    lateinit var binding : ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lvHistory.adapter = HistoryAdapter()
    }

    class HistoryAdapter : BaseAdapter() {
        override fun getCount(): Int = Scratches.list.size

        override fun getItem(p0 : Int): Any = Scratches.list[p0]

        override fun getItemId(p0: Int): Long = p0.toLong()

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val view = LayoutInflater.from(p2!!.context).inflate(R.layout.history_item, p2, false)
            val scratch = Scratches.list[p0]
            view.findViewById<TextView>(R.id.tvTitle).text = scratch.title
            view.findViewById<TextView>(R.id.tvInfo).text = scratch.bkgImage ?: String.format("%8X", scratch.bkgColor)
            view.findViewById<ImageView>(R.id.imgPreview).setImageBitmap(scratch.preview)
            return view
        }
    }
}