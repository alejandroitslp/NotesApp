package com.peraz.notesapp.presentation.home

import android.R.attr.value
import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.res.ResourceResolutionException
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.peraz.notesapp.Routes
import com.peraz.notesapp.data.repository.NotesRepositoryImpl
import com.peraz.notesapp.domain.repository.NotesRepository
import com.peraz.notesapp.presentation.addnote.AddNoteViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {
    val notes = mutableStateListOf<Notes>()

    private val _eventFlow = MutableSharedFlow<HomeEvent>()
    val eventFlow: SharedFlow<HomeEvent> = _eventFlow.asSharedFlow()


    init {
        val TAG="HomeViewModel"
        Log.d(TAG,"${notesRepository.hashCode()}")
        viewModelScope.launch(Dispatchers.IO) {
            val items=notesRepository.getAll()
            notes.addAll(items)
        }


        viewModelScope.launch {
            notesRepository.deleteNoteListener.collect { deletedNoteId->
                val itemIndex = notes.indexOfFirst { it.id == deletedNoteId }
                if (itemIndex!=-1){
                    notes.removeAt(itemIndex)
                }
            }
        }


        viewModelScope.launch{
            notesRepository.newNoteInsertionListener.collect{ newNote ->
                val isNoteEmpty = value.let {
                    newNote.title!="" && newNote.desc!=""
                }
                if (isNoteEmpty){
                    notes.add(0,newNote)
                }
            }
        }

        viewModelScope.launch{
            notesRepository.updatedNoteInsertionListener.collect{ updatedNote ->
                val itemIndex = notes.indexOfFirst {
                    it.id == updatedNote.id
                }
                if (itemIndex != -1){
                    notes[itemIndex] = updatedNote
                }
            }
        }


    }

    var TAG = "HomeViewModel"

    fun listItemOnClick(id: Int) {
        Log.d(TAG, "$notes")
        Log.d(TAG, "listItemOnClick: $id")
        viewModelScope.launch(Dispatchers.Main) {
            val route = Routes.ADD_NOTE + "/$id"
            _eventFlow.emit(HomeEvent.NavigateNext(route))
        }
    }


    /*fun saveNote(value: Notes){
        val isNoteEmpty = value.let {
            it.title.isEmpty() && it.desc.isEmpty()
        }//Crea un booleano de acuerdo a las especificaciones provistas.
        if (isNoteEmpty){
            return //Si la nota esta vacia, no guardará nada
        }
        if (value.id==-1){
            insertUseCase.invoke(value).map {
                result->
                if (result is Resource.Success){
                    val newId = result.data
                    val newValue = value.copy(
                        id = newId!!
                    )
                    notes.add(newValue)
                }
            }.launchIn(viewModelScope)
        }else{launchIn(viewModelScope)
            updateUseCase.invoke(value).map {
                result->
                if (result is Resource.Success)
                {
                    val index: Int= result.data!!.toInt()
                    notes[index]=value
                }
            }.launchIn(viewModelScope)
        }
    }*/

//     fun getAll(){
//        getAllUseCase.invoke().map { result ->
//            if (result is Resource.Success && result.data !=null) {
//                _notes=result.data
//                for (i in 0..<result.data!!.size) {
//                    _notes.add(result.data[i])
//                }
//            }
//        }.launchIn(viewModelScope)
//    }

    sealed class HomeEvent() {
        data class NavigateNext(val route: String) : HomeEvent()
    }
}