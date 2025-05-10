plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.protobuf)
}

android {
    namespace = "io.github.aris0x145.kimerai.core.protocol"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    // Core modules
    implementation(project(":core:common"))
    
    // Protobuf
    implementation(libs.protobuf.kotlin)
    implementation(libs.protobuf.javalite)
    
    // Serialization
    implementation(libs.kotlinx.serialization.json)
    
    // Kotlin
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.core)
    
    // Arrow for functional programming
    implementation(libs.arrow.core)
    
    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}
