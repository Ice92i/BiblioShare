package com.example.biblioshare


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_inscription.*
import java.util.*

private const val TAG = "InscriptionActivity"

class InscriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscription)
        inscription_bouton.setOnClickListener {
            val nom = inscription_nom_edittext.text.toString()
            Log.d(TAG, "Nom : ${nom.uppercase(Locale.getDefault())}")
            val prenom = inscription_prenom_edittext.text.toString()
            Log.d(TAG, "Prénom : $prenom")
            val email = inscription_email_edittext.text.toString()
            Log.d(TAG, "Email : $email")
            val motdepasse = inscription_mdp_edittext.text.toString()
            Log.d(TAG, "MDP : $motdepasse")

            // Créer un nouveau compte et ajouter les infos à FireBase

            val intent = Intent(this, AccueilActivity::class.java)
            startActivity(intent)

            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_retour, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.retour_action -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}