package com.eltex.androidschool.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.eltex.androidschool.R
import com.eltex.androidschool.ui.EdgeToEdgeHelper

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        applyInsets()

    }

    private fun applyInsets() {
        EdgeToEdgeHelper.enableEdgeToEdge(findViewById(R.id.container))
    }
}