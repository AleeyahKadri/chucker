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

extra.apply {
    set("kotlinVersion", "2.1.21")
    set("androidGradleVersion", "8.10.1")
    set("coroutineVersion", "1.10.2")

    // Google libraries
    set("activityVersion", "1.10.1")
    set("appCompatVersion", "1.7.1")
    set("constraintLayoutVersion", "2.2.1")
    set("materialComponentsVersion", "1.12.0")
    set("fragmentVersion", "1.8.8")
    set("roomVersion", "2.7.1")
    set("lifecycleVersion", "2.9.1")
    set("androidXCoreVersion", "2.2.0")
    set("paletteKtxVersion", "1.0.0")
    set("kspVersion", "2.1.21-2.0.2")

    // Networking
    set("brotliVersion", "0.1.2")
    set("gsonVersion", "2.13.1")
    set("okhttpVersion", "4.12.0")
    set("retrofitVersion", "3.0.0")
    set("wireVersion", "5.3.3")

    // Debug and quality control
    set("binaryCompatibilityValidator", "0.17.0")
    set("detektVersion", "1.23.8")
    set("ktLintGradleVersion", "12.3.0")
    set("leakcanaryVersion", "2.14")

    // Apollo
    set("apolloVersion", "3.8.6")

    // Testing
    set("androidxTestCoreVersion", "1.6.1")
    set("junitGradlePluignVersion", "1.13.0.0")
    set("junitVersion", "5.11.4")
    set("junit4Version", "4.13.2")
    set("mockkVersion", "1.14.2")
    set("robolectricVersion", "4.14.1")
    set("truthVersion", "1.4.4")
    set("androidXTestRunner", "1.6.2")
    set("androidXTestRules", "1.6.1")
    set("androidXTestExt", "1.2.1")
    set("androidXExpresso", "3.6.1")
    set("androidXExtJUnit", "1.2.1")

    // Publishing
    set("nexusStagingPlugin", "0.30.0")
}

plugins {
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.17.0"
    id("io.codearte.nexus-staging") version "0.30.0"
}

apiValidation {
    ignoredProjects.add("sample")
    ignoredPackages.add("com.chuckerteam.chucker.internal")
    ignoredPackages.add("com.chuckerteam.chucker.databinding")
}

allprojects {
    version = findProperty("VERSION_NAME") as String
    group = findProperty("GROUP") as String

    repositories {
        google()
        mavenCentral()
    }

    tasks.withType<Test> {
        testLogging {
            events("skipped", "failed", "passed")
        }
    }
}

tasks.register<Copy>("installGitHook") {
    from(File(rootProject.rootDir, "pre-commit"))
    into(File(rootProject.rootDir, ".git/hooks"))
    fileMode = 0x777
}

tasks.register<Delete>("clean") {
    dependsOn("installGitHook")
    delete(rootProject.layout.buildDirectory)
}

extra.apply {
    set("minSdkVersion", 21)
    set("targetSdkVersion", 35)
    set("compileSdkVersion", 35)
}

nexusStaging {
    username = findProperty("NEXUS_USERNAME") as String?
    password = findProperty("NEXUS_PASSWORD") as String?
    stagingProfileId = "ea09119de9f4"
}
