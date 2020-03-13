package com.example.fileproviderdemo

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnShare.setOnClickListener {
            if (background.isLaidOut) {

                launch {
                    val bitmap = background.drawToBitmap()

                    val uri = withContext(Dispatchers.IO) {
                        val path = saveImage(bitmap)
                        FileProvider.getUriForFile(this@MainActivity, "${BuildConfig.APPLICATION_ID}.provider", File(path!!))
                    }

                    if (uri != null) {
                        sharePhoto(this@MainActivity, uri)
                    }
                }
            }
        }
    }

    private fun sharePhoto(context: Context, uri: Uri) {
        val targetIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "image/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            //setPackage("com.instagram.android")
        }
        val intentChooser = Intent.createChooser(targetIntent, "Share to")
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        //context.grantUriPermission("com.instagram.android", uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(intentChooser) // throw java.lang.SecurityException: Permission Denial: reading androidx.core.content.FileProvider
        //context.startActivity(targetIntent) // no problem
    }

    private fun saveImage(bm: Bitmap): String? {
        // Create Path to save Image
        try {
            val imageFile = File(getExternalFilesDir(null)!!.path, "photo.jpg")
            val out = FileOutputStream(imageFile)
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
            return imageFile.path
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun onDestroy() {
        cancel()

        super.onDestroy()
    }
}