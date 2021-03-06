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
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.io.InputStream
import java.net.URL


class ScanDetailActivity : AppCompatActivity() {

    var barcodeResult = Vector<String>()
    var titre: TextView? = null
    var auteur: TextView? = null
    var image: ImageView? = null
    var okbutton: Button? = null
    var cancelbutton: Button? = null
    val db = FirebaseFirestore.getInstance()
    var user: String = ""
    var idLivre: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            db.collection("livres")
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (livre in it.result) {
                            if (livre.data.getValue("ISBN") == barcode) {
                                titre!!.text = livre.data.getValue("Titre").toString()
                                auteur!!.text = livre.data.getValue("Auteur").toString()
                                Picasso.get().load(livre.data.getValue("ImageLien").toString()).into(image!!)
                                idLivre = livre.id
                            }
                        }
                    }
                }
            okbutton?.setOnClickListener {onOkCapture(barcode)}
            cancelbutton?.setOnClickListener {onCancelCapture(barcode)}
        }
        else{
            val intent: Intent = Intent(this,AccueilActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onOkCapture(s: String?) {
        var pseudo: String = ""
        barcodeResult.remove(s)
        //ajoute le livre dans la base de donn??es
        var userID: String = ""
        db.collection("livres").document(idLivre).collection("utilisateurs")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (user in it.result) {
                        userID = user.id
                    }
                    db.collection("usermessage")
                        .get()
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                for (user in it.result) {
                                    if (user.data.getValue("uid") == Firebase.auth.currentUser!!.uid) {
                                        pseudo = user.data.getValue("username").toString()
                                    }
                                }
                            }
                            db.collection("livres").document(idLivre).collection("utilisateurs").document(userID)
                                .update(
                                    "UID", Firebase.auth.currentUser!!.uid,
                                    "Pseudonyme", pseudo
                                ).addOnCompleteListener{
                                    if(it.isSuccessful){
                                        Toast.makeText(this, "Livre ajout??", Toast.LENGTH_LONG).show()
                                        Log.e("ok", "ok")
                                    }
                                    else{
                                        Toast.makeText(this, "Probl??me d'ajout", Toast.LENGTH_LONG).show()
                                        Log.e("ok", "pb")
                                    }
                                }
                        }
                }
            }
        continueList()
    }

    private fun onCancelCapture(s: String?) {
        barcodeResult.remove(s)
        Toast.makeText(this, "Ajout annul??", Toast.LENGTH_LONG).show()
        continueList()
    }

    private fun continueList(){
        if(!barcodeResult.isEmpty()){
            var barcode: String = barcodeResult.firstElement()
            db.collection("livres")
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (livre in it.result) {
                            Log.e("ok",livre.data.getValue("ISBN").toString())
                            if (livre.data.getValue("ISBN") == barcode) {
                                titre!!.text = livre.data.getValue("Titre").toString()
                                auteur!!.text = livre.data.getValue("Auteur").toString()
                                Picasso.get().load(livre.data.getValue("ImageLien").toString()).into(image!!)
                                idLivre = livre.id
                            }
                        }
                    }
                }
            okbutton?.setOnClickListener {onOkCapture(barcode)}
            cancelbutton?.setOnClickListener {onCancelCapture(barcode)}
        }
        else{
            val intent: Intent = Intent(this,AccueilActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_complet, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_action -> {
                val intent = Intent(this, AccueilActivity::class.java)
                startActivity(intent)
            }

            R.id.messages_action -> {
                val intent = Intent(this, MessagerieActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}