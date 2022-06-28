package com.belkanoid.guessbolger.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.belkanoid.guessbolger.R

class MainActivity : AppCompatActivity() {
    private lateinit var playButton: Button
    private lateinit var settingsButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playButton = findViewById(R.id.main_menu_play_btn)
        settingsButton = findViewById(R.id.main_menu_settings_btn)


        playButton.setOnClickListener{
            val gameIntent = GameActivity.newIntent(applicationContext)
            startActivity(gameIntent)
        }
    }
}