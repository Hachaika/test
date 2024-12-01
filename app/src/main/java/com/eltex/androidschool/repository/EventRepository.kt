package com.eltex.androidschool.repository

import com.eltex.androidschool.data.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {

    fun getEvent(): Flow<List<Event>>

    fun likeById(id: Long)

    fun participateById(id: Long)

    fun deleteById(id: Long)

    fun editById(id: Long, updatedEvent: Event)

    fun save(content: String, id: Long)

}