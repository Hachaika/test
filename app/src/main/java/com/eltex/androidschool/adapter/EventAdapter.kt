package com.eltex.androidschool.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import com.eltex.androidschool.R
import com.eltex.androidschool.data.Event
import com.eltex.androidschool.databinding.CardEventBinding


class EventAdapter(
    private val listener: EventListener,
) : ListAdapter<Event, EventViewHolder>(EventItemCallback()) {

    interface EventListener{
        fun onLikeClicked (event: Event)
        fun onParticipateClicked (event: Event)
        fun onShareClicked (event: Event)
        fun onDeleteClicked (event: Event)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardEventBinding.inflate(layoutInflater, parent, false)

        val viewHolder = EventViewHolder(binding)

        binding.like.setOnClickListener {
            listener.onLikeClicked(getItem(viewHolder.adapterPosition))
        }

        binding.participants.setOnClickListener {
            listener.onParticipateClicked(getItem(viewHolder.adapterPosition))
        }

        binding.share.setOnClickListener {
            listener.onShareClicked(getItem(viewHolder.adapterPosition))
        }

        binding.menu.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.event_menu)

                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.delete_event -> {
                            listener.onDeleteClicked(getItem(viewHolder.adapterPosition))
                            true
                        }
                        else -> false
                    }
                }

                show()
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(
        holder: EventViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            payloads.forEach {
                if (it is EventPayload) {
                    holder.bind(it)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}