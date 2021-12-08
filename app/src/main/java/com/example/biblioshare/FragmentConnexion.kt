package com.example.biblioshare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_connexion.*


class FragmentConnexion : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        return inflater.inflate(R.layout.fragment_connexion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        connexion_bouton.setOnClickListener {
            val email = connexion_email_edittext.text.toString()
            val motdepasse = connexion_mdp_edittext.text.toString()
            (activity as GestionActivity).signIn(email,motdepasse)
        }
    }
}