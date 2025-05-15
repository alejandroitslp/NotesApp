package com.peraz.notesapp.data.repository

import android.util.Log
import com.peraz.notesapp.data.local.NotesDao
import com.peraz.notesapp.data.local.toModel
import com.peraz.notesapp.domain.repository.NotesRepository
import com.peraz.notesapp.presentation.home.Notes
import com.peraz.notesapp.presentation.home._notesList
import com.peraz.notesapp.presentation.home.toEntity
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

class NotesRepositoryImpl @Inject constructor(
    private val notesDao: NotesDao
): NotesRepository{
    private val TAG="NotesRepositoryImpl"
//    val items = arrayListOf<Notes>().apply {
//        addAll(_notesList)
//    } Esto se selecciono por la implementacion del DAO
    private var _newNoteInsertionListener = MutableSharedFlow<Notes>() //Crea una variable que es recolectada por varios elementos
    override var newNoteInsertionListener: SharedFlow<Notes> = _newNoteInsertionListener.asSharedFlow() //La variable expuesta que es solo lectura.

    private var _updatedNoteInsertionListener = MutableSharedFlow<Notes>()
    override var updatedNoteInsertionListener: SharedFlow<Notes> = _updatedNoteInsertionListener.asSharedFlow()

    private var _deleteNoteListener = MutableSharedFlow<Int>()
    override var deleteNoteListener: SharedFlow<Int> = _deleteNoteListener.asSharedFlow()

     override suspend fun getAll(): List<Notes> {
//        return items
         return notesDao.getAll().map {
             it.toModel()
         }//Todos los valores retornados por get all, uno por uno son transformados a model (Notes).
    }

     override suspend fun get(id: Int): Notes {
//        return items.first{
//            it.id==id
//        }
         return notesDao.getItem(id).toModel()
    }

     override suspend fun insert(item: Notes):Int {
        //val newId = items.size+1
         val newId = notesDao.insertItem(item.toEntity()).toInt()
        val newNote= item.copy(
            id = newId
        )
        _newNoteInsertionListener.emit(newNote)
        //items.add(newNote)

        return newId
    }

     override suspend fun update(item: Notes) {
         val itemIndex = notesDao.updateItem(item.toEntity())
        //val itemIndex = items.indexOfFirst{ it.id == item.id}
        //Se obtiene el index del item que coincide con el id
        _updatedNoteInsertionListener.emit(item)

         //items[itemIndex] = item
        //de la lista de items, se selecciona aquel que tiene el item que se va a reemplazar con item.
    }

    override suspend fun delete(id: Int) {
//        val itemIndex = items.indexOfFirst{ it.id == id}
//        if (itemIndex!=-1){
//            items.removeAt(itemIndex)
//        }
        notesDao.deleteNote(id)
        _deleteNoteListener.emit(id)
    }
}