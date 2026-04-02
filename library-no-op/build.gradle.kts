plugins {
    id("com.android.library")
    id("kotlin-android")
}

val compileSdkVersion: Int by rootProject.extra
val minSdkVersion: Int by rootProject.extra

val okhttpVersion: String by rootProject.extra
val kotlinVersion: String by rootProject.extra

android {
    compileSdk = compileSdkVersion
    namespace = "com.chuckerteam.chucker"

    kotlinOptions {
        freeCompilerArgs += listOf(
            "-module-name",
            "com.github.ChuckerTeam.Chucker.library-no-op",
            "-Xexplicit-api=strict",
        )
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        buildConfig = false
    }

    defaultConfig {
        minSdk = minSdkVersion
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }

    lint {
        abortOnError = true
        disable.add("RtlEnabled")
        disable.add("GradleDependency")
        warningsAsErrors = true
    }
}

kotlin {
    jvmToolchain(11)
}

dependencies {
    api("com.squareup.okhttp3:okhttp:$okhttpVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
}

apply(from = rootProject.file("gradle/gradle-mvn-push.gradle.kts"))
apply(from = rootProject.file("gradle/kotlin-static-analysis.gradle.kts"))
