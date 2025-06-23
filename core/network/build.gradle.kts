import java.util.Properties

plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
}

android {
    namespace = AppConfig.NAME_SPACE
    compileSdk = AppConfig.COMPILE_SDK

    defaultConfig {
        minSdk = AppConfig.MIN_SDK
        buildConfigField("String", "BASE_URL", "\"https://airline.api.cert.platform.sabre.com/\"")
        buildConfigField("String", "BASIC_TOKEN", "\"Basic VmpFNlQwUk5NREpUUnpwUFJEcFBSQT09OlRVOUNNbE5IVDBRPQ==\"")
    }
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTargetVersion.get()
    }
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(project(":data:tokenManager"))
}