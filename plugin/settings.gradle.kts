plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "k-ir-viz"

include("library")
include("k-ir-viz-gradle-plugin")
include("k-ir-viz-compiler-plugin")