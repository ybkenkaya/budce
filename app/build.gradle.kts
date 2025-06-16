plugins {
    alias(libs.plugins.android.application) // Bu satır zaten 'com.android.application' çağırıyor
    alias(libs.plugins.kotlin.android)

    id("org.jetbrains.kotlin.kapt") // kapt için doğru tanım

}




android {
    namespace = "com.ybk.budce"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.ybk.budce"
        minSdk = 24
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // ROOM (Veritabanı kütüphaneleri)
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    // Kotlin Annotation Processing Tool (Kapt) kullanmak için kapt'ı ekliyoruz.
    // build.gradle.kts (Module :app) dosyasının en üstündeki plugins { ... } bloğuna
    // `id("org.jetbrains.kotlin.kapt")` satırını eklediğinizden emin olun.
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version") // Coroutine desteği için

    // ViewModel (UI ile data arasındaki köprü)
    val lifecycle_version = "2.7.0"
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")

    // Kotlin Coroutines (Arka plan işlemleri için)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
}
