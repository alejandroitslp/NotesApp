package com.peraz.notesapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.gson.Gson
import com.peraz.notesapp.R
import com.peraz.notesapp.Routes
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.compose

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateNext: (String) -> Unit,
) {
    val notes = viewModel.notes

    /*
    LaunchedEffect(key1 = newNote) {
        if (newNote.isNullOrEmpty()) return@LaunchedEffect
        val newNoteObj = Gson().fromJson(newNote, Notes::class.java)
        viewModel.saveNote(newNoteObj)
    }*/

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event->
            when(event){
                is HomeViewModel.HomeEvent.NavigateNext -> navigateNext(event.route)
            }
        }
    }

    Scaffold(
        topBar = {
            Row(modifier = Modifier.fillMaxWidth().height(100.dp)
                .background(MaterialTheme.colorScheme.primary).padding(start = 30.dp, top = 40.dp)) {
                Text(text="Notes App", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val route = Routes.ADD_NOTE +"/-1" //Se define la ruta
                    navigateNext(route) //Se navega directamente a la ruta definida antes.
                },
                containerColor = MaterialTheme.colorScheme.tertiary
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_add_24),
                    tint = Color.White,
                    contentDescription = null
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier
            .padding(innerPadding)
            .background(Color.LightGray)) {
            items(notes.size){
                index->
                val note = notes[index]
                ElementList(note.id, note.title, note.desc, viewModel)
            }
        }
    }
}

@Composable
fun ElementList(id: Int ,title: String, desc: String, viewModel: HomeViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.White)
            .clip(RoundedCornerShape(10.dp))
            .clickable{
                viewModel.listItemOnClick(id)
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = title, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = desc, color = MaterialTheme.colorScheme.primary)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
}