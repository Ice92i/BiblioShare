package com.example.biblioshare

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class PossessionDetailActivity  : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_possession_detail)
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