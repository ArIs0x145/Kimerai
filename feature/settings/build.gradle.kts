//plugins {
//    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.kotlin.compose)
//    alias(libs.plugins.hilt)
//    alias(libs.plugins.ksp)
//}
//
//android {
//    namespace = "io.github.aris0x145.kimerai.feature.settings"
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
//    // 依賴領域層
//    implementation(project(":domain"))
//
//    // 依賴核心模組
//    implementation(project(":core:ui"))
//    implementation(project(":core:common"))
//
//    // Compose
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.ui.graphics)
//    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
//    implementation(libs.androidx.compose.material.icons.extended)
//
//    // 導航
//    implementation(libs.androidx.navigation.compose)
//
//    // Hilt 依賴注入
//    implementation(libs.hilt.android)
//    implementation(libs.hilt.navigation.compose)
//    ksp(libs.hilt.compiler)
//
//    // 測試
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//
//    // Debug
//    debugImplementation(libs.androidx.ui.tooling)
//}