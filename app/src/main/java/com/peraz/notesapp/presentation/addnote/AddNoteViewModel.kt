package com.peraz.notesapp.presentation.addnote

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peraz.notesapp.domain.Resource
import com.peraz.notesapp.domain.repository.NotesRepository
import com.peraz.notesapp.domain.usecase.GetUseCase
import com.peraz.notesapp.presentation.home.Notes
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val getUseCase: GetUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val notesRepository: NotesRepository


) : ViewModel() {
    private var _noteId: Int = -1
    private var _title : MutableStateFlow<String> = MutableStateFlow("") //Esta variable si se puede modificar si se accede como _title
    var title = _title.asStateFlow() //Accede a _title como read only, la idea es que no se pueda modificar.
    private var _description : MutableStateFlow<String> = MutableStateFlow("")
    var description = _description.asStateFlow()

    private val _event = MutableSharedFlow<Event>() //Se requiere que pase su estado a diferentes collectors
    val event= _event.asSharedFlow() //Representa la variable de solo lectura.

    private var _showConfirmationDialog = MutableStateFlow<Boolean>(false)
    var showConfirmationDialog: StateFlow<Boolean> = _showConfirmationDialog.asStateFlow()


    private val TAG= "AddNoteViewModel"

    init {
        val noteId = savedStateHandle
            .get<Int>("id") ?: -1
        _noteId=noteId
        //Si noteId es diferente de -1, entonces _noteId deberá tener el valor de noteId y no -1

        if(noteId!=-1){
            getUseCase.invoke(noteId).map {
                result->
                if (result is Resource.Success){
                    _title.value= result.data!!.title
                    _description.value = result.data!!.desc
                }
            }.launchIn(viewModelScope)
        }
    }

    fun titleOnValueChange(value: String) {
        _title.value = value
    }

    fun descriptionOnValueChange(value: String) {
        _description.value = value

    }

    fun backIconOnClick() =viewModelScope.launch(Dispatchers.IO){

        val noteModel = Notes(
            id = _noteId,
            title = _title.value,
            desc = _description.value
        )
        //Save Note
        if (noteModel.id == -1){
            notesRepository.insert(noteModel)
        }else{
            notesRepository.update(noteModel)
        }


        //Navigate Back
        viewModelScope.launch(Dispatchers.Main) { //Se inicia una corutina que cuando muera el viewModel, muere con el la rutina
            _event.emit(Event.NavigateBack)
        }
    }

    fun hideConfirmationDialog() {
        _showConfirmationDialog.value=false
    }

    fun showConfirmationDialog() {
        _showConfirmationDialog.value=true
    }

    fun deleteNote()= viewModelScope.launch(Dispatchers.IO){
        val itemId= _noteId
        notesRepository.delete(itemId)
        hideConfirmationDialog()
        //Navigate Back
        viewModelScope.launch(Dispatchers.Main) { //Se inicia una corutina que cuando muera el viewModel, muere con el la rutina
            _event.emit(Event.NavigateBack)
        }
    }

    sealed class Event {
        data object NavigateBack: Event()
    }

}