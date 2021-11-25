package pt.isec.a21280305.scratch.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import pt.isec.a21280305.scratch.R
import pt.isec.a21280305.scratch.databinding.ActivityImageBackgroundBinding
import pt.isec.a21280305.scratch.utils.ImageUtils
import java.io.File

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
    private var imagePath : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBackgroundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mode = intent.getIntExtra(PARAM_TYPE, SELECT)

        if(mode == SELECT) {
            binding.btnImage.apply {
                text = "Choose picture"
                setOnClickListener {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.setType("image/*")
                    /* DEPRECATED
                    startActivityForResult(intent, 10)
                    */
                    startActivityForResult.launch(intent)
                }
            }
        } else {
            binding.btnImage.apply {
                text = "New photo"
                setOnClickListener {
                    val folder = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    val imageFile = File.createTempFile("image", ".img", folder)
                    imagePath = imageFile.absolutePath
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                        val fileUri =  FileProvider.getUriForFile( context,
                            "pt.isec.a21280305.scratch.android.fileprovider", imageFile)
                        putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
                    }
                    startActivityForResultForPhoto.launch(intent)
                }
            }
        }

        updatePreview() // updates to the drawable image at the beginning

        if(ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQ_PERM_CODE)
        } else
            permissionsGranted = true
    }

    var startActivityForResultForPhoto = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult() ) { result ->
        if (result.resultCode != Activity.RESULT_OK) {
            imagePath = null
        }
        updatePreview() // updates to images if it's ok
    }

    var startActivityForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult() ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val resultIntent = result.data
            val uri = resultIntent?.data?.apply {
                val cursor = contentResolver.query(this,
                    arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)
                if (cursor !=null && cursor.moveToFirst())
                    imagePath = cursor.getString(0)
                updatePreview() // updates to selected image
            }
        }
    }
/* DEPRECATED METHOD
    override fun onActivityResult(requestCode: Int, resultCode: Int, info: Intent?)
    {
        if (requestCode == 10 && resultCode == Activity.RESULT_OK && info != null) {
            val uri = info.data?.apply {
                val cursor = contentResolver.query(this,
                    arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)
                if (cursor !=null && cursor.moveToFirst())
                    imagePath = cursor.getString(0)
                updatePreview()
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, info)
    }*/

    private fun updatePreview(){
        if(imagePath != null){
            ImageUtils.setPic(binding.frPreview, imagePath!!)
        }else{
            binding.frPreview.background = ResourcesCompat.getDrawable(resources, android.R.drawable.ic_menu_report_image, null)
        }
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

            if(imagePath == null){
                Snackbar.make(binding.frPreview, "Image not selected", Snackbar.LENGTH_LONG).show()
                return true
            }



            // TODO : if the user selected or captured an image

            val intent = Intent(this, DrawAreaActivity::class.java)
            intent.putExtra(DrawAreaActivity.TITLE_PARAM, binding.edTitle.text.toString())
            // TODO: set the desired image
            intent.putExtra(DrawAreaActivity.IMAGE_PARAM, imagePath)
            startActivity(intent)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}