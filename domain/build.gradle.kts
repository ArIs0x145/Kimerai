plugins {
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "io.github.aris0x145.kimerai.domain"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Kotlin
    implementation(libs.androidx.core.ktx)
    
    // Coroutines for async operations
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    
    // Inject for dependency injection interfaces
    implementation("javax.inject:javax.inject:1")
    
    // Testing
    testImplementation(libs.junit)
}