package com.peraz.notesapp.domain.repository

import com.peraz.notesapp.presentation.home.Notes
import kotlinx.coroutines.flow.SharedFlow

interface NotesRepository {
    val newNoteInsertionListener: SharedFlow<Notes>
    val updatedNoteInsertionListener: SharedFlow<Notes>
    val deleteNoteListener: SharedFlow<Int>

    fun getAll():List<Notes>
    fun get(id: Int): Notes
    suspend fun insert(item: Notes): Int
    suspend fun update(item: Notes)
    suspend fun delete(id: Int)
}