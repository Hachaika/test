package com.eltex.androidschool.adapter

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eltex.androidschool.data.Event
import com.eltex.androidschool.databinding.CardEventBinding

class EventViewHolder(private val binding: CardEventBinding) : ViewHolder(binding.root) {
    fun bind(event: Event) {
        binding.author.text = event.author
        binding.content.text = event.content
        binding.date.text = event.date
        binding.status.text = event.status
        binding.statusTime.text = event.statusTime
        binding.link.text = event.link
        binding.participants.text = event.participants
        binding.initial.text = event.author.take(1)
        updateLike(event.likedByMe)
        updateParticipant(event.participatedByMe)
    }

    fun bind(payload: EventPayload) {
        payload.likedByMe?.let { likedByMe ->
            updateLike(likedByMe)
        }

        payload.participatedByMe?.let { participatedByMe ->
            updateParticipant(participatedByMe)
        }
    }

    private fun updateLike(likedByMe: Boolean) {
        binding.like.isSelected = likedByMe
        binding.like.text = if (likedByMe) "1" else "0"

        // Анимация лайка
        binding.like.animate().scaleX(1.2f).scaleY(1.2f).setDuration(200).withEndAction {
            binding.like.animate().scaleX(1f).scaleY(1f).setDuration(200)
        }

    }

    private fun updateParticipant(participatedByMe: Boolean) {
        binding.participants.isSelected = participatedByMe
        binding.participants.text = if (participatedByMe) "1" else "0"

        // Анимация участия
        val translationY = if (participatedByMe) 10f else -10f

        val animator = ObjectAnimator.ofFloat(binding.participants, "translationY", translationY)
        animator.duration = 300
        animator.repeatCount = 1
        animator.repeatMode = ValueAnimator.REVERSE
        animator.start()
    }
}