package com.eltex.androidschool.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.eltex.androidschool.data.EventTable
import com.eltex.androidschool.entity.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Query("SELECT * FROM Events ORDER BY id DESC")
    fun getAll(): Flow<List<EventEntity>>
    @Upsert
    fun save(event: EventEntity): Long
    @Query(
        """
            UPDATE Events SET
                likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
            WHERE id = :id
        """
    )
    fun likeById(id: Long)
    @Query(
        """
            UPDATE Events SET
                participatedByMe = CASE WHEN participatedByMe THEN 0 ELSE 1 END
            WHERE id = :id
        """
    )
    fun participateById(id: Long)
    @Query("DELETE FROM Events WHERE id = :id")
    fun deleteById(id: Long)
    @Query(
        """
                UPDATE ${EventTable.TABLE_NAME} SET
                     content = :updatedContent
                WHERE id = :id;
            """
    )
    fun editById(id: Long, updatedContent: String)
}