import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.plugins.signing.SigningExtension

apply(plugin = "maven-publish")
apply(plugin = "signing")

group = property("GROUP")
version = property("VERSION_NAME")

configure<PublishingExtension> {
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
                name.set(property("POM_REPO_NAME").toString())
                description.set(property("POM_DESCRIPTION").toString())
                url.set(property("POM_URL").toString())
                licenses {
                    license {
                        name.set(property("POM_LICENSE_NAME").toString())
                        url.set(property("POM_LICENSE_URL").toString())
                    }
                }
                scm {
                    connection.set(property("POM_SCM_CONNECTION").toString())
                    developerConnection.set(property("POM_SCM_CONNECTION").toString())
                    url.set(property("POM_URL").toString())
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
if (!signingKey.isNullOrBlank() && !signingPwd.isNullOrBlank()) {
    configure<SigningExtension> {
        useInMemoryPgpKeys(signingKey, signingPwd)
        sign(extensions.getByType(PublishingExtension::class.java).publications.getByName("release"))
    }
} else {
    logger.info("Signing Disable as the PGP key was not found")
}
