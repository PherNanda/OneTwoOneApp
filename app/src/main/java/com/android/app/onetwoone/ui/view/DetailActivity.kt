package com.android.app.onetwoone.ui.view

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.app.onetwoone.R
import com.facebook.drawee.view.SimpleDraweeView
import org.koin.android.ext.android.inject

class DetailActivity : AppCompatActivity() {

    private val preferences: SharedPreferences by inject()
    private lateinit var titleBeer: TextView
    private lateinit var descriptionBeer: TextView
    private lateinit var taglineBeer: TextView
    private lateinit var abvBeer: TextView
    private lateinit var ibuBeer: TextView
    private lateinit var imageBeer: SimpleDraweeView
    private lateinit var backArrow: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        bind()
    }

    private fun bind() {
        titleBeer = findViewById(R.id.title_beer)
        val name = preferences.getString("beerName", "").toString()
        titleBeer.text = name

        taglineBeer = findViewById(R.id.beer_tagline)
        val tagline = preferences.getString("beerTagline", "").toString()
        taglineBeer.text = tagline

        imageBeer = findViewById(R.id.beer_image)
        val url = preferences.getString("beerImage", "").toString()
        imageBeer.setImageURI(url)

        descriptionBeer = findViewById(R.id.beer_description)
        val description = preferences.getString("beerDescription", "").toString()
        descriptionBeer.text = description

        abvBeer = findViewById(R.id.beer_abv)
        val abv = preferences.getFloat("beerABV", 0f).toString()
        abvBeer.text = abv

        ibuBeer = findViewById(R.id.beer_ibu)
        val ibu = preferences.getFloat("beerIBU", 0f).toString()
        ibuBeer.text = ibu

        backArrow = findViewById(R.id.back_arrow)
        backArrow.setOnClickListener {
            onBackPressed()
        }
    }
}