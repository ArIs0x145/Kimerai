plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android.gradlePlugin) // Hilt
    alias(libs.plugins.kotlin.ksp) // KSP
    alias(libs.plugins.kotlin.serialization) // Kotlin Serialization
}

android {
    namespace = "io.github.aris0x145.kimerai"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.github.aris0x145.kimerai"
        minSdk = 24
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // Core & Lifecycle
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose) // ViewModel Compose

    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose) // Compose Navigation

    // Hilt (Dependency Injection)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler) // Use ksp instead of kapt
    // Optional: Hilt Navigation Compose integration
    implementation(libs.hilt.navigation.compose)

    // Networking (Retrofit & kotlinx.serialization)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinx.serialization) // kotlinx.serialization converter
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging.interceptor) // Optional: For logging network requests

    // Database (Room)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx) // Coroutines support for Room
    ksp(libs.androidx.room.compiler) // Use ksp for Room annotation processing

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    // Optional: Hilt testing
    // androidTestImplementation(libs.hilt.android.testing)
    // kspAndroidTest(libs.hilt.compiler)

    // Debugging
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

// Optional: If you haven't configured KSP elsewhere
// ksp {
//    arg("room.schemaLocation", "$projectDir/schemas")
// }