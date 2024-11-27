package com.eltex.androidschool.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.eltex.androidschool.R
import com.eltex.androidschool.databinding.ActivityNewEventBinding
import com.eltex.androidschool.ui.EdgeToEdgeHelper

class NewEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val binding = ActivityNewEventBinding.inflate(layoutInflater)

        setContentView(binding.root)

        EdgeToEdgeHelper.enableEdgeToEdge(findViewById(android.R.id.content))

        val sharedText = intent.getStringExtra("SHARED_TEXT")
        if (!sharedText.isNullOrBlank()) {
            binding.content.setText(sharedText)
        }

        binding.toolbar.menu.findItem(R.id.save_event).setOnMenuItemClickListener {
            val content = binding.content.text?.toString().orEmpty()

            if (content.isNotBlank()) {
                setResult(RESULT_OK, Intent().putExtra(Intent.EXTRA_TEXT, content))
                finish()
            } else {
                Toast.makeText(this, R.string.text_is_empty, Toast.LENGTH_SHORT).show()
            }

            true
        }
    }
}
