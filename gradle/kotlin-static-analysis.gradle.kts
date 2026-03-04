plugins {
    id("io.gitlab.arturbosch.detekt")
    id("org.jlleitschuh.gradle.ktlint")
}

android.sourceSets.configureEach {
    java.srcDirs("src/$name/kotlin")
}

ktlint {
    debug.set(false)
    verbose.set(true)
    android.set(false)
    outputToConsole.set(true)
    ignoreFailures.set(false)
    enableExperimentalRules.set(true)
    kotlinScriptAdditionalPaths {
        include(fileTree("scripts/"))
    }
    filter {
        exclude { element -> element.file.path.contains("generated/") }
        include("**/kotlin/**")
    }
}

detekt {
    config.setFrom(files("../detekt-config.yml"))
    buildUponDefaultConfig = true
}
