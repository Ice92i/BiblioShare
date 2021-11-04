package com.example.biblioshare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_connexion.*
import kotlinx.android.synthetic.main.activity_gestion_compte.*
import kotlinx.android.synthetic.main.activity_inscription.*

private const val TAG = "ConnexionActivity"

class ConnexionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connexion)

        inscription_bouton.setOnClickListener{
           val email = inscription_email_edittext.text.toString()
            Log.d(TAG,"Email : $email") // afficher les entrées (pour test seulement)
            val motdepasse = inscription_mdp_edittext.text.toString()
            Log.d(TAG,"MDP : $motdepasse") // afficher les entrées (pour test seulement)

            // Comparer les id à Firebase pour verifier et autoriser la connexion

            // Si les id sont corrects :

                val intent = Intent(this, AccueilActivity::class.java)
                startActivity(intent)

            finish()

        }
    }
}