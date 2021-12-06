package com.example.biblioshare

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.biblioshare.modele.Livre
import kotlinx.android.synthetic.main.activity_recherche_detail.*

class RechercheDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recherche_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val livre = intent.getParcelableExtra<Livre>("id")

        livre_titre_textview.text = livre?.Titre

        livre_auteur_textview.text = livre?.Auteur

        //  livre_couverture_recherche_imageview

       // livre_categorie_textview.text = livre?.categorie

        //  livre_proprietaire_textview.text = livre.getProprietaire

        //  livre_proprietaire_textview.text = livre.getProprietaire
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