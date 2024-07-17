package com.singlepointsolution.todolist

import android.annotation.SuppressLint
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.singlepointsolution.todolist.presentation.AddNoteScreen
import com.singlepointsolution.todolist.presentation.NoteState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class AddNoteScreenTest {

    @Test
    fun testExample() {
        // Your test logic here
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: NavController

    @Composable
    @Before
    fun setup() {
        navController = rememberNavController()
        composeTestRule.setContent {
            AddNoteScreen(
                state = NoteState(),
                navController = navController,
                onEvent = {}
            )
        }
    }

//    @After
//    fun cleanup() {
//        composeTestRule.activityRule.scenario.close()
//    }

    @Test
    fun testSaveNoteButton() = runBlockingTest {
        // Wait for the UI to stabilize
        composeTestRule.waitForIdle()

        // Perform a click on the save note button
        composeTestRule.onNodeWithContentDescription("Save Note").performClick()

        // Assert that the error dialog is displayed
        composeTestRule.onNodeWithText("Error").assertIsDisplayed()
        composeTestRule.onNodeWithText("Title cannot be empty").assertIsDisplayed()
    }

    @Test
    fun testTitleTextField() = runBlockingTest {
        // Wait for the UI to stabilize
        composeTestRule.waitForIdle()

        // Perform a change in the title text field
        composeTestRule.onNodeWithTag("TitleTextField")
            .performTextInput("Test Title")

        // Assert that the title text has changed
        composeTestRule.onNodeWithTag("TitleTextField")
            .assert(hasText("Test Title"))
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Test
    fun testLoadingScreen() = runBlockingTest {
        // Wait for the UI to stabilize
        composeTestRule.waitForIdle()

        // Set the loading state to true
        composeTestRule.setContent {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {},
                        shape = CircleShape,
                        containerColor = Color(0xFF50C878)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = "Save Note"
                        )
                    }
                },
                content = {
//                    LoadingScreen()
                }
            )
        }

        // Assert that the loading screen is displayed
        composeTestRule.onNodeWithContentDescription("Circular Progress Indicator")
            .assertIsDisplayed()
    }
}