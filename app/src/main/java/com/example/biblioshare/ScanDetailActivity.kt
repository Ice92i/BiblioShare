package com.example.biblioshare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import java.util.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class ScanDetailActivity : AppCompatActivity() {

    var barcodeResult = Vector<String>()
    var titre: TextView? = null
    var auteur: TextView? = null
    var image: ImageView? = null
    var okbutton: Button? = null
    var cancelbutton: Button? = null
    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ref = FirebaseDatabase.getInstance().getReference("livres")
        setContentView(R.layout.activity_scan_detail)
        okbutton = findViewById(R.id.valider_scan_bouton)
        cancelbutton = findViewById(R.id.rescanner_bouton)
        titre = findViewById(R.id.livre_titre_textview)
        auteur = findViewById(R.id.livre_auteur_textview)
        image = findViewById(R.id.livre_couverture_scan_imageview)
        val barcodeResult2 = intent.getStringArrayExtra("barcode")
        if (barcodeResult2 != null) {
            for (s in barcodeResult2){
                barcodeResult.addElement(s)
            }
        }
        if(!barcodeResult.isEmpty()){
            var barcode: String = barcodeResult.firstElement()
            for(livre:Livre in FirebaseConnection.livreList){
                Log.e("ok","ok")
                if(livre.ISBN == barcode){
                    titre!!.text = livre.Titre
                    auteur!!.text = livre.Auteur
                }
            }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_complet, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.retour_action -> {
               finish()
            }

            R.id.messages_action -> {
                val intent = Intent(this, MessagerieActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}