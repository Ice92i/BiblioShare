package com.example.biblioshare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.biblioshare.modele.Livre
import com.example.biblioshare.modele.Utilisateur
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_possession_detail.*
import kotlinx.android.synthetic.main.activity_possession_detail.view.*
import java.text.SimpleDateFormat
import java.util.*

class PossessionDetailActivity  : AppCompatActivity(){

    lateinit var date : Date
    lateinit var livre : Livre

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_possession_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        livre = this.intent.extras!!.get("LIVRE") as Livre
        date = this.intent.extras!!.get("DATE") as Date

        livre_titre_textview.text = livre.Titre
        livre_auteur_textview.text = livre.Auteur
        Picasso
            .get()
            .load(livre.Image_du_livre.toString())
            .into(livre_couverture_imageview)

        livre_date_textview.text = SimpleDateFormat("dd/MM/yyyy").format(date)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_complet, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_action -> {
                val intent = Intent(this, AccueilActivity::class.java)
                startActivity(intent)
            }

            R.id.messages_action -> {
                val intent = Intent(this, MessagerieActivity::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }
}