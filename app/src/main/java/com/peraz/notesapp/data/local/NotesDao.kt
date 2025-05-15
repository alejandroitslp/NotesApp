package com.peraz.notesapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDao {
    @Query("Select * from notes")
    suspend fun getAll(): List<NoteEntity>

    @Insert
    suspend fun insertItem(item: NoteEntity): Long

    @Update
    suspend fun updateItem(item: NoteEntity)

    @Query("Delete from notes Where id = :id")
    suspend fun deleteNote(id: Int)

    @Query("Select * from notes where id = :id Limit 1")
    suspend fun getItem(id: Int): NoteEntity
}
