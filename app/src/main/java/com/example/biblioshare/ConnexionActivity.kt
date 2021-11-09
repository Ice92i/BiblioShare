package com.example.biblioshare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_connexion.*

private const val TAG = "ConnexionActivity"

class ConnexionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connexion)

        connexion_bouton.setOnClickListener {
            val email = connexion_email_edittext.text.toString()
            Log.d(TAG, "Email : $email") // afficher les entrées (pour test seulement)
            val motdepasse = connexion_mdp_edittext.text.toString()
            Log.d(TAG, "MDP : $motdepasse") // afficher les entrées (pour test seulement)

            // Comparer les id à Firebase pour verifier et autoriser la connexion

            // Si les id sont corrects :

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
                this.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}