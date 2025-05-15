package com.peraz.notesapp.presentation.addnote


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.peraz.notesapp.R
import com.peraz.notesapp.presentation.components.ConfirmationDialog
import com.peraz.notesapp.presentation.home.Notes
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.compose

@Composable
fun AddNoteScreen(
    viewModel: AddNoteViewModel= hiltViewModel(),
    navigateBack: () -> Unit, //Funcion de orden superior
) {
    val title by viewModel.title.collectAsState()
    val description by  viewModel.description.collectAsState()
    val showConfirmationDialog by viewModel.showConfirmationDialog.collectAsState()

    LaunchedEffect(key1 = true) { // Se lanza una vez y el valor de key para el relaunch va a cambiar. evitando la recomposicion
        viewModel.event.collectLatest { event->
            when(event){
                is AddNoteViewModel.Event.NavigateBack -> navigateBack()
            }
        }
    }


    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
                    .padding(vertical = 30.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_back_24),
                    contentDescription = null, tint = Color.White,
                    modifier = Modifier.size(40.dp)
                        .clickable{
                            viewModel.backIconOnClick()
                        }
                )
            }
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp)
                    .padding(vertical = 30.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_delete_24),
                    contentDescription = null, tint = Color.White,
                    modifier = Modifier.size(40.dp).clickable{
                        viewModel.showConfirmationDialog()
                    }
                )
            }
        }
        TextField(
            value = title,
            onValueChange = {
                viewModel.titleOnValueChange(it) },
            placeholder = {
                Text(text = "Enter a title")
            }, modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 20.sp
            ),
        )
        TextField(
            value = description,
            onValueChange = {
                viewModel.descriptionOnValueChange(it)},
            placeholder = {
                Text(text = "Enter Description")
            }, modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
    if (showConfirmationDialog){
        ConfirmationDialog(dismissButton = {
            viewModel.hideConfirmationDialog()
        }, confirmButton = {
            viewModel.deleteNote()
        })
    }
}

@Preview(showBackground = true)
@Composable
fun AddNoteScreenPreview() {
    AddNoteScreen(navigateBack = {})
}
