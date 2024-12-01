package com.eltex.androidschool.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import com.eltex.androidschool.R
import com.eltex.androidschool.databinding.FragmentNewEventBinding
import com.eltex.androidschool.db.AppDb
import com.eltex.androidschool.repository.SqliteEventRepository
import com.eltex.androidschool.viewmodel.NewEventViewModel
import com.eltex.androidschool.viewmodel.ToolbarViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class NewEventFragment : Fragment() {

    companion object {
        const val EVENT_ID = "EVENT_ID"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewEventBinding.inflate(layoutInflater)

        val toolbarViewModel by activityViewModels<ToolbarViewModel>()

        val eventId = arguments?.getLong(EVENT_ID) ?: 0L

        val newEventViewModel by viewModels<NewEventViewModel>{
            viewModelFactory {
                addInitializer(
                    NewEventViewModel::class,
                ) {
                    NewEventViewModel(
                        repository = SqliteEventRepository(
                            dao = AppDb.getInstance(requireContext().applicationContext).eventDao
                        ),
                        id = eventId
                    )
                }
            }
        }

        toolbarViewModel.saveClicked.filter { it }
            .onEach {
                val content = binding.content.text?.toString().orEmpty()

                if (content.isNotBlank()) {
                    newEventViewModel.save(content)
                    findNavController().navigateUp()
                } else {
                    Toast.makeText(requireContext(), R.string.text_is_empty, Toast.LENGTH_SHORT)
                        .show()
                }

                toolbarViewModel.onSaveClicked(false)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycle.addObserver(
            object : LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    when (event) {
                        Lifecycle.Event.ON_START -> toolbarViewModel.setSaveVisible(true)
                        Lifecycle.Event.ON_STOP -> toolbarViewModel.setSaveVisible(false)
                        Lifecycle.Event.ON_DESTROY -> source.lifecycle.removeObserver(this)
                        else -> Unit
                    }
                }

            }
        )

        return binding.root
    }
}