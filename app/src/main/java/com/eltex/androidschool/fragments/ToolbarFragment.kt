
package com.eltex.androidschool.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.eltex.androidschool.R
import com.eltex.androidschool.databinding.FragmentToolbarBinding
import com.eltex.androidschool.viewmodel.ToolbarViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ToolbarFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentToolbarBinding.inflate(inflater, container, false)

        val navController =
            requireNotNull(childFragmentManager.findFragmentById(R.id.container)).findNavController()

        binding.toolbar.setupWithNavController(navController)

        val toolbarViewModel by activityViewModels<ToolbarViewModel>()

        val newEventItem = binding.toolbar.menu.findItem(R.id.save_event)

        toolbarViewModel.saveVisible.onEach {
            newEventItem.isVisible = it
        }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        newEventItem.setOnMenuItemClickListener {
            toolbarViewModel.onSaveClicked(true)
            true
        }

        return binding.root
    }
}