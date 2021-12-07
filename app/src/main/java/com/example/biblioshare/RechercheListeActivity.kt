package com.example.biblioshare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.biblioshare.modele.Livre
import com.example.biblioshare.modele.Utilisateur
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_recherche_liste.*
import kotlinx.coroutines.runBlocking

class RechercheListeActivity : AppCompatActivity() {

    private var livres: MutableList<Livre>? = ArrayList()
    private var utilisateurs : MutableList<Utilisateur>? = ArrayList()
    private val db = FirebaseFirestore.getInstance()
    private lateinit var recyclerView : RecyclerView
    lateinit var user : Utilisateur

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recherche_liste)

        recyclerView = findViewById(R.id.livres_recherche_recyclerview)

        //Set recyclerview
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Get livre list
        livres  = this.intent.getParcelableArrayListExtra("LIVRES")

        // Get livre list
        utilisateurs  = this.intent.getParcelableArrayListExtra("UTILISATEURS")

        // Get current user
        user = this.intent.extras!!.get("USER") as Utilisateur

        Log.d("RECHERCHE LIST DEBUG UTILISATEURS : ", utilisateurs!!.size.toString())
        Log.d("RECHERCHE LIST DEBUG LIVRES", utilisateurs!!.size.toString())
        Log.d("RECHERCHE LIST DEBUG USER", user.toString())


    }

    override fun onStart() {
        super.onStart()

        runBlocking {
            recyclerView.adapter = RechercheLivreAdaptateur(livres, utilisateurs, user)
        }



    }

    private fun getBookUserDetails()
    {
        for (livre in livres!!) {

            db.collection("livres")
                .document(livre.livreDocumentID)
                .collection("utilisateurs")
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->

                    if (firebaseFirestoreException != null) {
                        Log.e("FIRESTORE", "User listener error.", firebaseFirestoreException)
                        return@addSnapshotListener
                    }

                    querySnapshot!!.documents.forEach {
                        val ref = it.reference
                        val util = (it.toObject(Utilisateur::class.java)!!)
                        util.utilisateurDocumentID = ref.id
                        utilisateurs!!.add(util)

                        Log.d("RECHERCHE LISTE", utilisateurs!!.size.toString())
                        //getUserLocation()
                    }
                }
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