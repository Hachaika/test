package com.eltex.androidschool.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eltex.androidschool.data.Event

@Entity(tableName = "Events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Long = 0L,
    @ColumnInfo("author")
    val author: String = "",
    @ColumnInfo("content")
    val content: String = "",
    @ColumnInfo("date")
    val date: String = "",
    @ColumnInfo("likedByMe")
    val likedByMe: Boolean = false,
    @ColumnInfo("status")
    val status: String = "",
    @ColumnInfo("statusTime")
    val statusTime: String = "",
    @ColumnInfo("link")
    val link: String = "",
    @ColumnInfo("participants")
    val participants: String = "",
    @ColumnInfo("participatedByMe")
    val participatedByMe: Boolean = false
) {

    companion object {
        fun fromEvent(event: Event): EventEntity = with(event) {
            EventEntity(
                id = id,
                author = author,
                content = content,
                date = date,
                likedByMe = likedByMe,
                status = status,
                statusTime = statusTime,
                link = link,
                participants = participants,
                participatedByMe = participatedByMe,
            )
        }
    }

    fun toEvent(): Event = Event(
        id = id,
        author = author,
        content = content,
        date = date,
        likedByMe = likedByMe,
        status = status,
        statusTime = statusTime,
        link = link,
        participants = participants,
        participatedByMe = participatedByMe,
    )
}