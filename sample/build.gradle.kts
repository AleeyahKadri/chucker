plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.squareup.wire")
    id("com.apollographql.apollo3")
}

val compileSdkVersion: Int by rootProject.extra
val minSdkVersion: Int by rootProject.extra
val targetSdkVersion: Int by rootProject.extra

val androidXTestRunner: String by rootProject.extra
val androidXEspresso: String by rootProject.extra
val androidXExtJUnit: String by rootProject.extra
val kotlinVersion: String by rootProject.extra
val coroutineVersion: String by rootProject.extra
val activityVersion: String by rootProject.extra
val materialComponentsVersion: String by rootProject.extra
val appCompatVersion: String by rootProject.extra
val constraintLayoutVersion: String by rootProject.extra
val okhttpVersion: String by rootProject.extra
val retrofitVersion: String by rootProject.extra
val apolloVersion: String by rootProject.extra
val leakcanaryVersion: String by rootProject.extra

val VERSION_NAME: String by project
val VERSION_CODE: String by project

wire {
    kotlin {}
}

android {
    compileSdk = compileSdkVersion
    namespace = "com.chuckerteam.chucker.sample"

    defaultConfig {
        minSdk = minSdkVersion
        targetSdk = targetSdkVersion
        applicationId = "com.chuckerteam.chucker.sample"
        versionName = VERSION_NAME
        versionCode = VERSION_CODE.toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = false
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = "chucker"
            keyPassword = "android"
            storeFile = file("debug.keystore")
            storePassword = "android"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    lint {
        abortOnError = true
        disable.add("AcceptsUserCertificates")
        disable.add("GradleDependency")
        warningsAsErrors = true
    }
}

kotlin {
    jvmToolchain(11)
}

apollo {
    service("rickandmortyapi") {
        packageName.set("com.chuckerteam.chucker.sample")
        schemaFile.set(file("src/main/graphql/com/chuckerteam/chucker/sample/schema.json.graphql"))
        srcDir("src/main/graphql")
        excludes.add("**/schema.json.graphql")
        excludes.add("**/schema.json")
    }
}

dependencies {
    implementation("androidx.test:runner:$androidXTestRunner")
    androidTestImplementation("androidx.test.espresso:espresso-core:$androidXEspresso")
    androidTestImplementation("androidx.test.ext:junit:$androidXExtJUnit")
    debugImplementation(project(":library"))
    releaseImplementation(project(":library-no-op"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")
    implementation("androidx.activity:activity-ktx:$activityVersion")

    implementation("com.google.android.material:material:$materialComponentsVersion")
    implementation("androidx.appcompat:appcompat:$appCompatVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")

    implementation("com.squareup.okhttp3:logging-interceptor:$okhttpVersion")
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    implementation("com.apollographql.apollo3:apollo-runtime:$apolloVersion")

    debugImplementation("com.squareup.leakcanary:leakcanary-android:$leakcanaryVersion")
}

apply(from = rootProject.file("gradle/kotlin-static-analysis.gradle.kts"))
