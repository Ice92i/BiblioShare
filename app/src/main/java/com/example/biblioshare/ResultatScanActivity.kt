package com.example.biblioshare

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ResultatScanActivity : AppCompatActivity() {
    var barcodeResult = Vector<String>()
    var titre: TextView? = null
    var okbutton: Button? = null
    var cancelbutton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_resultat)
        okbutton = findViewById(R.id.valider_scan_bouton)
        cancelbutton = findViewById(R.id.rescanner_bouton)
        titre = findViewById(R.id.livre_titre_textview)
        val barcodeResult2 = intent.getStringArrayExtra("barcode")
        if (barcodeResult2 != null) {
            for (s in barcodeResult2){
                barcodeResult.addElement(s)
            }
        }
        if(!barcodeResult.isEmpty()){
            var barcode: String = barcodeResult.firstElement()
            titre!!.text = barcode
            okbutton?.setOnClickListener {onOkCapture(barcode)}
            cancelbutton?.setOnClickListener {onCancelCapture(barcode)}
        }
        else{
            val intent: Intent = Intent(this,BarcodeScanningActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onOkCapture(s: String?) {
        barcodeResult.remove(s)
        //ajoute le livre dans la base de données
        continueList()
    }

    private fun onCancelCapture(s: String?) {
        barcodeResult.remove(s)
        continueList()
    }

    private fun continueList(){
        if(!barcodeResult.isEmpty()){
            var barcode: String = barcodeResult.firstElement()
            titre!!.text = barcode
            okbutton?.setOnClickListener {onOkCapture(barcode)}
            cancelbutton?.setOnClickListener {onCancelCapture(barcode)}
        }
        else{
            val intent: Intent = Intent(this,BarcodeScanningActivity::class.java)
            startActivity(intent)
        }
    }
}