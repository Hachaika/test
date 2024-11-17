package com.eltex.androidschool.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.eltex.androidschool.R
import com.eltex.androidschool.databinding.ActivityEditEventBinding
import com.eltex.androidschool.ui.EdgeToEdgeHelper

class EditEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditEventBinding
    private var eventId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityEditEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        EdgeToEdgeHelper.enableEdgeToEdge(findViewById(android.R.id.content))

        eventId = intent.getLongExtra("EVENT_ID", -1)
        val currentContent = intent.getStringExtra("EVENT_CONTENT") ?: ""
        binding.content.setText(currentContent)

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.save_event -> {
                    val content = binding.content.text?.toString ().orEmpty()

                    if (content.isNotBlank()) {
                        val resultIntent = Intent().apply {
                            putExtra("EVENT_ID", eventId)
                            putExtra("NEW_EVENT_CONTENT", content)
                        }
                        setResult(RESULT_OK, resultIntent)
                        finish()
                    } else {
                        Toast.makeText(this, R.string.text_is_empty, Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                else -> false
            }
        }
    }
}