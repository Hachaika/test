package com.eltex.androidschool.data

data class Event (
    val id: Long = 0L,
    val author: String = "",
    val content: String = "",
    val date: String = "",
    val likedByMe: Boolean = false,
    val status: String = "",
    val statusTime: String = "",
    val link: String = "",
    val participants: String = "",
    val participatedByMe: Boolean = false
)