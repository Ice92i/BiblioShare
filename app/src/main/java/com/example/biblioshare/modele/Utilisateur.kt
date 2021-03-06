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
    var utilisateurDocumentID : String,
    var UID : String,
    val dateReception : Date,
    var LatLocation : Double,
    var LonLocation : Double

    ) : Parcelable {
    constructor() : this("",
        "",
        "",
        "",
        "",
        "",
        Date(0),
        0.0,
        0.0)
    }


@Parcelize
class UserMessage(
    val uid: String,
    val username: String
    ) : Parcelable {
    constructor() : this("", "")
}