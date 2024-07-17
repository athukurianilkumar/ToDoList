package com.singlepointsolution.todolist.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/*
Anil Kumar Atukuri
This screen is about AddNoteScreen in this screen we have one TextField which we need to enter any text in that
and if we donot enter any means with empty field we click on save note button at that time we get error message
to add any text witout entering blank.
*/

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AddNoteScreen(
    state: NoteState,
    navController: NavController,
    onEvent: (NotesEvent) -> Unit,
) {
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(3000L) // 3 seconds delay
        isLoading = false
    }

    if (isLoading) {
        LoadingScreen()
    } else {
        Scaffold(floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (state.title.value.isNullOrEmpty()) {
                        showError = true
                        errorMessage = "Title cannot be empty"
                    } else {
                        isLoading = true
                        showError = false
                        onEvent(
                            NotesEvent.SaveNote(
                                title = state.title.value
                            )
                        )
                        isLoading = false
                    }
                    navController.popBackStack()
                }, shape = CircleShape, containerColor = Color(0xFF50C878)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Check, contentDescription = "Save Note"
                )
            }
        }, content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                    value = state.title.value ?: "",
                    onValueChange = {
                        showError = false
                        state.title.value = it
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontWeight = FontWeight.SemiBold, fontSize = 17.sp
                    ),
                    placeholder = {
                        Text(text = "Title")
                    })
            }
        })/*
       In this if condition i am checking showerror message is null or empty.
         by this condition i am trying to handle the error and making back screen and saving error message make it true
     */
        if (showError) {
            try {
                navController.navigate("NotesScreen") {
                    popUpTo("NotesScreen") { inclusive = true }
                }
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "error_message", "Failed to add TODO"
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

            /*
        This is normal alert dialog box
         */
//        AlertDialog(onDismissRequest = { showError = false },
//            title = { Text("Error", fontWeight = FontWeight.Bold) },
//            text = { Text(errorMessage , fontSize = 18.sp) },
//            confirmButton = {
//                Button(
//                    onClick = {
//                        showError = false
//                        navController.popBackStack()
//                    }, colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(0xFF50C878)
//                    )
//                ) {
//                    Text("OK", fontSize = 16.sp)
//                }
//            }, containerColor = Color(0xFFFFFFFF))
        }
    }

}


@Composable
fun LoadingScreen() {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            progress = 0.89f,
        )
    }
}






