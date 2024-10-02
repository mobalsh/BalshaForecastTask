import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)

    id("com.google.gms.google-services")

    id("com.google.devtools.ksp")

    kotlin("kapt")
    kotlin("plugin.parcelize")
}

android {
    namespace = "com.balsha.forecasttask"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.balsha.forecasttask"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())

    project.logger.lifecycle("Props: $properties")

    signingConfigs {
        create("release") {
            project.logger.lifecycle("Release: ${properties["RELEASE_KEY_STORE"]}")
            storeFile = file(path = "${properties["RELEASE_STORE_PATH"]}")
            storePassword = "${properties["RELEASE_STORE_PASSWORD"]}"
            keyAlias = "${properties["RELEASE_KEY_ALIAS"]}"
            keyPassword = "${properties["RELEASE_KEY_PASSWORD"]}"
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )

            buildConfigField("String", "BASE_URL", "\"${properties["DEBUG_BASE_URL"]}\"")
            buildConfigField("String", "IMAGES_URL", "\"${properties["DEBUG_IMAGES_URL"]}\"")
            buildConfigField("String", "APP_ID", "\"${properties["APP_ID"]}\"")

            signingConfig = signingConfigs["debug"]
        }
        release {
            isMinifyEnabled = false
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )

            buildConfigField("String", "BASE_URL", "\"${properties["RELEASE_BASE_URL"]}\"")
            buildConfigField("String", "IMAGES_URL", "\"${properties["RELEASE_IMAGES_URL"]}\"")
            buildConfigField("String", "APP_ID", "\"${properties["APP_ID"]}\"")

            signingConfig = signingConfigs["release"]
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions { jvmTarget = "1.8" }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(platform("com.google.firebase:firebase-bom:33.3.0"))
    implementation("com.google.firebase:firebase-analytics")


    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    //Glide
    implementation(libs.glide.library)
    kapt(libs.glide.compiler)

    //Room
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    //Unit Testing
    testImplementation(libs.androidx.room.testing)
    testImplementation(libs.mockito.core)
    testImplementation(libs.kotlinx.coroutines.test)

    //UI Testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.rules)
}