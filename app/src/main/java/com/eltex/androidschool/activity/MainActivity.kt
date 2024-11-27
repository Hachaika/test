package com.eltex.androidschool.activity

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.eltex.androidschool.R
import com.eltex.androidschool.adapter.EventAdapter
import com.eltex.androidschool.adapter.OffsetDecoration
import com.eltex.androidschool.data.Event
import com.eltex.androidschool.databinding.ActivityMainBinding
import com.eltex.androidschool.db.AppDb
import com.eltex.androidschool.repository.SqliteEventRepository
import com.eltex.androidschool.ui.EdgeToEdgeHelper
import com.eltex.androidschool.viewmodel.EventViewModel
import dev.ahmedmourad.bundlizer.bundle
import dev.ahmedmourad.bundlizer.unbundle
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<EventViewModel> {
        viewModelFactory {
            addInitializer(EventViewModel::class) {
                EventViewModel(SqliteEventRepository(
                    AppDb.getInstance(applicationContext).eventDao
                ))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()


        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        handleIncomingIntent()

        val adapter = EventAdapter(
            object : EventAdapter.EventListener {
                override fun onLikeClicked(event: Event) {
                    viewModel.likeById(event.id)
                }

                override fun onParticipateClicked(event: Event) {
                    viewModel.participateById(event.id)
                }

                override fun onShareClicked(event: Event) {
                    share(event.content)
                }

                override fun onDeleteClicked(event: Event) {
                    viewModel.deleteById(event.id)
                }

                override fun onEditClicked(event: Event) {
                    editEventLauncher.launch(
                        Intent(
                            this@MainActivity,
                            EditEventActivity::class.java
                        ).apply {
                            putExtras(event.bundle(Event.serializer()))
                        })
                }

                val editEventLauncher =
                    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                        if (result.resultCode == RESULT_OK) {
                            val data = result.data
                            val event = data?.extras?.unbundle(Event.serializer())
                            if (event != null) {
                                viewModel.editById(event.id, event)
                            }
                        }
                    }

            }
        )

        binding.list.adapter = adapter

        val editText = findViewById<EditText>(R.id.content)

        val sharedText = intent.getStringExtra("SHARED_TEXT")
        if (!sharedText.isNullOrBlank()) {
            editText.setText(sharedText)
        }


        val launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                it.data?.getStringExtra(Intent.EXTRA_TEXT)?.let {content ->
                    viewModel.addEvent(content)
                }
            }

        binding.list.addItemDecoration(
            OffsetDecoration(resources.getDimensionPixelSize(R.dimen.list_offset))
        )

        binding.newEvent.setOnClickListener {
            launcher.launch(Intent(this, NewEventActivity::class.java))
        }

        viewModel.state
            .onEach {
                adapter.submitList(it.events)
            }
            .launchIn(lifecycleScope)

        applyInsets()

    }

    private fun handleIncomingIntent() {
        if (intent?.action == Intent.ACTION_SEND) {
            val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)

            if (!sharedText.isNullOrBlank()) {
                viewModel.addEvent(sharedText)
            }

            intent.action = Intent.ACTION_MAIN
            intent.removeExtra(Intent.EXTRA_TEXT)
        }
    }



    private fun share(text: String) {
        val intent = Intent.createChooser(
            Intent(Intent.ACTION_SEND)
                .putExtra(Intent.EXTRA_TEXT, text)
                .setType("text/plain"),
            null,
        )

        runCatching {
            startActivity(intent)
        }
            .onFailure {
                Toast.makeText(this, R.string.app_not_found, Toast.LENGTH_SHORT).show()
            }
    }

    private fun applyInsets() {
        EdgeToEdgeHelper.enableEdgeToEdge(findViewById(android.R.id.content))
    }
}