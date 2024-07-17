package com.singlepointsolution.todolist.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.singlepointsolution.todolist.R
import kotlinx.coroutines.delay

@SuppressLint("UnrememberedMutableState")
@Composable
fun NotesScreen(
    state: NoteState,
    navController: NavController,
    viewModel: NotesViewModel,
    onEvent: (NotesEvent) -> Unit,
) {
    var searchtext by remember { mutableStateOf("") }

    /*
    Here the savestate getting true and its value
     */
    var showError by remember { mutableStateOf(false) }
    val errorMessage =
        navController.currentBackStackEntry?.savedStateHandle?.get<String>("error_message")

    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            showError = true
            navController.currentBackStackEntry?.savedStateHandle?.remove<String>("error_message")
        }
    }

    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .background(Color(0xFF008000))
                .padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                modifier = Modifier.weight(1f),
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                state.title.value = ""
                navController.navigate("AddNoteScreen")
            }, shape = CircleShape, containerColor = Color(0xFF50C878)
        ) {
            Icon(
                imageVector = Icons.Rounded.Add, contentDescription = "Add new note"
            )
        }
    }, content = { paddingValues ->
        if (state.notes.isEmpty() && searchtext.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(text = "Press the + button to add a TODO item")
            }
        }

        Column(Modifier.padding(paddingValues)) {
            OutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                value = searchtext,
                onValueChange = {
                    searchtext = it
                    viewModel.SearchText(searchtext)
                },
                textStyle = TextStyle(
                    fontWeight = FontWeight.SemiBold, fontSize = 17.sp
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Search
                ),
                singleLine = true,
                placeholder = {
                    Text(text = "Search")
                })

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.notes.size) { index ->
                    NoteItem(state = state, index = index)
                }
            }
        }
    })

    /*
    Here showing alert dialog box.
     */
    if (showError) {
        AlertDialog(onDismissRequest = { showError = false },
            title = { Text("Error", fontWeight = FontWeight.Bold) },
            text = { Text("Failed to add TODO", fontSize = 18.sp) },
            confirmButton = {
                Button(
                    onClick = {
                        showError = false
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF50C878)
                    )
                ) {
                    Text("OK", fontSize = 16.sp)
                }
            },
            containerColor = Color(0xFFFFFFFF)
        )
    }
}

@Composable
fun NoteItem(state: NoteState, index: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = state.notes[index].title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
