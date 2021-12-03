package com.example.biblioshare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_connexion.*


class FragmentConnexion : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // val rootView = inflater.inflate(R.layout.fragment_connexion, container, false)

        // val fragmentName = "Log In Fragment"

        // rootView.fragment_name.text = fragmentName

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connexion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        connexion_bouton.setOnClickListener {
            val email = connexion_email_edittext.text.toString()
            val motdepasse = connexion_mdp_edittext.text.toString()
            (activity as GestionActivity).signIn(email,motdepasse)
        }
    }
/*
    interface Listener {
        fun onLoginButtonClicked()
    }

    private var listener: Listener? = null

    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    // When drawer item selected, do something like
    //
    // if (listener != null)
    // {
    //    listener.onDrawerItemSelected();
    // }

 */
}