package com.example.biblioshare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_connexion.view.*


class FragmentInscription : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_inscription, container, false)

        val fragmentName = "Sign In Fragment"

        rootView.fragment_name.text = fragmentName

        return rootView
    }

}