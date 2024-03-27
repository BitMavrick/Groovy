plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("com.google.firebase.crashlytics")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.bitmavrick.groovy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bitmavrick.groovy"
        minSdk = 24
        targetSdk = 34
        versionCode = 5 // This version is currently on play store, make version update before pushing next update
        versionName = "1.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.transition:transition-ktx:1.5.0-alpha06")
    implementation("androidx.fragment:fragment-ktx:1.7.0-alpha10")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.appcompat:appcompat:1.7.0-alpha03")
    implementation("androidx.media3:media3-exoplayer:1.2.1")
    implementation("androidx.media3:media3-session:1.2.1")
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation("com.github.bumptech.glide:glide:4.15.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("me.zhanghai.android.fastscroll:library:1.3.0")
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.12")
    ksp("com.github.bumptech.glide:ksp:4.15.1")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.7.3"))

    // Add the dependencies for the Crashlytics and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")
}