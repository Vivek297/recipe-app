plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android") version "2.49" apply false
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.food1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.food1"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        dataBinding =  true
        viewBinding = true
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

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // dagger hilt and coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation("com.google.dagger:hilt-android:2.49")
    implementation("io.coil-kt:coil:2.0.0-rc02")
    kapt("com.google.dagger:hilt-android-compiler:2.49")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.49")
    kapt ("androidx.hilt:hilt-compiler:1.1.0")
    // navigation
    val nav_version = "2.7.6"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    // room components
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    //DataBinding
    ksp("com.android.databinding:compiler:3.1.4")
    ksp("androidx.databinding:databinding-common:8.2.2")

    // Data store
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    // retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")

    val lifecycle_version = "2.7.0"
    val arch_version = "2.2.0"
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    // optional - Test helpers for Lifecycle runtime
    testImplementation ("androidx.lifecycle:lifecycle-runtime-testing:$lifecycle_version")
    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")

    //Shimmer
    implementation("com.facebook.shimmer:shimmer:0.5.0@aar")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.github.bumptech.glide:glide:4.11.0")


    // Jsoup
    implementation("org.jsoup:jsoup:1.13.1")
    // Gson
    implementation("com.google.code.gson:gson:2.9.0")
    // Image loading library coil
    implementation("io.coil-kt:coil:0.13.0")

}