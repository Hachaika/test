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
    private var eventId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val binding = ActivityEditEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        EdgeToEdgeHelper.enableEdgeToEdge(findViewById(android.R.id.content))

        eventId = intent.getLongExtra("EVENT_ID", -1)

        binding.toolbar.menu.findItem(R.id.save_event).setOnMenuItemClickListener {
            val content = binding.content.text?.toString().orEmpty()

            if (content.isNotBlank()) {
                val resultIntent = Intent().apply {
                    putExtra("EVENT_ID", eventId)
                    putExtra(Intent.EXTRA_TEXT, content)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, R.string.text_is_empty, Toast.LENGTH_SHORT).show()
            }

            true
        }
    }
}