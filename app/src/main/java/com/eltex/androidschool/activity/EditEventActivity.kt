package com.eltex.androidschool.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.eltex.androidschool.R
import com.eltex.androidschool.data.Event
import com.eltex.androidschool.databinding.ActivityEditEventBinding
import com.eltex.androidschool.ui.EdgeToEdgeHelper
import dev.ahmedmourad.bundlizer.bundle
import dev.ahmedmourad.bundlizer.unbundle

class EditEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val binding = ActivityEditEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        EdgeToEdgeHelper.enableEdgeToEdge(findViewById(android.R.id.content))

        val event = requireNotNull(intent.extras?.unbundle(Event.serializer()))

        binding.content.setText(event.content)

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.save_event -> {
                    val content = binding.content.text?.toString().orEmpty()

                    if (content.isNotBlank()) {
                        val resultIntent = Intent().apply {
                            putExtras(event.copy(content = content).bundle(Event.serializer()))
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