package com.peraz.notesapp.domain.usecase

import com.peraz.notesapp.domain.Resource
import com.peraz.notesapp.domain.repository.NotesRepository
import com.peraz.notesapp.presentation.home.Notes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUseCase @Inject constructor(
    private val repository: NotesRepository
    ){
        operator fun invoke(id: Int): Flow<Resource<Notes>> = flow{
            try {
                emit(Resource.Loading())
                emit(Resource.Success(
                    data = repository.get(id)
                ))
            }catch(e: Exception) {
                emit(Resource.Error(message = e.message ?: "Unknow Error"))
            }
        }
    }