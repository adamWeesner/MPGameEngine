import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version Shared.composeUiVersion
    id("com.android.library")
    id("kotlin-android-extensions")
}

group = Shared.groupId
version = "1.0.0"

repositories {
    google()
}

kotlin {
    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = Shared.javaVersion
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
            }
        }
        val commonTest by getting
        val androidMain by getting {
            dependencies {
                api("com.google.android.material:material:1.3.0")
                api("androidx.core:core-ktx:1.3.2")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1")
                api("androidx.preference:preference-ktx:1.1.1")
                api("com.github.InkApplications.kimchi:kimchi-jvm:1.0.2")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13")
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                api("com.github.InkApplications.kimchi:kimchi-jvm:1.0.2")
            }
        }
        val desktopTest by getting {
            dependencies {
                implementation(kotlin("test-junit5"))
                implementation(Shared.Desktop.Test.junitApi)
                runtimeOnly(Shared.Desktop.Test.junitEngine)
            }
        }
    }
}

android {
    compileSdkVersion(Shared.Android.compileSdkVersion)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(Shared.Android.minSdkVersion)
        targetSdkVersion(Shared.Android.targetSdkVersion)
    }
}