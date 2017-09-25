# Checkstyle Config

A checkstyle configuration to be used in multiple projects.

Following [Gradle documentation's instructions](https://docs.gradle.org/2.2/release-notes.html#sharing-configuration-files-across-builds), 
checkstyle can be configured in your project this way:

```groovy
apply plugin: "checkstyle"

configurations {
    checkstyleConfig
}

dependencies {
    checkstyleConfig "org.hildan.checkstyle:checkstyle-config:1.0.0"
}

checkstyle { // affects all Checkstyle tasks
    config = resources.text.fromArchiveEntry(configurations.checkstyleConfig, "checkstyle.xml")
}

```
