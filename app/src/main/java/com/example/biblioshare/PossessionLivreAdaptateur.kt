package com.example.biblioshare

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.biblioshare.modele.Livre
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_possession_detail.view.*
import kotlinx.android.synthetic.main.activity_possession_detail.view.livre_auteur_textview
import kotlinx.android.synthetic.main.activity_possession_detail.view.livre_titre_textview
import kotlinx.android.synthetic.main.view_livre_recherche.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.view_livre_recherche.view.livre_couverture_imageview as livre_couverture_imageview1

//Utiliser la classe pour la liste des livres (possession) + détails

class PossessionLivreAdaptateur(
    private val livres : MutableList<Livre>?,
    private val dateScan : MutableList<Date>?)
    : RecyclerView.Adapter<PossessionLivreAdaptateur.LivreViewHolder>() {

    class LivreViewHolder(val livreView: View) : RecyclerView.ViewHolder(livreView)

    override fun getItemCount() = livres!!.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivreViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View =
            inflater.inflate(R.layout.view_livre_possession, parent, false)
        return LivreViewHolder(view)
    }

    override fun onBindViewHolder(holder: LivreViewHolder, position: Int) {
        val livre = livres!![position]
        val date = dateScan!![position]
        holder.livreView.livre_titre_textview.text =
            livre.Titre
        holder.livreView.livre_auteur_textview.text =
            livre.Auteur

        Picasso
            .get()
            .load(livre.Image_du_livre.toString())
            .into(holder.livreView.livre_couverture_imageview)

        Log.d("DATE", date.toString())
        val NouveauFormat = SimpleDateFormat("dd/MM/yyyy").format(date)
        Log.d("NOUVEAU FORMAT", NouveauFormat.toString())
        //holder.livreView.livre_date_textview.text = ""

        holder.livreView.setOnClickListener {
            val intent = Intent(it.context, PossessionDetailActivity::class.java)
            intent.putExtra("LIVRE", livre)
            intent.putExtra("DATE", date)
            it.context.startActivity(intent)
        }

    }


}