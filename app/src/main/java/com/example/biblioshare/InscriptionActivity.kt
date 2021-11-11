package com.example.biblioshare


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.biblioshare.firebase.CallFirebase
import com.example.biblioshare.utils.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_inscription.*
import java.util.*

private const val TAG = "InscriptionActivity"

class InscriptionActivity : AppCompatActivity() {

    lateinit var fusedLocationClient : FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscription)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        inscription_bouton.setOnClickListener {
            val nom = inscription_nom_edittext.text.toString()
            val prenom = inscription_prenom_edittext.text.toString()
            val email = inscription_email_edittext.text.toString()
            val motdepasse = inscription_mdp_edittext.text.toString()
            val firebaseName = "${nom.uppercase()} $prenom"

            createAccount(email,motdepasse,firebaseName)
        }
    }

    private fun createAccount(email: String, password: String,name: String) {
        if (email == "" || password == "" || name == ""){
            Toast.makeText(baseContext, "Name/Email/Password is empty.",
                Toast.LENGTH_SHORT).show()
        }else {
            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "createUserWithEmail:success")
                                val profileUpdates =
                                    UserProfileChangeRequest.Builder().setDisplayName(name).build()
                                Firebase.auth.currentUser?.updateProfile(profileUpdates)
                        if (ActivityCompat.checkSelfPermission(
                                this,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                this,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                        }
                        fusedLocationClient.lastLocation
                                .addOnSuccessListener { curlocation ->
                                    Log.d(
                                        "TAG",
                                        "getLastKnownLocation: ${curlocation.latitude} ${curlocation.longitude}"
                                    )
                                    Log.d(TAG, "createAccount: TEST")
                                    Firebase.auth.currentUser?.let { CallFirebase().setLocation(it.uid,
                                        curlocation.longitude.toString(), curlocation.latitude.toString()) }
                                }


                            val intent = Intent(this, ConnexionActivity::class.java)
                            startActivity(intent)

                    } else {
                        Log.w("TAG", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_retour, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.retour_action -> {
                this.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}