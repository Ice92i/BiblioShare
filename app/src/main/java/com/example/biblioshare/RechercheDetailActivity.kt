package com.example.biblioshare

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.biblioshare.modele.Livre
import com.example.biblioshare.modele.Utilisateur
import com.example.biblioshare.modele.UserMessage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_recherche_detail.*

class RechercheDetailActivity : AppCompatActivity() {

    private lateinit var utilisateur : Utilisateur
    lateinit var livre : Livre
    private lateinit var distance : String

    companion object {
        val USER_KEY = "USER_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recherche_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        utilisateur = this.intent.extras!!.get("UTILISATEUR") as Utilisateur
        livre = this.intent.extras!!.get("LIVRE") as Livre
        distance = this.intent.getStringExtra("DISTANCE").toString()

        livre_titre_textview.text = livre.Titre
        livre_auteur_textview.text = livre.Auteur
        Picasso
            .get()
            .load(livre.Image_du_livre.toString())
            .into(livre_couverture_recherche_imageview)
       // livre_categorie_textview.text = livre?.categorie
        livre_proprietaire_textview.text = utilisateur.Pseudonyme
        livre_distance_textview.text = distance

        contacter_proprietaire_bouton.setOnClickListener {
            launchConversation()
        }

    }

    private fun launchConversation() {
        val userToMessage = UserMessage(utilisateur.UID, utilisateur.Pseudonyme)
        val intentChatLog = Intent(this, ConversationActivity::class.java)
        intentChatLog.putExtra(USER_KEY, userToMessage)
        startActivity(intentChatLog)
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