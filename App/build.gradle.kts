// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.kotlin.android) apply false
}


buildscript {

    repositories {
        google()
        mavenCentral()
        maven { url = java.net.URI.create("https://jitpack.io") }
//        maven { url = URI.create("https://plugins.gradle.org/m2/") }
    }

    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.kotlin.stdlib.jdk8)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }

}
