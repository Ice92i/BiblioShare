package com.example.biblioshare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.biblioshare.modele.UserMessage
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_inscription.*


class FragmentInscription : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_inscription, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inscription_bouton.setOnClickListener {
            val prenom = inscription_prenom_edittext.text.toString()
            val pseudo = inscription_pseudo_edittext.text.toString()
            val email = inscription_email_edittext.text.toString()
            val motdepasse = inscription_mdp_edittext.text.toString()

            (activity as GestionActivity).createAccount(email,motdepasse,prenom, pseudo)
        }
    }
}