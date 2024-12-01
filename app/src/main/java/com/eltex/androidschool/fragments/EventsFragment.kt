package com.eltex.androidschool.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import com.eltex.androidschool.R
import com.eltex.androidschool.activity.EditEventActivity
import com.eltex.androidschool.adapter.EventAdapter
import com.eltex.androidschool.adapter.OffsetDecoration
import com.eltex.androidschool.data.Event
import com.eltex.androidschool.databinding.FragmentEventsBinding
import com.eltex.androidschool.db.AppDb
import com.eltex.androidschool.repository.SqliteEventRepository
import com.eltex.androidschool.viewmodel.EventViewModel
import dev.ahmedmourad.bundlizer.bundle
import dev.ahmedmourad.bundlizer.unbundle
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class EventsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val viewModel by viewModels<EventViewModel> {
            viewModelFactory {
                addInitializer(EventViewModel::class) {
                    EventViewModel(
                        SqliteEventRepository(
                            AppDb.getInstance(requireContext().applicationContext).eventDao
                        )
                    )
                }
            }
        }

        val binding = FragmentEventsBinding.inflate(layoutInflater, container, false)

//        handleIncomingIntent()

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
                    findNavController().navigate(
                        R.id.action_eventsFragment_to_newEventFragment,
                        bundleOf(
                            NewEventFragment.EVENT_ID to event.id,
                            NewEventFragment.ARG_CONTENT to event.content
                        )
                    )
                }
            }
        )

//        val editText = findViewById<EditText>(R.id.content)
//
//        val sharedText = intent.getStringExtra("SHARED_TEXT")
//        if (!sharedText.isNullOrBlank()) {
//            editText.setText(sharedText)
//        }

        binding.list.addItemDecoration(
            OffsetDecoration(resources.getDimensionPixelSize(R.dimen.list_offset))
        )

        binding.list.adapter = adapter

        viewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                adapter.submitList(it.events)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)


        return binding.root
    }

//    private fun handleIncomingIntent() {
//        if (intent?.action == Intent.ACTION_SEND) {
//            val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
//
//            if (!sharedText.isNullOrBlank()) {
//                viewModel.addEvent(sharedText)
//            }
//
//            intent.action = Intent.ACTION_MAIN
//            intent.removeExtra(Intent.EXTRA_TEXT)
//        }
//    }


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
                Toast.makeText(requireContext(), R.string.app_not_found, Toast.LENGTH_SHORT).show()
            }
    }
}