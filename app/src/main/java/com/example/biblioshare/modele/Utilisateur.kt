package com.example.biblioshare.modele

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Utilisateur(
    val nom: String,
    val prenom: String,
    val email: String,
    val motdepasse: Int,
    val active: Boolean
) : Parcelable