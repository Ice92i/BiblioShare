package com.example.biblioshare

import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.biblioshare.modele.Livre
import com.example.biblioshare.modele.Utilisateur
import kotlinx.android.synthetic.main.view_livre_recherche.view.*
import com.squareup.picasso.Picasso

class RechercheLivreAdaptateur(
    private val livres : MutableList<Livre>?,
    private val utilisateurs : MutableList<Utilisateur>?,
    private val user : Utilisateur)
    : RecyclerView.Adapter<RechercheLivreAdaptateur.LivreViewHolder>() {

    class LivreViewHolder(val livreView: View) : RecyclerView.ViewHolder(livreView)

    override fun getItemCount() = livres!!.size

    private var locLivre : Location = Location(LocationManager.NETWORK_PROVIDER)
    private var locUser : Location = Location(LocationManager.NETWORK_PROVIDER)// OR GPS_PROVIDER based on the requirement


    private fun calcDistance(util : Utilisateur) : String {
        val distanceKm: String
        locUser.latitude = user.LatLocation
        locUser.longitude = user.LonLocation

        locLivre.latitude = util.LatLocation
        locLivre.longitude = util.LonLocation

        val distanceKM = locUser.distanceTo(locLivre)
        distanceKm = if (distanceKM.div(1000) < 0.0) {
            String.format("%.1f", distanceKM) + " m"
        } else {
            String.format("%.1f", distanceKM.div(1000)) + " km"
        }
        return distanceKm
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivreViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_livre_recherche, parent, false)
        return LivreViewHolder(view)
    }

    override fun onBindViewHolder(holder: LivreViewHolder, position: Int) {
        val livre = livres!![position]
        val utilisateur = utilisateurs!![position]
        holder.livreView.livre_titre_textview.text = livre.Titre
        holder.livreView.livre_auteur_textview.text = livre.Auteur
        val distance = calcDistance(utilisateur)
        holder.livreView.livre_distance_textview.text = distance

        Picasso
            .get()
            .load(livre.Image_du_livre.toString())
            .into(holder.livreView.livre_couverture_imageview)

        holder.livreView.setOnClickListener {
            val intent = Intent(it.context, RechercheDetailActivity::class.java)
            intent.putExtra("LIVRE", livre)
            intent.putExtra("UTILISATEUR", utilisateur)
            intent.putExtra("DISTANCE", distance)
            it.context.startActivity(intent)
        }
    }


}