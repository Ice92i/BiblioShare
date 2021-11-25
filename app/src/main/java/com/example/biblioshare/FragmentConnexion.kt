package com.example.biblioshare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class FragmentConnexion : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // val rootView = inflater.inflate(R.layout.fragment_connexion, container, false)

        // val fragmentName = "Log In Fragment"

        // rootView.fragment_name.text = fragmentName

        return inflater.inflate(R.layout.fragment_connexion, container, false)
    }

}