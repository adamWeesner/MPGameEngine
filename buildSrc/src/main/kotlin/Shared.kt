object Shared {
    const val groupId = "com.weesnerDevelopment"
    const val composeUiVersion = "0.2.0-build132"
    val javaVersion = "11"

    object Android {
        const val appVersion = "1.0.0"
        const val compileSdkVersion = 29
        const val minSdkVersion = 24
        const val targetSdkVersion = 29
    }

    object Desktop {
        object Test {
            const val junitApi = "org.junit.jupiter:junit-jupiter-api:5.6.0"
            const val junitEngine = "org.junit.jupiter:junit-jupiter-engine:5.6.0"
        }
    }
}