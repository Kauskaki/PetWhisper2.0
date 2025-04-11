plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.petpawology.petwhisper"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.petpawology.petwhisper"
        minSdk = 28
        targetSdk = 35
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.legacy.support.v4)
    implementation(libs.recyclerview)
    implementation(libs.preference)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //Added for Round Images
    implementation(libs.material.v190)
    implementation(platform("androidx.compose:compose-bom:2025.02.00"))
    implementation(platform("com.google.firebase:firebase-bom:33.12.0"))
    implementation ("com.google.firebase:firebase-auth:21.1.0")
    implementation(libs.firebase.auth)
    implementation ("com.google.firebase:firebase-firestore:25.1.3")
    implementation("com.google.firebase:firebase-analytics")

}