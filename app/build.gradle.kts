plugins {
    alias(libs.plugins.android.application)
//    id("com.android.application")
    id("com.google.gms.google-services")


}

android {
    namespace = "com.sumit.mindspring"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sumit.mindspring"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
//    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(platform("com.google.firebase:firebase-bom:33.8.0"))
    implementation("com.google.firebase:firebase-analytics")

//    implementation ("com.google.firebase:firebase-auth:22.3.0")
    implementation("com.google.firebase:firebase-auth")  // Remove version number as using BOM

    implementation ("com.google.android.material:material:1.11.0")
    implementation ("de.hdodenhof:circleimageview:3.1.0")


    // Firebase Storage
//    implementation ("com.google.firebase:firebase-storage:20.3.0")
    implementation("com.google.firebase:firebase-storage")  // Remove version number as using BOM

    // PDF viewer
//    implementation ("com.github.barteksc:android-pdf-viewer:3.1.0-beta.1")
    implementation ("com.github.mhiew:android-pdf-viewer:3.2.0-beta.1")


    // RecyclerView
    implementation ("androidx.recyclerview:recyclerview:1.3.2")

}