package com.example.biblioshare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uqac.ppm.biblioshare.model.Livre
import kotlinx.android.synthetic.main.view_livre.view.*

//Utiliser la classe pour la liste des livres (recherche) + détails (post recherche et post scan)

class LivreAdaptateur(val livres: List<Livre>) :
    RecyclerView.Adapter<LivreAdaptateur.LivreViewHolder>() {

    class LivreViewHolder(val livreView: View) : RecyclerView.ViewHolder(livreView)


    override fun getItemCount() = livres.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivreViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View =
            inflater.inflate(R.layout.view_livre, parent, false)
        return LivreViewHolder(view)
    }

    override fun onBindViewHolder(holder: LivreViewHolder, position: Int) {
        val livre = livres[position]
        holder.livreView.livre_titre_textview.text =
            livre.titre
        holder.livreView.livre_auteur_textview.text =
            livre.auteur
//        holder.livreView.distance_livre_textview.text =
//            livre.distance
    }


}