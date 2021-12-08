package com.example.biblioshare

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profil.*

class ProfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        editTextMail.setText(Firebase.auth.currentUser?.email)
        editTextPseudo.setText(Firebase.auth.currentUser?.displayName)

        modifier_profile_button.setOnClickListener {
            val user = Firebase.auth.currentUser

            val profileUpdates =
                UserProfileChangeRequest.Builder()
                    .setDisplayName(editTextPseudo.text.toString())
                    .build()
            Firebase.auth.currentUser?.updateProfile(profileUpdates)
            Firebase.auth.currentUser?.updateEmail(editTextMail.text.toString())
            Firebase.auth.currentUser?.updatePassword(editTextMotDePasse.text.toString())

            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "Votre compte a été modifié.")
                    }
                }
        }
        supression_profile_button.setOnClickListener {
            val user = Firebase.auth.currentUser!!

            user.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "Votre compte a été supprimé.")
                        val intent = Intent(this, GestionActivity::class.java)
                        startActivity(intent)
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
