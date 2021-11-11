package com.example.biblioshare.firebase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CallFirebase {
    val db = Firebase.firestore

    fun setLocation(idUser: String, long : String, lat: String){
        val docData = hashMapOf(
            "long" to long,
            "lat" to lat,
        )

        db.collection("location")
            .document(idUser)
            .set(docData)
            .addOnSuccessListener { Log.d("TAG", "Location successfully written!") }
            .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }
    }

    fun updateLocation(idUser: String, long : String, lat: String){
        val docData = mapOf(
            "long" to long,
            "lat" to lat,
        )
        db.collection("location")
            .document(idUser)
            .update(docData)
            .addOnSuccessListener { Log.d("TAG", "Location successfully written!") }
            .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }
    }

}