package com.example.biblioshare

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.biblioshare.modele.Livre
import kotlinx.android.synthetic.main.view_livre_recherche.view.*

//Utiliser la classe pour la liste des livres (recherche) + détails (post recherche et post scan)

class RechercheLivreAdaptateur(val livres: List<Livre>) :
    RecyclerView.Adapter<RechercheLivreAdaptateur.LivreViewHolder>() {

    class LivreViewHolder(val livreView: View) : RecyclerView.ViewHolder(livreView)


    override fun getItemCount() = livres.size
    var distance = 0

    //OK
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivreViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_livre_recherche, parent, false)
        return LivreViewHolder(view)
    }

    override fun onBindViewHolder(holder: LivreViewHolder, position: Int) {
        val livre = livres[position]
        holder.livreView.livre_titre_textview.text =
            livre.Titre
        holder.livreView.livre_auteur_textview.text =
            livre.Auteur
        // Attention
        holder.livreView.livre_auteur_textview.text =
            livre.Image_du_livre
        holder.livreView.livre_distance_textview.text =
            distance.toString()
        /*
        holder.livreView.livre_Caterogie_textview.text =
            livre.Categorie
         */
        holder.livreView.livre_couverture_imageview.setImageResource(0)

        holder.livreView.setOnClickListener {
            val intent = Intent(it.context, RechercheDetailActivity::class.java)
            intent.putExtra("UID", livre)
            it.context.startActivity(intent)
        }
    }


}