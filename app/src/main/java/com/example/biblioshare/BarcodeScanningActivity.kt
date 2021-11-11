package com.example.biblioshare

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.util.*

class BarcodeScanningActivity : AppCompatActivity() {

    var barcodeResult = Vector<String>()
    var captureButton: Button? = null
    var currentFileD: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil)
        captureButton = findViewById(R.id.accueil_scan_bouton)
        captureButton?.setOnClickListener { v -> onClickCapture(v) }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val inputImage: InputImage = InputImage.fromFilePath(this, Uri.fromFile(currentFileD))
                this.scanBarcodes(inputImage)
            }
        }

    private fun onClickCapture(view: View?) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 2)
            }
        }
        else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 3)
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 2)
            }
        }
        val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val pictureName: String = "tmp"
        val tmpFileD = File(getExternalFilesDir(null), pictureName)
        currentFileD = tmpFileD
        val uriImage: Uri = FileProvider.getUriForFile(this, "com.example.biblioshare.provider", tmpFileD)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriImage)
        resultLauncher.launch(intent)
    }

    override fun onRequestPermissionsResult( requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            2 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(
                        this,
                        "The app was not allowed to write in your storage",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            3 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(
                        this,
                        "The app was not allowed to read in your storage",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun scanBarcodes(image: InputImage) {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_EAN_13,
                Barcode.FORMAT_EAN_8
            )
            .build()

        val scanner = BarcodeScanning.getClient(options)

        val result = scanner.process(image)
            .addOnSuccessListener { barcodes ->
                for (barcode in barcodes) {
                    when (barcode.format) {
                        Barcode.FORMAT_EAN_13 -> {
                            val result = barcode.displayValue
                            barcodeResult.addElement(result)
                        }
                        Barcode.FORMAT_EAN_8 -> {
                            val result = barcode.displayValue
                            barcodeResult.addElement(result)
                        }
                    }

                }
                if(barcodeResult.size == 0){
                    Toast.makeText(this, "Pas de code barre de livre détecté", Toast.LENGTH_LONG).show()
                }
                else{
                    val intent: Intent = Intent(this,ResultatScanActivity::class.java)
                    val finalResult: Array<String> = Array<String>(size = barcodeResult.size, init = { _ -> "" })
                    var i = 0
                    for(s in barcodeResult){
                        finalResult[i] = s
                        i += 1
                    }
                    intent.putExtra("barcode", finalResult)
                    startActivity(intent)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Problème de prise de photo", Toast.LENGTH_LONG).show()
            }
    }
}
