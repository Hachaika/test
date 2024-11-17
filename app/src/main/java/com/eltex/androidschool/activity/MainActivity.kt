package com.eltex.androidschool.activity

import android.content.Intent
import android.os.Bundle
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
import com.eltex.androidschool.repository.InMemoryEventRepository
import com.eltex.androidschool.ui.EdgeToEdgeHelper
import com.eltex.androidschool.viewmodel.EventViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val viewModel by viewModels<EventViewModel> {
            viewModelFactory {
                addInitializer(EventViewModel::class) {
                    EventViewModel(InMemoryEventRepository())
                }
            }
        }

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

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
                    viewModel.findById(event.id)
                }

            }
        )

        binding.list.adapter = adapter

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