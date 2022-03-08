package com.goodluck.progressbuttonlibrary

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.goodluck.progress_button.ProgressButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: ProgressButton = findViewById(R.id.progress_button)

        var count : Int = 0

        button.setOnClickListener {
            when(count) {
                0 -> {
                    button.setError()
                    count++
                }
                1 -> {
                    button.setIdle()
                    count++
                }
                2 -> {
                    button.setError()
                    count++
                }
                3 -> {
                    button.setIdle()
                    count++
                }
                4 -> button.setLoading()
            }
        }
    }
}