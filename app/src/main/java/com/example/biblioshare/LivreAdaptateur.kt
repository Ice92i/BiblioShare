package com.example.biblioshare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uqac.ppm.biblioshare.model.Livre
import kotlinx.android.synthetic.main.livre_view.view.*

//Utiliser la classe pou la liste des livres (recherche) + détails (post recherche et post scan)

class LivreAdaptateur (val livres: List<Livre>) : RecyclerView.Adapter<LivreAdaptateur.LivreViewHolder>() {

    class LivreViewHolder(val livreView : View) : RecyclerView.ViewHolder(livreView)


    override fun getItemCount()  = livres.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivreViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view : View =
            inflater.inflate(R.layout.livre_view, parent, false)
        return LivreViewHolder(view)
    }

    override fun onBindViewHolder(holder: LivreViewHolder, position: Int) {
        val livre = livres[position]
        holder.livreView.titre_livre_textview.text =
            livre.titre
        holder.livreView.auteur_livre_textview.text =
            livre.auteur
//        holder.livreView.distance_livre_textview.text =
//            livre.distance
    }




}