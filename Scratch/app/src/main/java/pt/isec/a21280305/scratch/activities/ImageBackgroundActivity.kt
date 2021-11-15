package pt.isec.a21280305.scratch.activities

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import pt.isec.a21280305.scratch.R
import pt.isec.a21280305.scratch.databinding.ActivityImageBackgroundBinding

class ImageBackgroundActivity : AppCompatActivity() {
    companion object{
        const val SELECT = 1
        const val CAPTURE = 2
        const val PARAM_TYPE = "type"

        private const val REQ_PERM_CODE = 1234
    }

    private var permissionsGranted = false
    private var mode = SELECT
    lateinit var binding : ActivityImageBackgroundBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBackgroundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mode = intent.getIntExtra(PARAM_TYPE, SELECT)

        if(mode == SELECT) {
            binding.btnImage.apply {
                text = "Choose picture"
            }
        } else {
            binding.btnImage.apply {
                text = "New photo"
            }
        }

        if(ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQ_PERM_CODE)
        } else
            permissionsGranted = true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQ_PERM_CODE){
            permissionsGranted =
                (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            binding.btnImage.isEnabled = permissionsGranted
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_create, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.mnCreate)?.isVisible = permissionsGranted
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.mnCreate){
            if(binding.edTitle.text.trim().isEmpty()){
                Snackbar.make(binding.edTitle, "You must input a title", Snackbar.LENGTH_LONG).show()
                binding.edTitle.requestFocus()
                return true
            }

            // TODO : if the user selected or captured an image

            val intent = Intent(this, DrawAreaActivity::class.java)
            intent.putExtra(DrawAreaActivity.TITLE_PARAM, binding.edTitle.text.toString())
            // TODO: set the desired image
            startActivity(intent)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}