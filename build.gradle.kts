plugins {
    java
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.0.0"
    id("org.hildan.github.changelog") version "0.8.0"
}

group = "org.hildan.checkstyle"
description = "A checkstyle configuration shared by multiple projects"

changelog {
    futureVersionTag = "v${project.version}"
    releaseUrlTemplate = "https://bintray.com/joffrey-bion/maven/checkstyle-config/%s"
    releaseUrlTagTransform = { it.substring(1) }
    customTagByIssueNumber = mapOf(1 to "v1.1.0")
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val javadocJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    archiveClassifier.set("javadoc")
    from(tasks.javadoc)
}

nexusPublishing {
    packageGroup.set("org.hildan")
    repositories {
        sonatype()
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            artifact(sourcesJar)
            artifact(javadocJar)

            val githubUser = findProperty("githubUser") as String? ?: System.getenv("GITHUB_USER")
            val githubSlug = "$githubUser/${rootProject.name}"
            val githubRepoUrl = "https://github.com/$githubSlug"

            pom {
                name.set(project.name)
                description.set(project.description)
                url.set(githubRepoUrl)
                licenses {
                    license {
                        name.set("The MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("joffrey-bion")
                        name.set("Joffrey Bion")
                        email.set("joffrey.bion@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:$githubRepoUrl.git")
                    developerConnection.set("scm:git:git@github.com:$githubSlug.git")
                    url.set(githubRepoUrl)
                }
            }
        }
    }
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications["maven"])
}
