plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = AppConfig.NAME_SPACE
    compileSdk = AppConfig.COMPILE_SDK

    defaultConfig {
        minSdk = AppConfig.MIN_SDK
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTargetVersion.get()
    }
}

//dependencies{
//    implementation(libs.retrofit)
//    implementation(libs.retrofit.gson)
//}