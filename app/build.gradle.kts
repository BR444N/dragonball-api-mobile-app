plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.br444n.dragonball"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.br444n.dragonball"
        minSdk = 26
        targetSdk = 36
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
        compose = true
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
}

    dependencies {
        // ---- Dependencias de la aplicación (implementation) ----

        // Core, Lifecycle y Activity
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(libs.splashScreen)

        // Jetpack Compose
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.material3)
        implementation(libs.androidx.compose.material.icons.extended)

        // Navigation Compose
        implementation(libs.androidx.navigation.navigation.compose)

        // ViewModel & LiveData
        implementation(libs.androidx.lifecycle.viewmodel.compose)
        implementation(libs.livedata)

        // Networking (Retrofit + Gson)
        implementation(libs.retrofit)
        implementation(libs.retrofit.converter.gson)

        // Coroutines
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.kotlinx.coroutines.android)

        // Image Loading (Coil)
        implementation(libs.coil)

        // Animations (Lottie)
        implementation(libs.lottie)

        // ML Kit Translation
        implementation(libs.translate)

        // Material Design Components (para compatibilidad con temas y vistas XML si es necesario)
        implementation(libs.android.material)

        // ---- Dependencias de Pruebas Unitarias (testImplementation) ----
        testImplementation(libs.junit)

        // ---- Dependencias de Pruebas de Instrumentación (androidTestImplementation) ----
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)

        // ---- Dependencias de Depuración (debugImplementation) ----
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)
    }