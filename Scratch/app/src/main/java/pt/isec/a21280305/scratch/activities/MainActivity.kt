package pt.isec.a21280305.scratch.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import pt.isec.a21280305.scratch.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onSolidBackground(view: android.view.View) {
        //Toast.makeText(this, "Available soon", Toast.LENGTH_LONG).show()
        val intent = Intent(this, SolidBackgroundActivity::class.java)
        startActivity(intent)
    }
    fun onGalleryImage(view: android.view.View) {
        val intent = Intent(this, ImageBackgroundActivity::class.java)
        intent.putExtra(ImageBackgroundActivity.PARAM_TYPE, ImageBackgroundActivity.SELECT)
        startActivity(intent)
    }
    fun onPhotography(view: android.view.View) {
        val intent = Intent(this, ImageBackgroundActivity::class.java)
        intent.putExtra(ImageBackgroundActivity.PARAM_TYPE, ImageBackgroundActivity.CAPTURE)
        startActivity(intent)
    }
    fun onHistory(view: android.view.View) {
        //Snackbar.make(view, R.string.msg_soon, Snackbar.LENGTH_LONG).show()
        startActivity(Intent(this, HistoryActivity::class.java))
    }
}