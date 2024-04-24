plugins {
    //alias(libs.plugins.androidApplication)
    kotlin("kapt")
    id("com.android.library")
 //   id("com.google.dagger.hilt.android")
   // alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.ssepulveda.costi.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // Room
    api(libs.room.runtime)
    kapt(libs.room.compiler) // remove kap

    // Room Coroutines
    implementation(libs.room.ktx)


    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // DataStore

    implementation(libs.androidx.datastore)

    // Test Mockito
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
   // testImplementation(libs.oi.mockk)

    testImplementation(libs.coroutines.test)

    // Room testing
    testImplementation(libs.room.testing)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

kapt {
    correctErrorTypes = true
}