rootProject.name = "LayoutOverlay"

enableFeaturePreview("VERSION_CATALOGS")

// Set single lock file (gradle.lockfile)
// This preview feature should be enabled by default in Gradle 7
// More: https://docs.gradle.org/current/userguide/dependency_locking.html#single_lock_file_per_project
// enableFeaturePreview("ONE_LOCKFILE_PER_PROJECT")

include(
    ":app",
    ":feature:home",
    ":feature:overlay",
    ":shared"
)

// Sharing dependency versions
// Plugin versions are in gradle.properties
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        val agpVersion: String by settings
        id ("com.android.application") version "7.1.0-alpha12"
        id ("com.android.library") version "7.1.0-alpha12"

        val kotlinVersion: String by settings
        id ("org.jetbrains.kotlin.android") version "1.6.0-M1"
        id ("org.jetbrains.kotlin.kapt") version kotlinVersion
        id ("org.jetbrains.kotlin.plugin.parcelize") version kotlinVersion

        val androidJUnit5Version: String by settings
        id("de.mannodermaus.android-junit5") version androidJUnit5Version

        val hiltVersion: String by settings
        id("dagger.hilt.android.plugin") version hiltVersion
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "com.android.application",
                "com.android.library" -> {
                    val agpCoordinates: String by settings
                    useModule(agpCoordinates)
                }
                "androidx.navigation.safeargs.kotlin" -> {
                    val navigationCoordinates: String by settings
                    useModule(navigationCoordinates)
                }
                "de.mannodermaus.android-junit5" -> {
                    val androidJnit5Coordinates: String by settings
                    useModule(androidJnit5Coordinates)
                }
                "dagger.hilt.android.plugin" -> {
                    val hiltCoordinates: String by settings
                    useModule(hiltCoordinates)
                }
            }
        }
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            alias("core-ktx").to("androidx.core:core-ktx:1.+")
            alias("fragment-ktx").to("androidx.fragment:fragment-ktx:1.+")
            bundle("ktx", listOf("core-ktx", "fragment-ktx"))

            alias("appcompat").to("androidx.appcompat:appcompat:1.+")
            alias("material").to("com.google.android.material:material:1.+")
            alias("constraintLayout").to("androidx.constraintlayout:constraintlayout:2.+")
            alias("lifecycle").to("androidx.lifecycle:lifecycle-common-java8:2.+")
            alias("cardview").to("androidx.cardview:cardview:1.+")
            alias("lottie").to("com.airbnb.android:lottie:2.+")

            val hiltVersion: String by settings
            version("hilt", hiltVersion)
            alias("hilt").to("com.google.dagger", "hilt-android").versionRef("hilt")
            alias("hilt.compiler").to("com.google.dagger", "hilt-android-compiler").versionRef("hilt")

            val navigationVersion: String by settings
            version("navigation", navigationVersion)
            alias("navigation-fragment").to("androidx.navigation", "navigation-fragment-ktx").versionRef("navigation")
            alias("navigation-ui-ktx").to("androidx.navigation", "navigation-ui-ktx").versionRef("navigation")
            bundle("navigation", listOf("navigation-fragment", "navigation-ui-ktx"))

            version("room", "2.+")
            alias("room-ktx").to("androidx.room", "room-ktx").versionRef("room")
            alias("room-runtime").to("androidx.room", "room-runtime").versionRef("room")
            bundle("room", listOf("room-ktx", "room-runtime"))

            alias("room.compiler").to("androidx.room", "room-compiler").versionRef("room")

            alias("test-runner").to("androidx.test:runner:1.+")
            alias("test-rules").to("androidx.test:rules:1.+")
            alias("hamcrest").to("org.hamcrest:hamcrest-library:1.+")
            alias("espresso").to("androidx.test.espresso:espresso-core:3.+")
            alias("uiautomator").to("androidx.test.uiautomator:uiautomator:2.+")
            alias("junit5-test-core").to("de.mannodermaus.junit5:android-test-core:1.+")
            alias("junit5-test-runner").to("de.mannodermaus.junit5:android-test-runner:1.+")

            version("junit", "5.+")
            alias("junit-jupiter-api").to("org.junit.jupiter", "junit-jupiter-api").versionRef("junit")
            alias("junit-jupiter-engine").to("org.junit.jupiter", "junit-jupiter-engine").versionRef("junit")

            bundle(
                "test",
                listOf(
                    "test-runner",
                    "junit-jupiter-api",
                    "junit5-test-core"
                )
            )

            bundle(
                "androidTest",
                listOf(
                    "test-runner",
                    "test-rules",
                    "hamcrest",
                    "espresso",
                    "uiautomator"
                )
            )
        }
    }
}
