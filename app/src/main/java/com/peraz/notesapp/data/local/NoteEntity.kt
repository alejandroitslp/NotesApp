package com.peraz.notesapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.peraz.notesapp.presentation.home.Notes

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val title: String?,
    val description: String?,
)

fun NoteEntity.toModel(): Notes{
    return Notes(
        id = this.id ?: -1,
        title=this.title ?: "",
        desc = this.description ?: "",
    )
}// Esta funcion convierte la Entity en un Modelo que se llama Notes jeje