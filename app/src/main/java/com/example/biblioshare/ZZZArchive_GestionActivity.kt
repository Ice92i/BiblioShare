package com.example.biblioshare


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.zzzarchive_activity_gestion.*

class ZZZArchive_GestionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zzzarchive_activity_gestion)

        gestion_connexion_bouton.setOnClickListener {
            val intent = Intent(this, ConnexionActivity::class.java)
            startActivity(intent)
        }

        gestion_inscription_bouton.setOnClickListener {
            val intent = Intent(this, InscriptionActivity::class.java)
            startActivity(intent)
        }
    }
}