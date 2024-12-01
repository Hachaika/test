
package com.eltex.androidschool.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.eltex.androidschool.R
import com.eltex.androidschool.databinding.FragmentBottomNavigationBinding

class BottomNavigationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBottomNavigationBinding.inflate(inflater, container, false)

        val navController =
            requireNotNull(childFragmentManager.findFragmentById(R.id.container)).findNavController()

        val eventsClickListener = View.OnClickListener {
            findNavController().navigate(R.id.action_bottomNavigationFragment_to_newEventFragment)
        }

        val postsClickListener = View.OnClickListener {
            // TODO
        }

        val usersClickListener = View.OnClickListener {
            // TODO
        }

        binding.bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.eventsFragment -> {
                    binding.newEvent.setOnClickListener(eventsClickListener)
                    binding.newEvent.animate()
                        .scaleX(1F)
                        .scaleY(1F)
                }
                R.id.postsFragment -> {
                    binding.newEvent.setOnClickListener(postsClickListener)
                    binding.newEvent.animate()
                        .scaleX(1F)
                        .scaleY(1F)
                }
                R.id.usersFragment -> {
                    binding.newEvent.setOnClickListener(usersClickListener)
                    binding.newEvent.animate()
                        .scaleX(0F)
                        .scaleY(0F)
                }
            }
        }


        return binding.root
    }
}