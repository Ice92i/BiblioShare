package com.example.biblioshare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.biblioshare.modele.UserMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_accueil.*
import kotlinx.android.synthetic.main.activity_profil.*

class AccueilActivity : AppCompatActivity() {

    companion object {
        var currentUser: UserMessage? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil)

        val userFirstName = Firebase.auth.currentUser?.displayName

        val welcomeTextView : TextView = findViewById(R.id.accueil_welcome_textview)
        val welcomeText = "Bienvenue $userFirstName!"
        welcomeTextView.text = welcomeText

        fetchCurrentUser()

        accueil_recherche_bouton.setOnClickListener {
            val intent = Intent(this, RechercheActivity::class.java)
            startActivity(intent)
        }

        accueil_scan_bouton.setOnClickListener {
            val intent = Intent(this, BarcodeScanningActivity::class.java)
            startActivity(intent)

        }
        acceuil_profile_bouton.setOnClickListener {
            val intent = Intent(this, ProfilActivity::class.java)
            startActivity(intent)

        }
    }

    private fun fetchCurrentUser() {
        val db = FirebaseFirestore.getInstance()
        val uid = FirebaseAuth.getInstance().uid

        if(uid != null) {
            val userDocRef = db.collection("usermessage").document(uid)
            userDocRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("accueil", "DocumentSnapshot data: ${document.data}")
                        currentUser = document.toObject<UserMessage>()
                        Log.d("accueil", "${currentUser?.username}, ${currentUser?.uid}")
                        //dummyConversation()
                    } else {
                        Log.d("accueil", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("accueil", "get failed with ", exception)
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_messages, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.messages_action -> {
                val intent = Intent(this, MessagerieActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}