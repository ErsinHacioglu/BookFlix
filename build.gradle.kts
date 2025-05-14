// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

buildscript {
    dependencies {
        classpath ("com.android.tools.build:gradle:8.2.2") // Gradle versiyonunu güncel tut
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
    }
}

