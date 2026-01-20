plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
}

val kotlinVersion: String by rootProject.extra
val materialComponentsVersion: String by rootProject.extra
val constraintLayoutVersion: String by rootProject.extra
val paletteKtxVersion: String by rootProject.extra
val activityVersion: String by rootProject.extra
val fragmentVersion: String by rootProject.extra
val lifecycleVersion: String by rootProject.extra
val roomVersion: String by rootProject.extra
val coroutineVersion: String by rootProject.extra
val gsonVersion: String by rootProject.extra
val brotliVersion: String by rootProject.extra
val okhttpVersion: String by rootProject.extra
val junitVersion: String by rootProject.extra
val junit4Version: String by rootProject.extra
val mockkVersion: String by rootProject.extra
val androidxTestCoreVersion: String by rootProject.extra
val androidXCoreVersion: String by rootProject.extra
val truthVersion: String by rootProject.extra
val robolectricVersion: String by rootProject.extra
val androidXTestRunner: String by rootProject.extra
val androidXTestRules: String by rootProject.extra
val androidXTestExt: String by rootProject.extra

val compileSdkVersion: Int by rootProject.extra
val minSdkVersion: Int by rootProject.extra

android {
    compileSdk = compileSdkVersion
    namespace = "com.chuckerteam.chucker"

    defaultConfig {
        minSdk = minSdkVersion
        consumerProguardFiles("proguard-rules.pro")
        resValue("string", "chucker_version", project.property("VERSION_NAME") as String)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        jvmToolchain(11)
    }

    buildFeatures {
        viewBinding = true
        buildConfig = false
    }


    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            all {
                it.useJUnitPlatform()
            }
        }
    }

    resourcePrefix = "chucker_"

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
    lint {
        abortOnError = true
        disable += listOf("RtlEnabled", "GradleDependency")
        warningsAsErrors = true
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs += listOf(
            "-module-name", "com.github.ChuckerTeam.Chucker.library",
            "-Xexplicit-api=strict"
        )
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    implementation("com.google.android.material:material:$materialComponentsVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")
    implementation("androidx.palette:palette-ktx:$paletteKtxVersion")

    implementation("androidx.activity:activity-ktx:$activityVersion")
    implementation("androidx.fragment:fragment-ktx:$fragmentVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")

    implementation("com.google.code.gson:gson:$gsonVersion")

    implementation("org.brotli:dec:$brotliVersion")

    api(platform("com.squareup.okhttp3:okhttp-bom:$okhttpVersion"))
    api("com.squareup.okhttp3:okhttp")
    testImplementation("com.squareup.okhttp3:mockwebserver")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("junit:junit:$junit4Version")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("androidx.test:core:$androidxTestCoreVersion")
    testImplementation("androidx.arch.core:core-testing:$androidXCoreVersion")
    testImplementation("com.google.truth:truth:$truthVersion")
    testImplementation("org.robolectric:robolectric:$robolectricVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineVersion")

    androidTestImplementation("junit:junit:$junit4Version")
    androidTestImplementation("androidx.test:runner:$androidXTestRunner")
    androidTestImplementation("androidx.test:rules:$androidXTestRules")
    androidTestImplementation("com.google.truth:truth:$truthVersion")
    androidTestImplementation("androidx.test.ext:junit:$androidXTestExt")
}

apply(from = rootProject.file("gradle/gradle-mvn-push.gradle.kts"))
apply(from = rootProject.file("gradle/kotlin-static-analysis.gradle.kts"))
