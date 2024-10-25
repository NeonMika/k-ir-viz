plugins {
    kotlin("jvm") version "2.0.20" // we have a kotlin project
    `java-gradle-plugin` // we generate a gradle plugin configured in the gradlePlugin section
    `maven-publish` // the generated plugin will be published to mavenCentral / mavenLocal


    // https://docs.gradle.org/current/userguide/kotlin_dsl.html#sec:kotlin-dsl_plugin
    // The Kotlin DSL Plugin provides a convenient way to develop Kotlin-based projects that contribute build logic.
    // That includes buildSrc projects, included builds and Gradle plugins.
    `kotlin-dsl` // To be able to use Kotlin when developing the Plugin<Project> class
}

group = "at.neon"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    // https://youtrack.jetbrains.com/issue/KT-47897/Official-Kotlin-Gradle-plugin-api
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin-api:2.0.20") // Use the appropriate version
}

gradlePlugin {
    plugins {
        create("k-ir-viz") { // this name is used to generate Gradle's "publishing" task names
            id = "at.neon.k-ir-viz" // to use this plugin later in other projects we will use plugins { id("at.neon.k-ir-viz-plugin") }
            implementationClass = "at.neon.KIRVizGradlePlugin"
        }
    }
}