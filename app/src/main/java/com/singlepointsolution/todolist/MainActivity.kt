package com.singlepointsolution.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.singlepointsolution.todolist.presentation.AddNoteScreen
import com.singlepointsolution.todolist.presentation.NotesScreen
import com.singlepointsolution.todolist.presentation.NotesViewModel
import com.singlepointsolution.todolist.ui.theme.ToDoListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<NotesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {

                    val state by viewModel.state.collectAsState()
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = stringResource(id = R.string.notes_screen)
                    ) {
                        composable("NotesScreen") { entry ->
                            NotesScreen(
                                state = state,
                                navController = navController,viewModel,
                                onEvent = viewModel::onEvent
                            )
                        }
                        composable("AddNoteScreen") {
                            AddNoteScreen(
                                state = state,
                                navController = navController,
                                onEvent = viewModel::onEvent
                            )
                        }
                    }

                }
            }
        }
    }
}