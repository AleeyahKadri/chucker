plugins {
    `maven-publish`
    signing
}

group = project.property("GROUP") as String
version = project.property("VERSION_NAME") as String

publishing {
    repositories {
        maven {
            name = "snapshot"
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
            credentials {
                username = findProperty("NEXUS_USERNAME") as String?
                password = findProperty("NEXUS_PASSWORD") as String?
            }
        }
        maven {
            name = "staging"
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
            credentials {
                username = findProperty("NEXUS_USERNAME") as String?
                password = findProperty("NEXUS_PASSWORD") as String?
            }
        }
    }
    publications {
        create<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"])
            }
            artifactId = project.name
            pom {
                name.set(project.property("POM_REPO_NAME") as String)
                description.set(project.property("POM_DESCRIPTION") as String)
                url.set(project.property("POM_URL") as String)
                licenses {
                    license {
                        name.set(project.property("POM_LICENSE_NAME") as String)
                        url.set(project.property("POM_LICENSE_URL") as String)
                    }
                }
                scm {
                    connection.set(project.property("POM_SCM_CONNECTION") as String)
                    developerConnection.set(project.property("POM_SCM_CONNECTION") as String)
                    url.set(project.property("POM_URL") as String)
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

val signingKey = findProperty("SIGNING_KEY") as String?
val signingPwd = findProperty("SIGNING_PWD") as String?
if (signingKey != null && signingPwd != null) {
    signing {
        useInMemoryPgpKeys(signingKey, signingPwd)
        sign(publishing.publications["release"])
    }
} else {
    logger.info("Signing Disable as the PGP key was not found")
}
