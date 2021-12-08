package com.example.biblioshare

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.biblioshare.modele.Livre
import com.example.biblioshare.modele.Utilisateur
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_recherche.*
import kotlinx.android.synthetic.main.activity_recherche_liste.*
import kotlinx.android.synthetic.main.view_livre_recherche.view.*
import java.util.*
import kotlin.collections.ArrayList

class RechercheActivity : AppCompatActivity() {

    private var livres : MutableList<Livre> = ArrayList()
    private var utilisateurs : MutableList<Utilisateur> = ArrayList()
    private val db = FirebaseFirestore.getInstance()
    private var user : Utilisateur = Utilisateur("",
        "",
        "",
        "",
        "",
        "",
        Date(0),
        0.0,
        0.0)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recherche)

        recherche_bouton.setOnClickListener {
            utilisateurs.clear()
            livres.clear()
            val recherche = recherche_livre_edittext.text.toString()
            readFireStoreData(recherche)
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

    private fun readFireStoreData(rechercheET : String) {
        db.collection("livres")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (livreDoc in it.result) {
                        if (livreDoc.data.getValue("Titre").toString() == rechercheET
                            || livreDoc.data.getValue("Auteur").toString() == rechercheET
                            || livreDoc.data.getValue("ISBN").toString() == rechercheET)
                        {
                            val livre = Livre(livreDoc.data.getValue("Titre").toString(),
                                livreDoc.data.getValue("Auteur").toString(),
                                livreDoc.data.getValue("ISBN").toString(),
                                livreDoc.data.getValue("ImageLien").toString(),
                                livreDoc.id)

                            livres.add(livre)
                            getBookUserDetails(livreDoc.id)

                        }
                    }
                }
            }

    }

    private fun getBookUserDetails(livreDocID : String) {
            db.collection("livres")
                .document(livreDocID)
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
                        utilisateurs.add(util)
                    }
                    getBookLoc()
                    getUserLoc()
                    displayResults()
                }
        }

    private fun getBookLoc() {
        for (utilisateur in utilisateurs) {

            db.collection("location")
                .get()
                .addOnCompleteListener{
                    if (it.isSuccessful) {
                        for (locDoc in it.result) {
                            if (locDoc.id == utilisateur.UID)
                            {
                                val latLoc = locDoc.data.getValue("lat").toString()
                                val latLocDouble = latLoc.toDouble()
                                val longLoc = locDoc.data.getValue("long").toString()
                                val longLocDouble = longLoc.toDouble()

                                utilisateur.LatLocation = latLocDouble
                                utilisateur.LonLocation = longLocDouble
                            }
                        }
                    }
                }

        }
    }

    private fun getUserLoc(){
        user.UID = Firebase.auth.currentUser!!.uid

        db.collection("location")
            .get()
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    for (locDoc in it.result) {
                        if (locDoc.id == user.UID)
                        {
                            val latLoc = locDoc.data.getValue("lat").toString()
                            val latLocDouble = latLoc.toDouble()
                            val longLoc = locDoc.data.getValue("long").toString()
                            val longLocDouble = longLoc.toDouble()

                            user.LatLocation = latLocDouble
                            user.LonLocation = longLocDouble
                        }
                    }
                }
            }
    }

    private fun displayResults() {

        Handler().postDelayed({
            if(livres.size != 0) {
                val intent = Intent(this, RechercheListeActivity::class.java)
                intent.putParcelableArrayListExtra("LIVRES", ArrayList(livres))
                intent.putParcelableArrayListExtra("UTILISATEURS", ArrayList(utilisateurs))
                intent.putExtra("USER", user)
                startActivity(intent)

            } else {
                Toast.makeText(baseContext, "Aucun livre trouvé, essayez encore !",
                    Toast.LENGTH_SHORT).show()
            }
        }, 500)
    }
}