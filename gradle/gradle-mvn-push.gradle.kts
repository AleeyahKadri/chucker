plugins {
    `maven-publish`
    signing
}

val GROUP: String by project
val VERSION_NAME: String by project
val POM_REPO_NAME: String by project
val POM_DESCRIPTION: String by project
val POM_URL: String by project
val POM_LICENSE_NAME: String by project
val POM_LICENSE_URL: String by project
val POM_SCM_CONNECTION: String by project

group = GROUP
version = VERSION_NAME

publishing {
    publications {
        create<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"])
            }
            artifactId = project.name
            pom {
                name.set(POM_REPO_NAME)
                description.set(POM_DESCRIPTION)
                url.set(POM_URL)
                licenses {
                    license {
                        name.set(POM_LICENSE_NAME)
                        url.set(POM_LICENSE_URL)
                    }
                }
                scm {
                    connection.set(POM_SCM_CONNECTION)
                    developerConnection.set(POM_SCM_CONNECTION)
                    url.set(POM_URL)
                }
                developers {
                    developer {
                        id.set("cortinico")
                        name.set("Nicola Corti")
                        email.set("corti.nico@gmail.com")
                    }
                    developer {
                        id.set("vbuberen")
                        name.set("Volodymyr Buberenko")
                        email.set("v.buberenko@gmail.com")
                    }
                    developer {
                        id.set("olivierperez")
                        name.set("Olivier Perez")
                        email.set("olivier@olivierperez.fr")
                    }
                }
            }
        }
    }
}

val signingKey: String? = findProperty("SIGNING_KEY") as String?
val signingPwd: String? = findProperty("SIGNING_PWD") as String?
if (signingKey != null && signingPwd != null) {
    signing {
        useInMemoryPgpKeys(signingKey, signingPwd)
        sign(publishing.publications["release"])
    }
} else {
    logger.info("Signing Disable as the PGP key was not found")
}
