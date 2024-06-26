plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "project.study.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "project.study.app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
    buildFeatures {
        viewBinding = true
    }
}


dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.androidx.room.runtime)
    implementation(libs.ext.junit)
    implementation(libs.androidx.core.testing)
    implementation(libs.gson)
    implementation(libs.androidx.runner)
    implementation(libs.mockito.android)
    androidTestImplementation(libs.hamcrest)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.junit)
    testImplementation(libs.mockito.android)
    testImplementation(libs.mockito.core)
    testImplementation(libs.byte.buddy)
    testImplementation(libs.robolectric)
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.androidx.test.espresso.espresso.core)
    androidTestImplementation(libs.androidx.core)
    androidTestImplementation(libs.runner)
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.androidx.espresso.intents)
    androidTestImplementation(libs.androidx.test.ext.junit)
    annotationProcessor(libs.androidx.room.compiler)

}