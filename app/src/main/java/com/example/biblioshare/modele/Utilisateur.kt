package com.example.biblioshare.modele

data class Utilisateur(
    val nom: String,
    val prenom: String,
    val email: String,
    val motdepasse: Int,
    val active: Boolean
)