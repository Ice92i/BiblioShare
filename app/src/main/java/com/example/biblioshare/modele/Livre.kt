package com.example.biblioshare.modele

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Livre(
    val titre: String,
    val auteur: String,
    val categorie: String,
    val ISBN: String,
    val dateScan : Date
) : Parcelable