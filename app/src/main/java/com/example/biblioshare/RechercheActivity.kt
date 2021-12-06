package com.example.biblioshare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.biblioshare.modele.Livre
import com.example.biblioshare.modele.Utilisateur
import com.google.api.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_recherche.*
import kotlinx.android.synthetic.main.activity_recherche.livres_recherche_recyclerview
import kotlinx.android.synthetic.main.activity_recherche_liste.*
import kotlinx.android.synthetic.main.view_livre_recherche.view.*

private const val TAG = "RechercheActivity"



class RechercheActivity : AppCompatActivity() {

 //   lateinit var champrecherche : EditText
   // lateinit var boutonrecherche : Button
   // lateinit var recyclerView: RecyclerView
  //  lateinit var livres: MutableList<Livre>
    lateinit var utilisateurs: MutableList<Utilisateur>
    val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recherche)

        /*
        //champrecherche = recherche_livre_edittext
        //boutonrecherche = recherche_bouton
       // recyclerView = livres_recherche_recyclerview

      //  livres_recherche_recyclerview.layoutManager =
        //    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recherche_bouton.setOnClickListener {


            @Override
            public void onClick(View v) {
                    if (MCSearchET.getText().length() >= 1){
                        query = firebaseFirestore.collection("Files/MindfulnessFeatures/Content")
                            .orderBy("ContentName")
                            .startAt(MCSearchET.getText().toString())
                            .endAt(MCSearchET.getText().toString() + "\uf8ff");
                        options = new FirestorePagingOptions.Builder<Model>().setQuery(query, config,  Model.class).build();
                        adapter.updateOptions(options);
                    }
            }
        });
        */


    recherche_bouton.setOnClickListener {

        val recherche = recherche_livre_edittext.text.toString()
        Log.d(TAG, "Recherche : $recherche")

        readFireStoreData()
/*
        if(recherche.length() >=1){
            query = db.collection("livres")
                .orderBy("Titre")
                .startAt(recherche)
                .endAt(recherche + "\uf8ff")
        }

            firebaseRechercheLivre();



            val firebaseSearchQuery: Query =
                mUserDatabase.orderByChild("name").startAt(recherche).endAt(recherche + "\uf8ff")


             // afficher les entrées (pour test seulement)

            // Comparer la recherche à la bdd des livres, si résultat :

            if (true) {
                val intent = Intent(this, RechercheListeActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, NotFoundActivity::class.java)
                startActivity(intent)
            }
            finish()
 */

        }



    }
/*
    private fun firebaseRechercheLivre() {
        FirestoreRecyclerAdapter<Livre, LivreViewHolder> firebaseRecyclerAdapter = new FirestoreRecyclerAdapter<Livre, LivreViewHolder>();
        TODO("Not yet implemented")
    }

*/

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

    //View holder class

    fun readFireStoreData() {

        db.collection("livres")
            .get()
            .addOnCompleteListener {

                Log.d(TAG, "Connected")

                val resultLivre = StringBuffer()
                val resultUtilisateur = StringBuffer()
                var utilId = ""

                if (it.isSuccessful) {
                    for (livre in it.result) {
                        resultLivre.append("  Titre : ")
                            .append(livre.data.getValue("Titre"))
                            .append("  Auteur : ")
                            .append(livre.data.getValue("Auteur"))
                            .append("  ISBN : ")
                            .append(livre.data.getValue("ISBN"))
                            .append("\n\n")

                                
                        livre.collection("utilisateurs")
                            .get().addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d(TAG, "Accès aux utilisateurs")
                                    for (util in task.result) {
                                        resultUtilisateur.append("  Pdeudo : ")
                                            .append(util.data.getValue("Pseudonyme"))
                                            .append("\n\n")

                                        utilId = util.id
                                    }
                                }
/*
                        db.collection("restaurants").document("123").collection("reviews").get()
                            .then(querySnapshot => {
                                querySnapshot.forEach(document => {
                                    console.log(document.id, " => ", document.data());
                                })
                            })

 */
                            }

                        resultLivre.toString()
                        resultUtilisateur.toString()
                        Log.d(TAG, "Id du livre : ${livre.id}")
                        Log.d(TAG, "Recherche : $resultLivre")
                        Log.d(TAG, "Id de l'utilisateur : $utilId")
                        Log.d(TAG, "Utilisateur : $resultUtilisateur")

                    }
                }

            }

    }

    fun addCardsListener(
        context: Context, selectedUser: String, onListen: (List<Utilisateur>) -> Unit
    ): ListenerRegistration {
        return db.collection("livres").document(selectedUser).collection("utilisateurs")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->

                if (firebaseFirestoreException != null) {
                    Log.e("FIRESTORE", "Cards listener error.", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                val utilisateurs = mutableListOf<Utilisateur>()
                querySnapshot!!.documents.forEach {

                    val ref = it.reference
                    val utilDocumentID = ref.id
                    Log.d("FIRESTORE", "cardDocumentID: " + utilDocumentID)

                    // we need to add this ID to our Card item
                    var util = (it.toObject(Utilisateur::class.java)!!)
                    util.utilisateurDocumentID = utilDocumentID

                    utilisateurs.add
                }
                onListen(utilisateurs)
            }
    }
/*
    class LivreViewHolder(val livreView: View) : RecyclerView.ViewHolder(livreView) {


        fun getItemCount() = livres.size
        var distance = 0

        fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivreViewHolder {
            val inflater: LayoutInflater = LayoutInflater.from(parent.context)
            val view: View =
                inflater.inflate(R.layout.view_livre_recherche, parent, false)
            return LivreViewHolder(view)
        }

        fun onBindViewHolder(holder: LivreViewHolder, position: Int) {
            val livre = livres[position]
            holder.livreView.livre_titre_textview.text =
                livre.titre
            holder.livreView.livre_auteur_textview.text =
                livre.auteur
            holder.livreView.livre_distance_textview.text =
                distance.toString()
        }

    }


 */
}

private fun DocumentSnapshot.collection(s: String): Any {

}
