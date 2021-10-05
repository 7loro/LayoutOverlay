plugins {
    id(GradlePluginId.ANDROID_APPLICATION)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_KAPT)
}

android {
    compileSdk = AndroidConfig.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = AndroidConfig.ID
        minSdk = AndroidConfig.MIN_SDK_VERSION
        targetSdk = AndroidConfig.TARGET_SDK_VERSION
        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
    }
    buildFeatures.viewBinding = true
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":feature:service"))
    implementation(project(":feature:home"))
    implementation(project(":feature:overlay"))
    implementation(project(":feature:calculator"))
    implementation(project(":feature:settings"))
    implementation(project(":shared"))

    api(libs.bundles.ktx)
    api(libs.appcompat)
    api(libs.material)
    api(libs.constraintLayout)
    api(libs.bundles.navigation)
    api(libs.hilt)
    kapt(libs.hilt.compiler)

    testApi(libs.bundles.test)
    testRuntimeOnly(libs.junit.jupiter.engine)
    androidTestApi(libs.bundles.androidTest)
}