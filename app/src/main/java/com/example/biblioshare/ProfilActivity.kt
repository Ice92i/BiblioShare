package com.example.biblioshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profil.*

class ProfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        profil_accueil_button.setOnClickListener {
            val intent = Intent(this, AccueilActivity::class.java)
            startActivity(intent)

        }
        supression_profile_button.setOnClickListener {
            val user = Firebase.auth.currentUser!!

            user.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "User account deleted.")
                        val intent = Intent(this, GestionActivity::class.java)
                        startActivity(intent)
                    }
                }
        }
    }

}