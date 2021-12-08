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
import kotlinx.android.synthetic.main.activity_possession_liste.*
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.collections.ArrayList

class PossessionListeActivity : AppCompatActivity() {

    private var livres : MutableList<Livre>? = ArrayList()
    private var dateScan :  MutableList<Date>? = ArrayList()
    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_possession_liste)

        recyclerView = findViewById(R.id.livres_possession_recyclerview)

        //Set recyclerview
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        livres = this.intent.getParcelableArrayListExtra("LIVRESUTIL")
        dateScan = this.intent.extras!!.get("DATESCAN") as MutableList<Date>?
    }

    override fun onStart() {
        super.onStart()

        runBlocking {
            recyclerView.adapter = PossessionLivreAdaptateur(livres, dateScan)
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