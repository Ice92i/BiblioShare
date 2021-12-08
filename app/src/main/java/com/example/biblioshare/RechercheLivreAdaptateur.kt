package com.example.biblioshare

import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.biblioshare.modele.Livre
import com.example.biblioshare.modele.Utilisateur
import kotlinx.android.synthetic.main.view_livre_recherche.view.*
import kotlin.math.round

//Utiliser la classe pour la liste des livres (recherche) + détails (post recherche et post scan)

class RechercheLivreAdaptateur(val livres : MutableList<Livre>?,
                               val utilisateurs : MutableList<Utilisateur>?,
                               val user : Utilisateur)
    : RecyclerView.Adapter<RechercheLivreAdaptateur.LivreViewHolder>() {

    class LivreViewHolder(val livreView: View) : RecyclerView.ViewHolder(livreView)

    override fun getItemCount() = livres!!.size


    var locLivre : Location = Location(LocationManager.NETWORK_PROVIDER)
    private var locUser : Location = Location(LocationManager.NETWORK_PROVIDER)// OR GPS_PROVIDER based on the requirement


    fun calcDistance(util : Utilisateur) : String {
        var distanceKm = "hihi"
        locUser.latitude = user.LatLocation
        locUser.longitude = user.LonLocation

        locLivre.latitude = util.LatLocation
        locLivre.longitude = util.LonLocation

        val distanceKM = locUser.distanceTo(locLivre)
        Log.d("DISTANCE DEBUG", "$distanceKM m")
        if (distanceKM.div(1000) < 0.0) {
            distanceKm = String.format("%.1f", distanceKM) + " m"
        } else {

            distanceKm = String.format("%.1f", distanceKM.div(1000)) + " km"
        }
        return distanceKm
    }

    //OK
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivreViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_livre_recherche, parent, false)
        return LivreViewHolder(view)
    }

    override fun onBindViewHolder(holder: LivreViewHolder, position: Int) {
        val livre = livres!![position]
        val utilisateur = utilisateurs!![position]
        holder.livreView.livre_titre_textview.text =
            livre.Titre
        holder.livreView.livre_auteur_textview.text =
            livre.Auteur

        /*
        holder.livreView.livre_auteur_textview.text =
            livre.Image_du_livre

*/
        val distance = calcDistance(utilisateur)
        holder.livreView.livre_distance_textview.text =
            distance

        //holder.livreView.livre_couverture_imageview.setImageResource(0)

        holder.livreView.setOnClickListener {
            val intent = Intent(it.context, RechercheDetailActivity::class.java)
            intent.putExtra("LIVRE", livre)
            intent.putExtra("UTILISATEUR", utilisateur)
            intent.putExtra("DISTANCE", distance)
            it.context.startActivity(intent)
        }
    }


}