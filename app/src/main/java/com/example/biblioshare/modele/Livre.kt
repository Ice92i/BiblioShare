package com.example.biblioshare.modele

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.lang.reflect.Constructor
import java.util.*

@Parcelize
data class Livre(
    val Titre: String,
    val Auteur: String,
    val ISBN: String,
    val Image_du_livre : String?,
    val livreDocumentID : String
//    val categorie: String,
//    val dateScan : Date

) : Parcelable