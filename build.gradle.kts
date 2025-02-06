// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.android.application) apply false

    id("com.google.gms.google-services") version "4.4.2" apply false
}


// Add this block
//allprojects {
//    repositories {
//        google()
//        mavenCentral()
//        maven { url = uri("https://jitpack.io")}
//    }
//}
//dependencyResolutionManagement {
//    repositories {
//        google()
//        mavenCentral()
//        maven {url = uri("https://jitpack.io") }  // Add this line
//    }
//}