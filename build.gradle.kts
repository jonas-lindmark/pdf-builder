plugins {
    kotlin("jvm") version "2.2.20"
}

group = "io.github.jonaslindmark.pdfcompose"
version = "1.0-SNAPSHOT"
description = "Composable PDF builder with Kotlin DSL"

java { withSourcesJar() }

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.zxing:core:3.5.3")
    api("org.apache.pdfbox:pdfbox:3.0.6")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
