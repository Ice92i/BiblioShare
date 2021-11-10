package com.example.biblioshare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uqac.ppm.biblioshare.model.Livre
import kotlinx.android.synthetic.main.activity_possession_detail.view.*

//Utiliser la classe pour la liste des livres (possession) + détails

class PossessionLivreAdaptateur(val livres: List<Livre>) :
    RecyclerView.Adapter<PossessionLivreAdaptateur.LivreViewHolder>() {

    class LivreViewHolder(val livreView: View) : RecyclerView.ViewHolder(livreView)


    override fun getItemCount() = livres.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivreViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View =
            inflater.inflate(R.layout.view_livre_possession, parent, false)
        return LivreViewHolder(view)
    }

    override fun onBindViewHolder(holder: LivreViewHolder, position: Int) {
        val livre = livres[position]
        holder.livreView.livre_titre_textview.text =
            livre.titre
        holder.livreView.livre_auteur_textview.text =
            livre.auteur
        holder.livreView.livre_date_textview.text =
            livre.dateScan.toString()
    }


}