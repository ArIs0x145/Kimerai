//plugins {
//    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.kotlin.compose)
//}
//
//android {
//    namespace = "io.github.aris0x145.kimerai.core.navigation"
//    compileSdk = 35
//
//    defaultConfig {
//        minSdk = 24
//    }
//
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }
//
//    kotlinOptions {
//        jvmTarget = "11"
//    }
//
//    buildFeatures {
//        compose = true
//    }
//}
//
//dependencies {
//    // Compose Navigation
//    implementation(libs.androidx.navigation.compose)
//
//    // Features
//    implementation(project(":feature:conversation"))
//    implementation(project(":feature:chat-history"))
//    implementation(project(":feature:settings"))
//    implementation(project(":feature:tool-hub"))
//
//    // Compose
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.ui.graphics)
//    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
//
//    // Testing
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//
//    // Debug
//    debugImplementation(libs.androidx.ui.tooling)
//}