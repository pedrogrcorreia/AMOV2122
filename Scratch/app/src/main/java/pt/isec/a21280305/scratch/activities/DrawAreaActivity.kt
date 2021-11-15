package pt.isec.a21280305.scratch.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import pt.isec.a21280305.scratch.R
import pt.isec.a21280305.scratch.databinding.ActivityDrawAreaBinding
import pt.isec.a21280305.scratch.model.Scratch
import pt.isec.a21280305.scratch.views.DrawArea

class DrawAreaActivity : AppCompatActivity() {
    companion object {
        const val TITLE_PARAM = "title"
        const val RED_PARAM = "red"
        const val GREEN_PARAM = "green"
        const val BLUE_PARAM = "blue"
        const val IMAGE_PARAM = "image"
        const val INDEX_PARAM = "index"
    }

    lateinit var scratch : Scratch
    lateinit var binding: ActivityDrawAreaBinding
    lateinit var drawArea : DrawArea

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawAreaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val title = intent.getStringExtra(TITLE_PARAM) ?: "No name"

        val r = intent.getIntExtra(RED_PARAM, 255)
        val g = intent.getIntExtra(GREEN_PARAM, 255)
        val b = intent.getIntExtra(BLUE_PARAM, 255)

        scratch = Scratch(title, bkgColor = Color.rgb(r, g, b))

        supportActionBar?.title = "Scratch: " + title

        drawArea = DrawArea(this, scratch)
        binding.frDrawArea.addView(drawArea)

        //frFrameLayout = findViewById<FrameLayout>(R.id.frDrawArea)

        //frFrameLayout.setBackgroundColor(Color.rgb(r, g, b)) -> set responsability to View DrawArea

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_draw, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.groupId){
            R.id.grpColors -> {
                item.setChecked(true)
                drawArea.lineColor = Color.parseColor(item.title.toString())
            }
            else -> {
                when (item.itemId){
                    R.id.mnSave -> {
                        finish()
                    }
                    else -> {
                        return super.onOptionsItemSelected(item)
                    }
                }
            }
        }
        return true
    }
}