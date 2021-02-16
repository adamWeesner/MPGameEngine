plugins {
    id("org.jetbrains.compose") version Shared.composeUiVersion
    id("com.android.application")
    kotlin("android")
}

group = Shared.groupId
version = Shared.Android.appVersion

repositories {
    google()
}

dependencies {
    implementation(project(":common"))
}

android {
    compileSdkVersion(Shared.Android.compileSdkVersion)
    defaultConfig {
        applicationId = "${Shared.groupId}.android"
        minSdkVersion(Shared.Android.minSdkVersion)
        targetSdkVersion(Shared.Android.targetSdkVersion)
        versionCode = 1
        versionName = Shared.Android.appVersion
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}