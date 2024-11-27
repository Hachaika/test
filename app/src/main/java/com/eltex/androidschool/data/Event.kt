package com.eltex.androidschool.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    @SerialName("id")
    val id: Long = 0L,
    @SerialName("author")
    val author: String = "",
    @SerialName("content")
    val content: String = "",
    @SerialName("date")
    val date: String = "",
    @SerialName("likedByMe")
    val likedByMe: Boolean = false,
    @SerialName("status")
    val status: String = "",
    @SerialName("statusTime")
    val statusTime: String = "",
    @SerialName("link")
    val link: String = "",
    @SerialName("participants")
    val participants: String = "",
    @SerialName("participatedByMe")
    val participatedByMe: Boolean = false
)
