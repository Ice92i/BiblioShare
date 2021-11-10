package com.example.biblioshare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uqac.ppm.biblioshare.model.Livre
import kotlinx.android.synthetic.main.view_livre_recherche.view.*

//Utiliser la classe pour la liste des livres (recherche) + détails (post recherche et post scan)

class RechercheLivreAdaptateur(val livres: List<Livre>) :
    RecyclerView.Adapter<RechercheLivreAdaptateur.LivreViewHolder>() {

    class LivreViewHolder(val livreView: View) : RecyclerView.ViewHolder(livreView)


    override fun getItemCount() = livres.size
    var distance = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivreViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View =
            inflater.inflate(R.layout.view_livre_recherche, parent, false)
        return LivreViewHolder(view)
    }

    override fun onBindViewHolder(holder: LivreViewHolder, position: Int) {
        val livre = livres[position]
        holder.livreView.livre_titre_textview.text =
            livre.titre
        holder.livreView.livre_auteur_textview.text =
            livre.auteur
        holder.livreView.livre_distance_textview.text =
            distance.toString()
    }


}