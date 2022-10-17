package com.example.myapplication.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_gif.*

class GIFActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gif)

        gif.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}