import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.testing.Test
import kotlinx.validation.ApiValidationExtension
import io.codearte.gradle.nexus.NexusStagingExtension

val kotlinVersion = "2.1.21"
val androidGradleVersion = "8.10.1"
val coroutineVersion = "1.10.2"

// Google libraries
val activityVersion = "1.10.1"
val appCompatVersion = "1.7.1"
val constraintLayoutVersion = "2.2.1"
val materialComponentsVersion = "1.12.0"
val fragmentVersion = "1.8.8"
val roomVersion = "2.7.1"
val lifecycleVersion = "2.9.1"
val androidXCoreVersion = "2.2.0"
val paletteKtxVersion = "1.0.0"
val kspVersion = "2.1.21-2.0.2"

// Networking
val brotliVersion = "0.1.2"
val gsonVersion = "2.13.1"
val okhttpVersion = "4.12.0"
val retrofitVersion = "3.0.0"
val wireVersion = "5.3.3"

// Debug and quality control
val binaryCompatibilityValidator = "0.17.0"
val detektVersion = "1.23.8"
val ktLintGradleVersion = "12.3.0"
val leakcanaryVersion = "2.14"

// Apollo
val apolloVersion = "3.8.6"

// Testing
val androidxTestCoreVersion = "1.6.1"
val junitGradlePluignVersion = "1.13.0.0"
val junitVersion = "5.11.4"
val junit4Version = "4.13.2"
val mockkVersion = "1.14.2"
val robolectricVersion = "4.14.1"
val truthVersion = "1.4.4"
val androidXTestRunner = "1.6.2"
val androidXTestRules = "1.6.1"
val androidXTestExt = "1.2.1"
val androidXExpresso = "3.6.1"
val androidXExtJUnit = "1.2.1"

// Publishing
val nexusStagingPlugin = "0.30.0"

extra.apply {
    set("kotlinVersion", kotlinVersion)
    set("androidGradleVersion", androidGradleVersion)
    set("coroutineVersion", coroutineVersion)
    set("activityVersion", activityVersion)
    set("appCompatVersion", appCompatVersion)
    set("constraintLayoutVersion", constraintLayoutVersion)
    set("materialComponentsVersion", materialComponentsVersion)
    set("fragmentVersion", fragmentVersion)
    set("roomVersion", roomVersion)
    set("lifecycleVersion", lifecycleVersion)
    set("androidXCoreVersion", androidXCoreVersion)
    set("paletteKtxVersion", paletteKtxVersion)
    set("kspVersion", kspVersion)
    set("brotliVersion", brotliVersion)
    set("gsonVersion", gsonVersion)
    set("okhttpVersion", okhttpVersion)
    set("retrofitVersion", retrofitVersion)
    set("wireVersion", wireVersion)
    set("binaryCompatibilityValidator", binaryCompatibilityValidator)
    set("detektVersion", detektVersion)
    set("ktLintGradleVersion", ktLintGradleVersion)
    set("leakcanaryVersion", leakcanaryVersion)
    set("apolloVersion", apolloVersion)
    set("androidxTestCoreVersion", androidxTestCoreVersion)
    set("junitGradlePluignVersion", junitGradlePluignVersion)
    set("junitVersion", junitVersion)
    set("junit4Version", junit4Version)
    set("mockkVersion", mockkVersion)
    set("robolectricVersion", robolectricVersion)
    set("truthVersion", truthVersion)
    set("androidXTestRunner", androidXTestRunner)
    set("androidXTestRules", androidXTestRules)
    set("androidXTestExt", androidXTestExt)
    set("androidXExpresso", androidXExpresso)
    set("androidXExtJUnit", androidXExtJUnit)
    set("nexusStagingPlugin", nexusStagingPlugin)
    set("minSdkVersion", 21)
    set("targetSdkVersion", 35)
    set("compileSdkVersion", 35)
}

buildscript {
    repositories {
        google()
        gradlePluginPortal()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.10.1")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.13.0.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.21")
        classpath("com.apollographql.apollo3:apollo-gradle-plugin:3.8.6")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.8")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:12.3.0")
        classpath("org.jetbrains.kotlinx:binary-compatibility-validator:0.17.0")
        classpath("com.squareup.wire:wire-gradle-plugin:5.3.3")
        classpath("io.codearte.gradle.nexus:gradle-nexus-staging-plugin:0.30.0")
        classpath("com.google.devtools.ksp:symbol-processing-gradle-plugin:2.1.21-2.0.2")
    }
}

apply(plugin = "binary-compatibility-validator")
apply(plugin = "io.codearte.nexus-staging")

configure<ApiValidationExtension> {
    ignoredProjects += listOf("sample")
    ignoredPackages += listOf(
        "com.chuckerteam.chucker.internal",
        "com.chuckerteam.chucker.databinding",
    )
}

val VERSION_NAME: String by project
val GROUP: String by project

allprojects {
    version = VERSION_NAME
    group = GROUP

    repositories {
        google()
        mavenCentral()
    }

    tasks.withType<Test>().configureEach {
        testLogging {
            events("skipped", "failed", "passed")
        }
    }
}

tasks.register<Copy>("installGitHook") {
    from(File(rootProject.rootDir, "pre-commit"))
    into(File(rootProject.rootDir, ".git/hooks"))
    fileMode = 511
}

tasks.register<Delete>("clean") {
    dependsOn("installGitHook")
    delete(rootProject.buildDir)
}

configure<NexusStagingExtension> {
    username = findProperty("NEXUS_USERNAME") as String?
    password = findProperty("NEXUS_PASSWORD") as String?
    stagingProfileId = "ea09119de9f4"
}
