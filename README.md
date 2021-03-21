# Checkstyle Config

[![Maven central version](https://img.shields.io/maven-central/v/org.hildan.checkstyle/checkstyle-config.svg)](http://mvnrepository.com/artifact/org.hildan.checkstyle/checkstyle-config)
[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/joffrey-bion/fx-gson/blob/master/LICENSE)

A checkstyle configuration shared by multiple projects.

### Usage

Following [Gradle documentation's instructions](https://docs.gradle.org/2.2/release-notes.html#sharing-configuration-files-across-builds), 
checkstyle can be configured in your project this way:

```groovy
apply plugin: "checkstyle"

configurations {
    checkstyleConfig
}

dependencies {
    checkstyleConfig "org.hildan.checkstyle:checkstyle-config:1.1.0"
}

checkstyle { // affects all Checkstyle tasks
    config = resources.text.fromArchiveEntry(configurations.checkstyleConfig, "checkstyle.xml")
}

```
