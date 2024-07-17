// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    //dependency injection with hilt
    id ("com.google.dagger.hilt.android") version "2.46.1" apply false
    alias(libs.plugins.android.library) apply false
}