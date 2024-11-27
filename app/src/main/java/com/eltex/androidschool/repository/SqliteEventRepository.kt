package com.eltex.androidschool.repository

import com.eltex.androidschool.data.Event
import com.eltex.androidschool.dao.EventDao
import com.eltex.androidschool.entity.EventEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SqliteEventRepository(private val dao: EventDao) : EventRepository {

    override fun getEvent(): Flow<List<Event>> = dao.getAll()
        .map {
            it.map(EventEntity::toEvent)
        }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun participateById(id: Long) {
        dao.participateById(id)
    }

    override fun deleteById(id: Long) {
        dao.deleteById(id)
    }

    override fun addEvent(content: String) {
        dao.save(EventEntity.fromEvent(Event(content = content)))
    }


    override fun editById(id: Long, updatedEvent: Event) {
        val updatedContent = updatedEvent.content
        dao.editById(id, updatedContent)
    }
}