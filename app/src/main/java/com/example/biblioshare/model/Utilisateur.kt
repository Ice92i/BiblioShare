package com.uqac.ppm.biblioshare.model

data class Utilisateur(
    val nom: String,
    val prenom: String,
    val email: String,
    val motdepasse: Int,
    val active: Boolean
)