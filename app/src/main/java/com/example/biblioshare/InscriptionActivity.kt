package com.example.biblioshare


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.uqac.ppm.biblioshare.model.Utilisateur
import kotlinx.android.synthetic.main.activity_inscription.*

private const val TAG = "InscriptionActivity"

class InscriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscription)
        bouton_inscription.setOnClickListener{
            val nom = inscription_nom_edittext.text.toString()
            Log.d(TAG,"Nom : ${nom.toUpperCase()}")
            val prenom = inscription_prenom_edittext.text.toString()
            Log.d(TAG,"Prénom : $prenom")
            val email = inscription_email_edittext.text.toString()
            Log.d(TAG,"Email : $email")
            val motdepasse = inscription_mdp_edittext.text.toString()
            Log.d(TAG,"MDP : $motdepasse")
            finish()

            //val utilisateurs = Utilisateur.all()
        }
    }
}