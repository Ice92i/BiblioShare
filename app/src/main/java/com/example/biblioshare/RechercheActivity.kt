package com.example.biblioshare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_recherche.*

private const val TAG = "RechercheActivity"

class RechercheActivity : AppCompatActivity () {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connexion)

        recherche_bouton.setOnClickListener {
            val recherche = recherche_livre_edittext.text.toString()
            Log.d(TAG, "Recherche : $recherche") // afficher les entrées (pour test seulement)

            // Comparer la recherche à la bdd des livres, si résultat :

            if (true) {
                val intent = Intent(this, RechercheActivity::class.java)
                startActivity(intent)
            }

            else {
                val intent = Intent(this, NotFoundActivity::class.java)
                startActivity(intent)
            }
            finish()

        }
    }

}