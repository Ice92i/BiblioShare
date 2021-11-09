package com.example.biblioshare

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_accueil.*

class AccueilActivity : AppCompatActivity () {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil)

        accueil_recherche_bouton.setOnClickListener {
            val intent = Intent(this, RechercheActivity::class.java)
            startActivity(intent)
        }

        // Accéder à l'appareil photo pour le scan

/*        accueil_scan_bouton.setOnClickListener {
            val intent = Intent(this, ConnexionActivity::class.java)
            startActivity(intent)
        }*/
    }
}