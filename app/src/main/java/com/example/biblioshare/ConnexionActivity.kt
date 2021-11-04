package com.example.biblioshare

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_inscription.*

private const val TAG = "ConnexionActivity"

class ConnexionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connexion)

        inscription_bouton.setOnClickListener{
           val email = inscription_email_edittext.text.toString()
            Log.d(TAG,"Email : $email")
            val motdepasse = inscription_mdp_edittext.text.toString()
            Log.d(TAG,"MDP : $motdepasse")
            finish()

            //val utilisateurs = Utilisateur.all()
        }
    }
}