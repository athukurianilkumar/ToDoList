package com.singlepointsolution.todolist.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/*
Anil Kumar Atukuri
now, enable HILT in our app by annotating our application class with @HiltAndroidApp to trigger HILT’s code generation.
 */
@HiltAndroidApp
class myapp : Application()