package com.peraz.notesapp.data.repository

import android.util.Log
import com.peraz.notesapp.domain.repository.NotesRepository
import com.peraz.notesapp.presentation.home.Notes
import com.peraz.notesapp.presentation.home._notesList
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class NotesRepositoryImpl @Inject constructor(): NotesRepository{
    private val TAG="NotesRepositoryImpl"
    val items = arrayListOf<Notes>().apply {
        addAll(_notesList)
    }
    private var _newNoteInsertionListener = MutableSharedFlow<Notes>() //Crea una variable que es recolectada por varios elementos
    override var newNoteInsertionListener: SharedFlow<Notes> = _newNoteInsertionListener.asSharedFlow() //La variable expuesta que es solo lectura.

    private var _updatedNoteInsertionListener = MutableSharedFlow<Notes>()
    override var updatedNoteInsertionListener: SharedFlow<Notes> = _updatedNoteInsertionListener.asSharedFlow()

    private var _deleteNoteListener = MutableSharedFlow<Int>()
    override var deleteNoteListener: SharedFlow<Int> = _deleteNoteListener.asSharedFlow()

     override fun getAll(): List<Notes> {
        return items
    }

     override fun get(id: Int): Notes {
        return items.first{
            it.id==id
        }
    }

     override suspend fun insert(item: Notes):Int {
        val newId = items.size+1
        val newNote= item.copy(
            id = newId
        )
         Log.d(TAG,"${hashCode()}")
        _newNoteInsertionListener.emit(newNote)
        items.add(newNote)

        return newId
    }

     override suspend fun update(item: Notes) {
        val itemIndex = items.indexOfFirst{ it.id == item.id}
        //Se obtiene el index del item que coincide con el id
        _updatedNoteInsertionListener.emit(item)
        items[itemIndex] = item
        //de la lista de items, se selecciona aquel que tiene el item que se va a reemplazar con item.
    }

    override suspend fun delete(id: Int) {
        val itemIndex = items.indexOfFirst{ it.id == id}
        if (itemIndex!=-1){
            items.removeAt(itemIndex)
        }

        _deleteNoteListener.emit(id)
    }
}