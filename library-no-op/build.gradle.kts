plugins {
    id("com.android.library")
    id("kotlin-android")
}

val kotlinVersion: String by rootProject.extra
val okhttpVersion: String by rootProject.extra
val compileSdkVersion: Int by rootProject.extra
val minSdkVersion: Int by rootProject.extra

android {
    compileSdk = compileSdkVersion
    namespace = "com.chuckerteam.chucker"

    defaultConfig {
        minSdk = minSdkVersion
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        jvmToolchain(11)
    }

    buildFeatures {
        buildConfig = false
    }

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
            "-module-name", "com.github.ChuckerTeam.Chucker.library-no-op",
            "-Xexplicit-api=strict"
        )
    }
}

dependencies {
    api("com.squareup.okhttp3:okhttp:$okhttpVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
}

apply(from = rootProject.file("gradle/gradle-mvn-push.gradle.kts"))
apply(from = rootProject.file("gradle/kotlin-static-analysis.gradle.kts"))
