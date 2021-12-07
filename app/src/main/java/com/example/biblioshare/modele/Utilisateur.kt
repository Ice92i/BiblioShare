package com.example.biblioshare.modele

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Utilisateur(
    val prenom : String,
    val Pseudonyme : String,
    val email : String,
    val motdepasse : String,
//    val active : Boolean,
    var utilisateurDocumentID : String,
    val dateReception : Date


) : Parcelable {
    constructor() : this("", "", "", "", "", Date(0))
}