
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
        testInstrumentationRunnerArguments["clearPackageData"] = "true"
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
        viewBinding = true
    }

    packaging {
        resources {
            excludes.add("META-INF/INDEX.LIST")
            excludes.add("META-INF/io.netty.versions.properties")
            excludes.add("META-INF/DEPENDENCIES")
        }
    }


    // Adicionando as configurações de testOptions
    testOptions {
        // Utiliza o Orchestrator para testes mais confiáveis
        execution = "ANDROIDX_TEST_ORCHESTRATOR"

        // Configurações para testes unitários
        unitTests.all {
            it.testLogging {
                events("passed", "failed", "skipped")
            }
            it.reports {
                junitXml.required.set(true)
                junitXml.outputLocation.set(layout.buildDirectory.dir("test-results/junit/connectedDebugAndroidTest"))
            }

        }
        // Diretório específico para resultados de testes instrumentados em XML
        resultsDir = layout.buildDirectory.dir("test-results/connected").get().asFile.toString()



    }




}

// Task personalizada para copiar e renomear o APK após a build
//tasks.register<Copy>("copyAndRenameDebugApk") {
//    val debugApkPath = layout.buildDirectory.file("outputs/apk/debug/app-debug.apk")
//    val outputDir = file("$rootDir/apk-outputs") // Diretório de destino
//    val newApkName = "TaskManagerPlus-debug.apk"
//
//    from(debugApkPath)
//    into(outputDir)
//    rename { newApkName }
//
//    // Configura a task para rodar após o assembleDebug
//    dependsOn("assembleDebug")
//}

//tasks.register<Copy>("copyAndRenameDebugApk") {
//    val debugApkPath = layout.buildDirectory.file("outputs/apk/debug/app-debug.apk")
//    val outputDir = file("C:\\ApksGerados") // Novo diretório de destino
//    val newApkName = "TaskManagerPlus-debug.apk"
//
//    from(debugApkPath)
//    into(outputDir)
//    rename { newApkName }
//
//    // Configura a task para rodar após o assembleDebug
//    dependsOn("assembleDebug")
//}

tasks.register<Copy>("copyAndRenameDebugApk") {
    val debugApkPath = layout.buildDirectory.file("outputs/apk/debug/app-debug.apk")
    val outputDir = file("$rootDir/apk-outputs") // Diretório de destino dentro do workspace
    val newApkName = "TaskManagerPlus-debug.apk"

    from(debugApkPath)
    into(outputDir)
    rename { newApkName }

    // Configura a task para rodar após o assembleDebug
    dependsOn("assembleDebug")
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
    implementation(libs.androidx.ui.test.android)
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





}