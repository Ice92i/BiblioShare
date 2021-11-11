package com.example.biblioshare

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.biblioshare.modele.Livre
import kotlinx.android.synthetic.main.activity_possession_detail.*

class PossessionDetailActivity  : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_possession_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val livre = intent.getParcelableExtra<Livre>("id")

        livre_titre_textview.text = livre?.titre

        livre_auteur_textview.text = livre?.auteur

      //  livre_couverture_recherche_imageview

        livre_categorie_textview.text = livre?.categorie

        livre_date_textview.text = livre?.dateScan.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_livre, menu)
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

            R.id.supprimer_livre_action -> {
                AlertDialog.Builder(this)
                    .setTitle("Supprimer le livre de votre liste")
                    .setMessage("Etes-vous sûr ?")
                    .setPositiveButton("Oui") { _, _ ->
                        finish()
                        Toast.makeText(this, "Livre supprimé", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Non"){ dialog,_ ->
                        dialog.dismiss()
                    }
                    .show()
            }

        }
        return super.onOptionsItemSelected(item)
    }
}