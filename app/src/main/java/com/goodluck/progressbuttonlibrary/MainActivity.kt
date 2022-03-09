package com.goodluck.progressbuttonlibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.goodluck.progress_button.ProgressButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: ProgressButton = findViewById(R.id.progress_button)

        button.setOnClickListener {
            button.setLoading()
        }

        button.buttonText = "Botão"
    }
}