package com.example.biblioshare

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.biblioshare.modele.Livre
import com.example.biblioshare.modele.Utilisateur
import kotlinx.coroutines.runBlocking

class RechercheListeActivity : AppCompatActivity() {

    private var livres: MutableList<Livre>? = ArrayList()
    private var utilisateurs : MutableList<Utilisateur>? = ArrayList()
    private lateinit var recyclerView : RecyclerView
    private lateinit var user : Utilisateur

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recherche_liste)

        recyclerView = findViewById(R.id.livres_recherche_recyclerview)

        //Set recyclerview
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        livres  = this.intent.getParcelableArrayListExtra("LIVRES")
        utilisateurs  = this.intent.getParcelableArrayListExtra("UTILISATEURS")
        user = this.intent.extras!!.get("USER") as Utilisateur
    }

    override fun onStart() {
        super.onStart()

        runBlocking {
            recyclerView.adapter = RechercheLivreAdaptateur(livres, utilisateurs, user)
        }
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