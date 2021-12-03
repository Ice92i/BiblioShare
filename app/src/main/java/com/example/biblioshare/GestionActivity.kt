package com.example.biblioshare

// TODO :Ajouter pseudo à fireB

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.example.biblioshare.firebase.CallFirebase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_gestion.*

private const val TAG = "GestionActivity"

class GestionActivity  : AppCompatActivity() {

    private lateinit var fusedLocationClient : FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Tabs Customization
        tab_layout.setSelectedTabIndicatorColor(Color.WHITE)
        tab_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_warm_grey))
        tab_layout.tabTextColors = ContextCompat.getColorStateList(this, android.R.color.white)

        // Set different Text Color for Tabs for when are selected or not
        //tab_layout.setTabTextColors(R.color.normalTabTextColor, R.color.selectedTabTextColor)

        // Number Of Tabs
        val numberOfTabs = 2

        // Set Tabs in the center
        //tab_layout.tabGravity = TabLayout.GRAVITY_CENTER

        // Show all Tabs in screen
        tab_layout.tabMode = TabLayout.MODE_FIXED

        // Scroll to see all Tabs
        //tab_layout.tabMode = TabLayout.MODE_SCROLLABLE

        // Set Tab icons next to the text, instead above the text
        tab_layout.isInlineLabel = true

        // Set the ViewPager Adapter
        val adapter = TabsPagerAdapter(supportFragmentManager, lifecycle, numberOfTabs)
        tabs_viewpager.adapter = adapter

        // Enable Swipe
        tabs_viewpager.isUserInputEnabled = true

        // Link the TabLayout and the ViewPager2 together and Set Text & Icons
        TabLayoutMediator(tab_layout, tabs_viewpager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "S'inscrire"
                    tab.setIcon(R.drawable.sign_in)
                }
                1 -> {
                    tab.text = "Se connecter"
                    tab.setIcon(R.drawable.login)

                }
            }
            // Change color of the icons
            tab.icon?.colorFilter =
                BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    Color.WHITE,
                    BlendModeCompat.SRC_ATOP
                )
        }.attach()

        setCustomTabTitles()
/*

        inscription_bouton.setOnClickListener {
            val nom = inscription_nom_edittext.text.toString()
            val prenom = inscription_prenom_edittext.text.toString()
            val pseudo = inscription_pseudo_edittext.text.toString()
            val email = inscription_email_edittext.text.toString()
            val motdepasse = inscription_mdp_edittext.text.toString()
            val firebaseName = "${nom.uppercase()} $prenom"

            createAccount(email,motdepasse,firebaseName)
        }



        connexion_bouton.setOnClickListener {
            val email = connexion_email_edittext.text.toString()
            val motdepasse = connexion_mdp_edittext.text.toString()
            signIn(email,motdepasse)
        }

 */

    }

    internal fun createAccount(email: String, password: String, name: String) {
        if (email == "" || password == "" || name == ""){
            Toast.makeText(baseContext, "Name/Email/Password is empty.",
                Toast.LENGTH_SHORT).show()
        }
        else {
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
                        }
                        val intent = Intent(this, AccueilActivity::class.java)
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

    internal fun signIn(email: String, password: String) {
        if (email == "" || password == ""){
            Toast.makeText(baseContext, "Email or Password is empty.",
                Toast.LENGTH_SHORT).show()
        }
        else {
            Firebase.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        if (ActivityCompat.checkSelfPermission(
                                this,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                this,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
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
                        }




                        val intent = Intent(this, AccueilActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.w("TAG", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun setCustomTabTitles() {
        val vg = tab_layout.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount

        for (j in 0 until tabsCount) {
            val vgTab = vg.getChildAt(j) as ViewGroup

            val tabChildCount = vgTab.childCount

            for (i in 0 until tabChildCount) {
                val tabViewChild = vgTab.getChildAt(i)
                if (tabViewChild is TextView) {

                    // Change Font and Size
                    tabViewChild.typeface = Typeface.DEFAULT_BOLD
//                    val font = ResourcesCompat.getFont(this, R.font.myFont)
//                    tabViewChild.typeface = font
//                    tabViewChild.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f)
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
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
