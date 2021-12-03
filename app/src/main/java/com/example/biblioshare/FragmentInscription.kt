package com.example.biblioshare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_inscription.*


class FragmentInscription : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // val rootView = inflater.inflate(R.layout.fragment_inscription, container, false)

        // val fragmentName = "Sign In Fragment"

        // rootView.fragment_name.text = fragmentName

        return inflater.inflate(R.layout.fragment_inscription, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inscription_bouton.setOnClickListener {
            val nom = inscription_nom_edittext.text.toString()
            val prenom = inscription_prenom_edittext.text.toString()
            val pseudo = inscription_pseudo_edittext.text.toString()
            val email = inscription_email_edittext.text.toString()
            val motdepasse = inscription_mdp_edittext.text.toString()
            val firebaseName = "${nom.uppercase()} $prenom"

            (activity as GestionActivity).createAccount(email,motdepasse,firebaseName)
        }
    }
}