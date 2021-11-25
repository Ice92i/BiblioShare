package com.example.biblioshare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_recherche.*

private const val TAG = "RechercheActivity"

class RechercheActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zzzarchive_activity_connexion)

        recherche_bouton.setOnClickListener {
            val recherche = recherche_livre_edittext.text.toString()
            Log.d(TAG, "Recherche : $recherche") // afficher les entrées (pour test seulement)

            // Comparer la recherche à la bdd des livres, si résultat :

            if (true) {
                val intent = Intent(this, RechercheListeActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, NotFoundActivity::class.java)
                startActivity(intent)
            }
            finish()

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