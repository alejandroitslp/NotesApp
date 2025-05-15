package com.peraz.notesapp.presentation

import android.icu.text.MessagePattern
import android.icu.text.MessagePattern.ArgType
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import com.peraz.notesapp.presentation.addnote.AddNoteScreen
import com.peraz.notesapp.presentation.home.HomeScreen
import com.peraz.notesapp.presentation.theme.NotesAppTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.peraz.notesapp.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesAppTheme {
                Surface {

                    val navController = rememberNavController()

                    NavHost(
                        startDestination = Routes.HOME,
                        navController = navController
                    )
                    {

                        composable(Routes.HOME) {
                            /*val newNoteJsonStr = navController
                                .currentBackStackEntry
                                ?.savedStateHandle
                                ?.getStateFlow("new_note", "")
                                ?.collectAsState()*/
                            //Cuando regresa a Home, dado el key, retorna lo que se encuentra en el new_note
                            //Como un bundle

                            HomeScreen(
                                navigateNext = { route ->
                                    navController.navigate(route)
                                    //Se usa la funcion de HomeScreen NavigateNext
                                })
                        }
                        composable(
                            route =  Routes.ADD_NOTE + "/{id}",
                            arguments = listOf(
                                navArgument("id"){
                                    this.type = NavType.IntType
                                    this.defaultValue = -1
                                }
                            ) ) {
                            AddNoteScreen(navigateBack = {
                                navController.popBackStack()
                                //Esto solo ocurre cuando el usuario da click en boton atras
                            })
                        }
                    }
                }
            }
        }
    }
}

