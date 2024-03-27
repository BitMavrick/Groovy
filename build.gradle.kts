buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:8.3.0")
        classpath("com.google.gms:google-services:4.4.0")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("com.google.devtools.ksp") version "1.9.21-1.0.+" apply false
    id("org.jetbrains.kotlin.plugin.parcelize") version "1.9.21" apply false
    id("com.google.firebase.crashlytics") version "2.9.9" apply false
}