package com.example.biblioshare

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class NotFoundActivity : AppCompatActivity () {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_not_found)

        // Ajout bouton retour ?

        val imageView: ImageView = findViewById(R.id.not_found_gif)
        imageView.layoutParams.width = 120
        imageView.layoutParams.height = 30

    }

}