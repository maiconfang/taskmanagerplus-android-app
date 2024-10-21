plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "maif.taskmanagerplus"
    compileSdk = 34

    defaultConfig {
        applicationId = "maif.taskmanagerplus"
        minSdk = 33
        targetSdk = 34
        versionCode = 3
        versionName = "1.0.1"

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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.espresso.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

// Room dependencies using Kotlin DSL syntax
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    // Optional Room components
    implementation("androidx.room:room-rxjava2:$room_version")
    implementation("androidx.room:room-rxjava3:$room_version")
    implementation("androidx.room:room-guava:$room_version")
    testImplementation("androidx.room:room-testing:$room_version")
    implementation("androidx.room:room-paging:$room_version")

    val activity_version = "1.9.2"

    // Java language implementation
    implementation("androidx.activity:activity:$activity_version")
    // Kotlin
    implementation("androidx.activity:activity-ktx:$activity_version")

    // Core testing framework and extensions
    androidTestImplementation("androidx.test:core:1.6.1")
    androidTestImplementation("androidx.test:core-ktx:1.6.1")

    // Espresso core for UI testing
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // JUnit and Kotlin extensions
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.2.1")

    // Truth for more intuitive assertions
    androidTestImplementation("androidx.test.ext:truth:1.6.0")

    // Runner to execute instrumented tests
    androidTestImplementation("androidx.test:runner:1.6.1")

    // Orchestrator for better test management
    androidTestUtil("androidx.test:orchestrator:1.5.0")

    // https://mvnrepository.com/artifact/io.appium/java-client
    implementation("io.appium:java-client:9.3.0")

}