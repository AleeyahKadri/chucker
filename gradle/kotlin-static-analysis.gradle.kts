plugins {
    id("io.gitlab.arturbosch.detekt")
    id("org.jlleitschuh.gradle.ktlint")
}

configure<com.android.build.gradle.BaseExtension> {
    sourceSets.configureEach {
        java.srcDirs("src/$name/kotlin")
    }
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
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

configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
    config.setFrom(files("../detekt-config.yml"))
    buildUponDefaultConfig = true
}
