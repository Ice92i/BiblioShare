package com.example.biblioshare

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

data class Livre (
    val titre: String? = "",
    val isbn: String? = "",
    val auteur: String? = "",
    val image: String? = ""
)

class FirebaseConnection {

    companion object Singleton {
        val databaseRef = FirebaseFirestore.getInstance()
        val livreList: Vector<Livre> = Vector()

        fun readFireStoreData(){
            databaseRef.collection("livres")
                .get()
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        for(livre in it.result!!){
                            val newLivre: Livre = Livre(livre.data.getValue("Titre").toString(),
                                livre.data.getValue("ISBN").toString(),
                                livre.data.getValue("Auteur").toString(),
                                livre.data.getValue("Image_du_livre").toString()
                            )
                            livreList.add(newLivre)
                            Log.e("ok",newLivre.auteur!!)
                        }
                    }
                }
        }
    }
}