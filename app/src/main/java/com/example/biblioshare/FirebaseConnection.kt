package com.example.biblioshare

import android.media.Image
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Livre {
    public val Titre: String? = null
    public val ISBN: String? = null
    public val Auteur: String? = null
    public val Image_Du_Livre: String? = null
}

class FirebaseConnection {

    companion object Singleton {
        val databaseRef = FirebaseDatabase.getInstance().getReference("livres")
        val livreList = arrayListOf<Livre>()
    }

    fun updateData(callback:()->Unit){
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                livreList.clear()
                Log.e("ok","ok")
                for (ds in snapshot.children){
                    val livre = ds.getValue(Livre::class.java)
                    if (livre!=null) {
                        livreList.add(livre)
                    }
                }
                callback()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("Failed to read value.", error.toException())
            }
        })
    }
    fun updateTask(livre: Livre) = databaseRef.child(livre.ISBN!!).setValue(livre)
    fun deleteTask(livre: Livre) = databaseRef.child(livre.ISBN!!).removeValue()
}