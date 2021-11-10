package com.uqac.ppm.biblioshare.model

import java.util.*

data class Livre(
    val titre: String,
    val auteur: String,
    val ISBN: String,
    val dateScan : Date
)