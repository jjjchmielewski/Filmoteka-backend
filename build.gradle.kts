import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.sparkjava:spark-core:2.9.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.0-rc3")
    testImplementation("org.slf4j:slf4j-simple:2.0.3")
    implementation("org.mongodb:mongodb-driver-sync:4.7.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
