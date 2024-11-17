package com.eltex.androidschool.repository

import com.eltex.androidschool.data.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryEventRepository : EventRepository {

    private var nextId: Long = 100

    private val state = MutableStateFlow(
        List(nextId.toInt()) {
            Event(
                id = it.toLong(),
                author = "Lydia Westervelt",
                content = "№ ${it + 1} Приглашаю провести уютный вечер за увлекательными играми! У нас есть несколько вариантов настолок, подходящих для любой компании.",
                date = "11.05.22 11:21",
                status = "Offline",
                statusTime = "16.05.22 12:00",
                link = "https://m2.material.io/components/cards",
                participants = "2"
            )
        }
            .reversed()
    )

    override fun getEvent(): Flow<List<Event>> = state.asStateFlow()


    override fun likeById(id: Long) {
        state.update { events ->
            events.map {
                if (it.id == id) {
                    it.copy(likedByMe = !it.likedByMe)
                } else {
                    it
                }
            }
        }
    }

    override fun participateById(id: Long) {
        state.update { events ->
            events.map {
                if (it.id == id) {
                    it.copy(participatedByMe = !it.participatedByMe)
                } else {
                    it
                }
            }
        }
    }

    override fun deleteById(id: Long) {
        state.update { events ->
            events.filter {
                it.id != id
            }
        }
    }

    override fun editById(id: Long, newContent: String) {
        state.update { events ->
            events.map {
                if (it.id == id) {
                    it.copy(content = newContent)
                } else {
                    it
                }
            }
        }
    }

    override fun addEvent(content: String) {
        state.update {
            buildList (it.size + 1) {
                add(Event(id = nextId++, content = content, author = "Student"))
                addAll(it)
            }
        }
    }
}