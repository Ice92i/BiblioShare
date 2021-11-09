package com.example.biblioshare


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_inscription.*

private const val TAG = "InscriptionActivity"

class InscriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscription)
        inscription_bouton.setOnClickListener{
            val nom = inscription_nom_edittext.text.toString()
            Log.d(TAG,"Nom : ${nom.toUpperCase()}")
            val prenom = inscription_prenom_edittext.text.toString()
            Log.d(TAG,"Prénom : $prenom")
            val email = inscription_email_edittext.text.toString()
            Log.d(TAG,"Email : $email")
            val motdepasse = inscription_mdp_edittext.text.toString()
            Log.d(TAG,"MDP : $motdepasse")

            // Créer un nouveau compte et ajouter les infos à FireBase

            val intent = Intent(this, AccueilActivity::class.java)
            startActivity(intent)

            finish()
        }
    }
}