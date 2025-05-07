//plugins {
//    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.hilt)
//    alias(libs.plugins.ksp)
//    alias(libs.plugins.kotlin.serialization)
//}
//
//android {
//    namespace = "io.github.aris0x145.kimerai.data"
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
//}
//
//dependencies {
//    // 依賴領域層
//    implementation(project(":domain"))
//
//    // 依賴核心模組
//    implementation(project(":core:common"))
//    implementation(project(":core:network"))
//    implementation(project(":core:database"))
//
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.core.ktx)
//
//    // 協程
////    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
////    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
//
//    // Hilt 依賴注入
//    implementation(libs.hilt.android)
//    ksp(libs.hilt.compiler)
//
//    // JSON 序列化
//    implementation(libs.kotlinx.serialization.json)
//
//    // 測試
//    testImplementation(platform(libs.androidx.compose.bom))
//    testImplementation(libs.junit)
//}