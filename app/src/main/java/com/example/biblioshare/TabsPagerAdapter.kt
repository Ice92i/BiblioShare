package com.example.biblioshare

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle, private var numberOfTabs: Int) : FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                // # Sign in Fragment
                // val bundle = Bundle()
                // bundle.putString("fragmentName", "Sign In Fragment")
                // val signInFragment = FragmentInscription()
                // signInFragment.arguments = bundle
                return FragmentInscription()
            }
            1 -> {
                // # Log in Fragment
                // val bundle = Bundle()
                // bundle.putString("fragmentName", "Log In Fragment")
                // val logInFragment = FragmentConnexion()
                // logInFragment.arguments = bundle
                return FragmentConnexion()
            }
            else -> return FragmentInscription()
        }
    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }

}