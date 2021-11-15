package pt.isec.a21280305.scratch.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import com.google.android.material.snackbar.Snackbar
import pt.isec.a21280305.scratch.R
import pt.isec.a21280305.scratch.databinding.ActivitySolidBackgroundBinding

class SolidBackgroundActivity : AppCompatActivity() {
    lateinit var b : ActivitySolidBackgroundBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySolidBackgroundBinding.inflate(layoutInflater)
        setContentView(b.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        b.seekRed.max = 255
        b.seekRed.progress = 255
        b.seekRed.setOnSeekBarChangeListener(procSeekBar)

        b.seekGreen.apply {
            max = 255
            progress = 255
            setOnSeekBarChangeListener(procSeekBar)
        }

        b.seekBlue.apply {
            max = 255
            progress = 255
            setOnSeekBarChangeListener(procSeekBar)
        }

    }

    val procSeekBar = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            updatePreview()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    }

    fun updatePreview(){
        b.frPreview.setBackgroundColor(Color.rgb(b.seekRed.progress, b.seekGreen.progress, b.seekBlue.progress))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_create, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnCreate){
            if(b.edTitle.text.trim().isEmpty()){
                Snackbar.make(b.edTitle, "You must input a title", Snackbar.LENGTH_LONG).show()
                b.edTitle.requestFocus()
                return true
            }
            val intent = Intent(this, DrawAreaActivity::class.java)
            intent.putExtra("title", b.edTitle.text.toString())
            intent.putExtra("red", b.seekRed.progress)
            intent.putExtra("green", b.seekGreen.progress)
            intent.putExtra("blue", b.seekBlue.progress)
            startActivity(intent)
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}